package com.yangui.common.emchat.api;


import java.io.File;

import com.yangui.common.emchat.comm.wrapper.BodyWrapper;
import com.yangui.common.emchat.comm.wrapper.HeaderWrapper;
import com.yangui.common.emchat.comm.wrapper.QueryWrapper;
import com.yangui.common.emchat.comm.wrapper.ResponseWrapper;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header, QueryWrapper query);
}
