package com.yangui.common.emchat.api.impl;

import com.yangui.common.emchat.api.EasemobRestAPI;
import com.yangui.common.emchat.api.SendMessageAPI;
import com.yangui.common.emchat.comm.constant.HTTPMethod;
import com.yangui.common.emchat.comm.helper.HeaderHelper;
import com.yangui.common.emchat.comm.wrapper.BodyWrapper;
import com.yangui.common.emchat.comm.wrapper.HeaderWrapper;


public class EasemobSendMessage extends EasemobRestAPI implements SendMessageAPI {
    private static final String ROOT_URI = "/messages";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object sendMessage(Object payload) {
        String  url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        BodyWrapper body = (BodyWrapper) payload;

        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
