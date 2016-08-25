package com.yangui.action;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionSupport;
import com.yangui.common.Constants;
import com.yangui.entity.Group;
import com.yangui.entity.Photo;
import com.yangui.entity.Photostamp;
import com.yangui.entity.User;
import com.yangui.service.impl.GroupService;
import com.yangui.service.impl.PhotoService;
import com.yangui.service.impl.PhotostampService;
import com.yangui.service.impl.UserService;

@ParentPackage("dataJsonDefault")
@Service
public class PhotoAction extends ActionSupport{
	private static Logger     logger = LoggerFactory.getLogger(PhotoAction.class);

	//交互数据
	private String id;//用户Id
	private String  photoId;//照片Id
	private File[] file;
	private String[] fileName;//文件名setFileFileName
	private String stampId;//相册ID
	private String page;



	//自定义数据
	private String errorMsg;

	//单个Json
	private Map<String, Object> dataMap=new HashMap<String, Object>();
	//Json数组
	private List<Object> dataList=new ArrayList<Object>();




	@Autowired
	private UserService userService;
	@Autowired
	private GroupService  groupService;
	@Autowired
	private PhotoService  photoService;
	@Autowired
	private PhotostampService  photostampService;




	/**
	 * 初始化，提供给安卓端班级照片的所有URL
	 * @return
	 */
	@Action(value="/photo/init",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String init(){
		User user=null;
		Group group=null;
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(group!=null){
			List<Photo> photos=photoService.findPhotosByClassIdForPage(group.getId(), (Integer.parseInt(page)-1)*Constants.ACTION_PHOTO_PAGELIMIT, Constants.ACTION_PHOTO_PAGELIMIT);
			for (int i = 0; i <photos.size(); i++) {
				try {
					int picId=photos.get(i).getId();
					String picUrl=Constants.ACTION_PHOTO_URLNOID+picId;
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("id", picId);
					map.put("picUrl", picUrl);
					dataList.add(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "success";
	}



	/**
	 * 增加照片到已有的相册
	 * @return
	 */
	@Action(value="/photo/upload",results={
			@Result(name="success",params={"root","dataMap","maximumSize","1024*1024*600",},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String upload(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		//处理文件超出范围
		if(errorMsg!=null && "文件超出指定的大小，请重新上传".equals(errorMsg)){
			dataMap.put("errorMsg", errorMsg);
			return "success";
		}
		User user=null;
		Group group=null;
		Photo photo=new Photo();
		Photostamp photostamp=null;
		if(id!=null&& stampId!=null){
			user=userService.getOne(Integer.parseInt(id));
			photostamp=photostampService.getOne(Integer.parseInt(stampId));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(user!=null && group!=null && photostamp!=null){
			List<Photo> photos=new ArrayList<Photo>();
		
			for(int i = 0; i < file.length; i++) {
				try {
					photo=photoService.savePhotoInfo(fileName[i], photo, user, group);
					photo.setPhotostamp(photostamp);
					photoService.insert(photo);
					photos.add(photo);
					FileUtils.copyFile(file[i], new File(photo.getActualUrl()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			user.setPhotos(photos);
			userService.update(user);
			group.setPhotos(photos);
			groupService.update(group);
			photostamp.setPhotos(photos);
			photostampService.update(photostamp);
			dataMap.put("msg","上传成功");
		}else{
			dataMap.put("msg","上传失败");
		}

		return "success";
	}



	/**
	 * 展示相片(无相册)
	 * @return
	 */
	@Action(value="/photo/show",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="show",location="/show.jsp")})
	public String show(){
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream os=null;
		FileInputStream is=null;
		try {
			if(photoId!=null){
				os=response.getOutputStream();
				is=new FileInputStream(photoService.getOne(Integer.parseInt(photoId)).getActualUrl());
				byte[] buffer=new byte[1024];
				int a=0;
				while((a=is.read(buffer))!=-1){
					os.write(buffer, 0, a);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;//不跳转，保证流输出
	}

	
	
	/**
	 * 这里要先判断一下，是我们要替换的错误，处理文件上传超出范围的业务
	 */
	@Override
	public void addActionError(String anErrorMessage) {    
		if (anErrorMessage.startsWith("the request was rejected because its size")) {    
			errorMsg="文件超出指定的大小，请重新上传";
		}
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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










	public String getPhotoId() {
		return photoId;
	}



	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}





	public String getStampId() {
		return stampId;
	}



	public void setStampId(String stampId) {
		this.stampId = stampId;
	}



	public String getPage() {
		return page;
	}



	public void setPage(String page) {
		this.page = page;
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
