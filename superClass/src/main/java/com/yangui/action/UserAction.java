package com.yangui.action;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yangui.common.Constants;
import com.yangui.entity.Photo;
import com.yangui.entity.User;
import com.yangui.service.impl.PhotoService;
import com.yangui.service.impl.UserService;

@ParentPackage("dataJsonDefault")
@Service
public class UserAction {
	//交互数据
	private User user;
	private String id;
	private String nickName;
	private String password;
	private String telNum;
	private String institute;
	private String position;
	private File[] file;
	private String[] fileName;//文件名setFileFileName

	private List<User> users=new ArrayList<User>();
	private Map<String, Object> dataMap=new HashMap<String, Object>();


	@Autowired
	private UserService userService;
	@Autowired
	private PhotoService  photoService;

	/**
	 * 用户登陆
	 * @return
	 */
	@Action(value="/user/login",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String login(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		System.out.println("--------");
		User user=null;
		if( password!=null && telNum!=null ){
			user=userService.getUserByTelNumAndPassword(telNum,password);
			if(user!=null && !(userService.isTelNum(telNum))){
				dataMap.put("userName",user.getNickName());
				dataMap.put("institute",user.getInstitute());
				dataMap.put("position",user.getPosition());
				dataMap.put("id",user.getId());
				dataMap.put("avatorPath",Constants.ACTION_PHOTO_URLNOID+user.getAvator());
			}else if(user==null && !(userService.isTelNum(telNum))){
				dataMap.put("errorMsg","用户或密码不正确");
			}else{
				dataMap.put("errorMsg","用户不存在");
			}
		}
		return "success";
	}

	
	/**
	 * 用户注册
	 * @return
	 */
	@Action(value="/user/regist",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String regist(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		System.out.println("--------");
		User user=new User();
		if(nickName!=null && password!=null && telNum!=null &&  userService.isTelNum(telNum)){
			user.setNickName(nickName);
			user.setPassword(password);
			user.setInstitute(institute);
			user.setTelNum(telNum);
			user.setAvator(0);
			user.setPosition("member");
			dataMap.put("msg","成功注册");
			userService.insert(user);
		}else{
			dataMap.put("errorMsg","注册不成功");
		}
		return "success";
	}


	/**
	 * 用户重置密码
	 * @return
	 */
	@Action(value="/user/reset",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String reset(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		User user=null;
		if( password!=null && telNum!=null &&  !(userService.isTelNum(telNum))){
			user=userService.getUserByTelNum(telNum);
			if(user!=null){
				user.setPassword(password);
				userService.update(user);
				dataMap.put("userName",user.getNickName());
				dataMap.put("institute",user.getInstitute());
				dataMap.put("id",user.getId());
				dataMap.put("id",user.getInstitute());
				dataMap.put("msg","密码修改成功");
			}else{
				dataMap.put("errorMsg","密码修改不成功");
			}
		}
		return "success";
	}
	
	/**
	 * 用户修改信息
	 * @return
	 */
	@Action(value="/user/changemsg",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String changemsg(){
	User user=null;
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			//上传头像
			Photo photo=new Photo();
			if(file!=null && file.length==1 && file[0]!=null){
				for(int i = 0; i < file.length; i++) {
					try {
						photo=photoService.savePhotoInfo(fileName[i], photo, user, null);
						photoService.insert(photo);
						FileUtils.copyFile(file[i], new File(photo.getActualUrl()));
						user.setAvator(photo.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		
			//昵称和职责
			user.setNickName(nickName);
			user.setPosition(position);
			userService.update(user);
			dataMap.put("msg", "成功修改用户信息");
		}else{
			dataMap.put("errorMsg", "修改用户信息失败");
		}
		return "success";
	}

	


	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}






	public String getNickName() {
		return nickName;
	}






	public void setNickName(String nickName) {
		this.nickName = nickName;
	}






	public String getPassword() {
		return password;
	}






	public void setPassword(String password) {
		this.password = password;
	}






	public String getTelNum() {
		return telNum;
	}






	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}






	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getInstitute() {
		return institute;
	}


	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public File[] getFile() {
		return file;
	}




	public void setFile(File[] file) {
		this.file = file;
	}
	public String[] getFileName() {
		return fileName;
	}




	public void setFileFileName(String[] fileName) {
		this.fileName = fileName;
	}
	
	
	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	//使json有效
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}


}
