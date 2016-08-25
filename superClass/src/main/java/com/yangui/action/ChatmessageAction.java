package com.yangui.action;



import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yangui.common.Constants;
import com.yangui.service.impl.BroadcastService;
import com.yangui.service.impl.GroupService;
import com.yangui.service.impl.UserService;

@ParentPackage("dataJsonDefault")
@Service
public class ChatmessageAction {
	//交互数据
	private String id;//用户Id
	private String date;
	private String content;
	private String page;//页数

	//单个Json
	private Map<String, Object> dataMap=new HashMap<String, Object>();
	//Json数组
	private List<Object> dataList=new ArrayList<Object>();


	@Autowired
	private UserService userService;
	@Autowired
	private GroupService  groupService;
	@Autowired
	private BroadcastService  broadcastService;


	/**
	 * 发布公告
	 * @return
	 */
	@Action(value="/chatmessage/publish",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String publish(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		return "success";
	}

	
	
	
	/**
	 * 回显公告
	 * @return
	 */
	@Action(value="/chatmessage/show",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String show(){
		// dataList中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataList.clear();
		return "success";
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}




	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	//使json有效
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}


	public List<Object> getDataList() {
		return dataList;
	}




	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}




	public String getPage() {
		return page;
	}




	public void setPage(String page) {
		this.page = page;
	}


}
