package com.yangui.common.emchat.api.impl;

import com.yangui.common.emchat.api.ChatMessageAPI;
import com.yangui.common.emchat.api.EasemobRestAPI;
import com.yangui.common.emchat.comm.constant.HTTPMethod;
import com.yangui.common.emchat.comm.helper.HeaderHelper;
import com.yangui.common.emchat.comm.wrapper.HeaderWrapper;
import com.yangui.common.emchat.comm.wrapper.QueryWrapper;


public class EasemobChatMessage extends EasemobRestAPI implements ChatMessageAPI {

    private static final String ROOT_URI = "chatmessages";

    public Object exportChatMessages(Long limit, String cursor, String query) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        QueryWrapper queryWrapper = QueryWrapper.newInstance().addLimit(limit).addCursor(cursor).addQueryLang(query);

        return getInvoker().sendRequest(HTTPMethod.METHOD_DELETE, url, header, null, queryWrapper);
    }

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }
}
