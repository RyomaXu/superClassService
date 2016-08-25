package com.yangui.action;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yangui.common.Constants;
import com.yangui.entity.Broadcast;
import com.yangui.entity.Group;
import com.yangui.entity.User;
import com.yangui.service.impl.BroadcastService;
import com.yangui.service.impl.GroupService;
import com.yangui.service.impl.UserService;
import com.yangui.utils.jpush.JpushUtil;
import com.yangui.utils.string.StringUtil;

@ParentPackage("dataJsonDefault")
@Service
public class BroadcastAction {
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
	@Action(value="/broadcast/publish",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String publish(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		User user=null;
		Group group=null;
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}else{
			dataMap.put("errorMsg", "用户不存在");
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}else{
			dataMap.put("errorMsg", "班级不存在");
		}
		if(group!=null && content!=null){
			Broadcast broadcast=new Broadcast();
			broadcast.setContent(content);
			broadcast.setGroup(group);
			broadcastService.insert(broadcast);
			
			List<Broadcast>  broadcasts=new ArrayList<Broadcast>();
			broadcasts.add(broadcast);
			group.setBroadcasts(broadcasts);
			groupService.update(group);
			
			
			//极光推送
			List<User> users=userService.findUsersByClassId(group.getId());
			StringUtil<User> userStringUtil=new StringUtil<User>();
			String alias=userStringUtil.getIDsToJsonArray(users);
			String json="{'title':'公告','alert':"+content+",'alias':"+alias+"}";
			JSONObject jsonObject=null;
			try {
				jsonObject = new JSONObject(json);
				JpushUtil.push(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			dataMap.put("msg", "班级公告发布成功");
		}
		return "success";
	}

	
	
	
	/**
	 * 回显公告
	 * @return
	 */
	@Action(value="/broadcast/show",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String show(){
		// dataList中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataList.clear();
		User user=null;
		Group group=null;
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}else{
			dataMap.put("errorMsg", "用户不存在");
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}else{
			dataMap.put("errorMsg", "班级不存在");
		}
		if(group!=null){
			try {  
				List<Broadcast> broadcasts=broadcastService.findBroadcastsForPage(group.getId(), (Integer.parseInt(page)-1)*Constants.ACTION_BROADCAST_PAGELIMIT, Constants.ACTION_BROADCAST_PAGELIMIT);
				for (int i = 0; i < broadcasts.size(); i++) {
					Map<String,Object> map=new HashMap<String,Object>();
					Broadcast broadcast=broadcasts.get(i);
					map.put("date", broadcast.getCreateTime().getTime());
					map.put("content", broadcast.getContent());
					broadcast=null;
					dataList.add(map);
					map=null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			dataMap.put("msg","展示公告");
		}
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
