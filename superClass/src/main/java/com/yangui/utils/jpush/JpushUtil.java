package com.yangui.utils.jpush;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.yangui.common.Constants;

/**
 * 极光推送工具类
 * @author yan gui
 *
 */
public class JpushUtil {
	public static void  push(JSONObject jsonObject){
		// HttpProxy proxy = new HttpProxy("localhost", 3128);
		// Can use this https proxy: https://github.com/Exa-Networks/exaproxy
		JPushClient jpushClient = new JPushClient(Constants.JPUSH_MASTER_SECRET,Constants.JPUSH_APP_KEY);

		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPushObject_android_tag_alertWithTitle(jsonObject);

		try {
			PushResult result = jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}
	public static PushPayload buildPushObject_android_tag_alertWithTitle(JSONObject jsonObject) {
		String  title=null;
		String  alert=null;
		String[] alias=null;
		Map<String, String> map=new HashMap<String,String>();
		try {
			title=(String) jsonObject.get("title");
			alert=(String) jsonObject.get("alert");
			JSONArray jsonArray=(JSONArray)  jsonObject.get("alias");
			alias=new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				alias[i]=(String) jsonArray.get(i);
			}
			if("公告".equals(title)){
				map.put("type", "1");
			}else if("点名".equals(title)){
				map.put("type", "3");
			}else{//作业
				map.put("type", "2");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.alias(alias))
				.setNotification(Notification.android(alert, title,map))
				.build();
	}

}
