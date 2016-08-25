package com.yangui.common.emchat.api.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yangui.common.emchat.api.AuthTokenAPI;
import com.yangui.common.emchat.api.EasemobRestAPI;
import com.yangui.common.emchat.comm.body.AuthTokenBody;
import com.yangui.common.emchat.comm.constant.HTTPMethod;
import com.yangui.common.emchat.comm.helper.HeaderHelper;
import com.yangui.common.emchat.comm.wrapper.BodyWrapper;
import com.yangui.common.emchat.comm.wrapper.HeaderWrapper;


public class EasemobAuthToken extends EasemobRestAPI implements AuthTokenAPI{
	
	public static final String ROOT_URI = "/token";
	
	private static final Logger log = LoggerFactory.getLogger(EasemobAuthToken.class);
	
	@Override
	public String getResourceRootURI() {
		return ROOT_URI;
	}

	public Object getAuthToken(String clientId, String clientSecret) {
		String url = getContext().getSeriveURL() + getResourceRootURI();
		BodyWrapper body = new AuthTokenBody(clientId, clientSecret);
		HeaderWrapper header = HeaderHelper.getDefaultHeader();
		
		return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
	}
}
