package com.yangui.utils.emchat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.yangui.common.Constants;

/**
 * 环信工具类
 * @author yan gui
 *
 */
public class EmchatUtil {


	/**
	 * httpclient POST用JSON请求，返回JSON数据
	 * @return 环信token
	 */
	public static String getToken(){
		String path= "https://a1.easemob.com/"+Constants.EMCHAT_ORG_NAME+"/"+Constants.EMCHAT_APP_NAME+"/token"; 
		String access_token=null;
		try {
			//1.得到浏览器  
			CloseableHttpClient httpclient = HttpClients.createDefault();

			//2.构建请求实体的数据  
			JSONObject  obj = new JSONObject();
			obj.put("grant_type", "client_credentials");
			obj.put("client_id",Constants.EMCHAT_CLIENT_ID);
			obj.put("client_secret", Constants.EMCHAT_CLIENT_SECRET);//obj.append表示{"client_secret", ["YXA6Q5QsCqzgzZ_-BCeN0j28aVt0FZg"]}


			//3指定请求方式  
			HttpPost httpPost=new HttpPost(path);  
			httpPost.addHeader("Content-Type","application/json");
			// 解决中文乱码问题
			StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			httpPost.setEntity(stringEntity);


			//4.构建Handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {//
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {

						HttpEntity entity = response.getEntity();

						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};

			//5请求
			String responseBody = httpclient.execute(httpPost, responseHandler);
			JSONObject jsonObject=new JSONObject(responseBody);
			access_token=(String) jsonObject.get("access_token");
			Integer expires_in=(Integer) jsonObject.get("expires_in");
			String application=(String) jsonObject.get("application");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access_token;
	}

	/**
	 * 
	 * 使用http连接调用CXF rest风格的web服务
	 */
	public static void  getChatMessages(){//TODO..还没完成
		String authPath = "https://a1.easemob.com/"+Constants.EMCHAT_ORG_NAME+"/"+Constants.EMCHAT_APP_NAME+"/chatmessages";
		String token=getToken();
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(authPath).openConnection();
			conn.setRequestProperty("Content-Type","application/json");
			conn.setRequestProperty("Authorization","Bearer "+token+"");
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			//调用web服务
			if (code == 200) {
				InputStream inStream1 = conn.getInputStream();
				String jsonResult = getResponseString(inStream1);
				System.out.println(jsonResult);
			    //解析返回的json数组
				JSONArray jsonArray=new JSONArray();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static String getResponseString(InputStream inStream) throws Exception {
		byte[] data = read(inStream);
		String objectstring = new String(data);
		return objectstring;
	}
	public static byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outputStream.write(buffer);
		}
		inStream.close();
		return outputStream.toByteArray();
	}
	
	public static void main(String[] args) {
		getChatMessages();
	}
}
