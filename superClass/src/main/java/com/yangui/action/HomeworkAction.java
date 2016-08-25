package com.yangui.action;



import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yangui.common.Constants;
import com.yangui.entity.Group;
import com.yangui.entity.Homework;
import com.yangui.entity.User;
import com.yangui.service.impl.GroupService;
import com.yangui.service.impl.HomeworkService;
import com.yangui.service.impl.UserService;
import com.yangui.utils.jpush.JpushUtil;
import com.yangui.utils.string.StringUtil;

@ParentPackage("dataJsonDefault")
@Service
public class HomeworkAction {
	//交互数据
	private String id;//用户Id
	private Boolean isNewest;//是否最新
    private String subjectName;
    private String subjectRequire;
    private String  uploadDate;//year  mouth  day上交时间
    private String week;//上交截止周
    private String page;//第几页

	//单个Json
	private Map<String, Object> dataMap=new HashMap<String, Object>();
	//Json数组
	private List<Object> dataList=new ArrayList<Object>();


	@Autowired
	private UserService userService;
	@Autowired
	private GroupService  groupService;
	@Autowired
	private HomeworkService  homeworkService;
	


	/**
	 * 发布作业
	 * @return
	 */
	@Action(value="/homework/publish",results={
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
		if(group!=null && subjectName!=null && subjectRequire!=null){
			try {
				Homework homework=new Homework();
				homework.setSubjectName(subjectName);
				homework.setSubjectRequire(subjectRequire);
				homework.setSubmitWeek(week);
				Timestamp timestamp=new Timestamp(Long.parseLong(uploadDate));
				homework.setUploadDate(timestamp);
				homework.setGroup(group);
				homeworkService.insert(homework);
				
				List<Homework>  homeworks=new ArrayList<Homework>();
				homeworks.add(homework);
				group.setHomeworks(homeworks);
				groupService.update(group);
				
				
				//极光推送
				List<User> users=userService.findUsersByClassId(group.getId());
				StringUtil<User> userStringUtil=new StringUtil<User>();
				String alias=userStringUtil.getIDsToJsonArray(users);
				String json="{'title':'作业："+subjectName+"','alert':'作业要求："+subjectRequire+"','alias':"+alias+"}";
				JSONObject jsonObject=null;
				try {
					jsonObject = new JSONObject(json);
					JpushUtil.push(jsonObject);
					dataMap.put("msg", "作业发布成功");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				dataMap.put("msg", "作业发布失败");
				e.printStackTrace();
			}
		}
		return "success";
	}

	
	
	
	/**
	 * 回显过期作业
	 * @return
	 */
	@Action(value="/homework/shownotnew",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String shownotnew(){
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
		if(group!=null  && page!=null){
			try {
				List<Homework> homeworks=homeworkService.findHomeworkByClassIdForPage(false, group.getId(),(Integer.parseInt(page)-1)*Constants.ACTION_HOMEWORK_PAGELIMIT, Constants.ACTION_HOMEWORK_PAGELIMIT);
				for (int i = 0; i < homeworks.size(); i++) {
					Map<String,Object> map=new HashMap<String,Object>();
					Homework homework=homeworks.get(i);
					map.put("subjectName", homework.getSubjectName());
					map.put("subjectRequire", homework.getSubjectRequire());
					map.put("uploadDate", homework.getUploadDate().getTime());
					map.put("week", homework.getSubmitWeek());
					map.put("isNewest", false);
					homework=null;
					dataList.add(map);
					map=null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			dataMap.put("msg","展示最新作业");
		}else{
			dataMap.put("errorMsg","无法展示最新作业");
		}
		return "success";
	}
	
	
	
	
	
	/**
	 * 回显最新作业（不过期）
	 * @return
	 */
	@Action(value="/homework/shownew",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String shownew(){
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
		if(group!=null && page!=null){
			try {
				List<Homework> homeworks=homeworkService.findHomeworkByClassIdForPage(true, group.getId(),(Integer.parseInt(page)-1)*Constants.ACTION_HOMEWORK_PAGELIMIT, Constants.ACTION_HOMEWORK_PAGELIMIT);
				for (int i = 0; i < homeworks.size(); i++) {
					Map<String,Object> map=new HashMap<String,Object>();
					Homework homework=homeworks.get(i);
					map.put("subjectName", homework.getSubjectName());
					map.put("subjectRequire", homework.getSubjectRequire());
					map.put("uploadDate", homework.getUploadDate().getTime());
					map.put("week", homework.getSubmitWeek());
					map.put("isNewest", true);
					homework=null;
					dataList.add(map);
					map=null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			dataMap.put("msg","展示过期作业");
		}else{
			dataMap.put("errorMsg","展示过期作业失败");
		}
		return "success";
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}





	public String getSubjectName() {
		return subjectName;
	}




	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}




	public String getSubjectRequire() {
		return subjectRequire;
	}




	public void setSubjectRequire(String subjectRequire) {
		this.subjectRequire = subjectRequire;
	}









	public String getUploadDate() {
		return uploadDate;
	}




	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}




	public String getWeek() {
		return week;
	}




	public void setWeek(String week) {
		this.week = week;
	}




	public String getPage() {
		return page;
	}




	public void setPage(String page) {
		this.page = page;
	}




	public Boolean getIsNewest() {
		return isNewest;
	}




	public void setIsNewest(Boolean isNewest) {
		this.isNewest = isNewest;
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






}
