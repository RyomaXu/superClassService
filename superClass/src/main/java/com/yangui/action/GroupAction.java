package com.yangui.action;



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
import com.yangui.entity.Homework;
import com.yangui.entity.Photo;
import com.yangui.entity.User;
import com.yangui.service.impl.BroadcastService;
import com.yangui.service.impl.GroupService;
import com.yangui.service.impl.HomeworkService;
import com.yangui.service.impl.PhotoService;
import com.yangui.service.impl.UserService;
import com.yangui.utils.jpush.JpushUtil;
import com.yangui.utils.string.StringUtil;

@ParentPackage("dataJsonDefault")
@Service
public class GroupAction {
	//交互数据
	private String id;//用户Id
	private String schoolName;
	private String grade;
	private String major;
	private String secret;//班级暗号
	private String classNickname;
	private String maxNum;
	private String classSecret;//用户暗号

	private Map<String, Object> dataMap=new HashMap<String, Object>();;


	@Autowired
	private UserService userService;
	@Autowired
	private GroupService  groupService;
	@Autowired
	private PhotoService  photoService;
	@Autowired
	private BroadcastService  broadcastService;
	@Autowired
	private HomeworkService  homeworkService;


	/**
	 * 创建班级
	 * @return
	 */
	@Action(value="/group/create",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String create(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		if(id!=null && schoolName!=null && grade!=null && major!=null && secret!=null && classNickname!=null && maxNum!=null){
			if(!groupService.isSecretExisted(secret)){
				Group group=new Group();
				group.setOwnerId(Integer.parseInt(id));
				group.setSchoolName(schoolName);
				group.setGrade(grade);
				group.setMajor(major);
				group.setSecret(secret);
				group.setFlockName(classNickname);
				group.setMaxNum(Integer.parseInt(maxNum));
				List<User> users=new ArrayList<User>();
				User user=userService.getOne(Integer.parseInt(id));
				user.setInstitute(secret);
				users.add(user);
				group.setUsers(users);
				groupService.insert(group);
				user.setGroup(group);
				userService.update(user);
				
				dataMap.put("maxNum",group.getMaxNum());
				dataMap.put("ownerId", group.getOwnerId());
				dataMap.put("schoolName", group.getSchoolName());
				dataMap.put("major",group.getMajor());
				dataMap.put("grade", group.getGrade());
				dataMap.put("className", group.getFlockName());
				dataMap.put("secret", group.getSecret());
				dataMap.put("msg", "班级创建成功");
			}else{
				dataMap.put("errorMsg", "班级暗号已存在");
			}
		}else{
			dataMap.put("errorMsg", "班级创建失败");
		}
		return "success";
	}

	/**
	 * 加入班级
	 * @return
	 */
	@Action(value="/group/join",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String join(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		User user=null;
		if(id!=null){
		  user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null && classSecret!=null){
			user.setInstitute(classSecret);
			if(groupService.isSecretExisted(classSecret) && groupService.isInstituteEqualSecret(classSecret, Integer.parseInt(id))){
				Group group=groupService.getGroupBySecret(classSecret);
				user.setGroup(group);
				userService.update(user);
				List<User> users=new ArrayList<User>();
				users.add(user);
				group.setUsers(users);
				groupService.update(group);
				
				dataMap.put("maxNum",group.getMaxNum());
				dataMap.put("ownerId", group.getOwnerId());
				dataMap.put("schoolName", group.getSchoolName());
				dataMap.put("major",group.getMajor());
				dataMap.put("grade", group.getGrade());
				dataMap.put("className", group.getFlockName());
				dataMap.put("secret", group.getSecret());
				dataMap.put("msg", "成功加入班级");
			}else if( !groupService.isSecretExisted(classSecret)){
				dataMap.put("errorMsg", "班级暗号不存在");
			}else{
				dataMap.put("errorMsg", "加入班级失败");
			}
		}
		
		return "success";
	}


	/**
	 * 班级首页
	 * @return
	 */
	@Action(value="/group/index",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String index(){
		User user=null;
		Group group=null;
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(group!=null){
			
			//照片部分
			List<Photo> photos=photoService.findPhotosByClassIdForPage(group.getId(),0, Constants.ACTION_PHOTO_PAGELIMIT);
			List<HashMap<String,Object>> photosInfo=new ArrayList<HashMap<String,Object>>();
			for (int i = 0; i < 8; i++) {//首页默认8张照片（最新）
				HashMap<String,Object>  map=new HashMap<String, Object>();
				Photo photo=photos.get(i);
				if(photo!=null){
					map.put("id",photo.getId());
					map.put("picUrl",Constants.ACTION_PHOTO_URLNOID+photo.getId());
					map.put("groupId", group.getId());
				}
				photosInfo.add(map);
				map=null;
			}
			dataMap.put("photos", photosInfo);
			
			//公告部分
			List<Broadcast> broadcasts=broadcastService.findBroadcastsForPage(group.getId(), 0, Constants.ACTION_BROADCAST_PAGELIMIT);
			for (int i = 0; i < 1; i++) {//首页取一条公告
				Broadcast broadcast=broadcasts.get(0);
				if(broadcast!=null){
					dataMap.put("broadcastContent",broadcast.getContent());
					dataMap.put("broadcastWriteDate",broadcast.getCreateTime().getTime());
				}
			}
			
			
			//作业部分
			List<Homework> homeworks=homeworkService.findHomeworkByClassIdForPage(true, group.getId(),0, Constants.ACTION_HOMEWORK_PAGELIMIT);
			for (int i = 0; i < 1; i++) {//首页取一条作业
				Homework homework=homeworks.get(0);
				if(homework!=null){
					dataMap.put("subjectName",homework.getSubjectName());
					dataMap.put("require",homework.getSubjectRequire());
					dataMap.put("uploadDateMillis",homework.getUploadDate().getTime());
					dataMap.put("writeDate",homework.getCreateTime().getTime());
				}
			}
			
			//首页照片TODO..要随机取班级里面的一张照片
			Photo  photo=photoService.getOne(100);
			if(photo!=null){
				dataMap.put("mainPicUrl", Constants.ACTION_PHOTO_URLNOID+photo.getId());
			}
			
			

			
		}
		return "success";
	}
	
	/**
	 * 班级点名
	 * @return
	 */
	@Action(value="/group/call",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String call(){
		User user=null;
		Group group=null;
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(group!=null){
			//极光推送
			List<User> users=userService.findUsersByClassId(group.getId());
			StringUtil<User> userStringUtil=new StringUtil<User>();
			String alias=userStringUtil.getIDsToJsonArray(users);
			String json="{'title':'点名','alert':点名啦!!~,'alias':"+alias+"}";
			JSONObject jsonObject=null;
			try {
				jsonObject = new JSONObject(json);
				JpushUtil.push(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			dataMap.put("msg", "成功提醒点名");
		}else{
			dataMap.put("errorMsg", "提醒点名失败");
		}
		return "success";
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getClassNickname() {
		return classNickname;
	}

	public void setClassNickname(String classNickname) {
		this.classNickname = classNickname;
	}

	public String getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}

	public String getClassSecret() {
		return classSecret;
	}

	public void setClassSecret(String classSecret) {
		this.classSecret = classSecret;
	}

	//使json有效
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}


}
