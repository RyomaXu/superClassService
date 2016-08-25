package com.yangui.action;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PhotostampAction extends ActionSupport{
	private static Logger     logger = LoggerFactory.getLogger(PhotostampAction.class);

	//交互数据
	private String id;//用户Id
	private String stampName;//相册名
	private String  photoId;
	private File[] file;
	private String[] fileName;//文件名setFileFileName
	private String photostampId;
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
	 * 创建相册并上传照片
	 * @return
	 */
	@Action(value="/photostamp/create",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	@Transactional
	public String create(){
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
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
	
		
		List<Photo> photos=new ArrayList<Photo>();
		Photostamp photostamp=new Photostamp();
		if(group!=null){
			photostamp.setGroup(group);
			photostamp.setStampName(stampName);
			photostampService.insert(photostamp);
		}
		if(user!=null && group!=null && file!=null){
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
		}
		if(group!=null){
			photostamp.setPhotos(photos);
			photostampService.update(photostamp);
			
			List<Photostamp> photostamps=new ArrayList<Photostamp>();
			photostamps.add(photostamp);
			group.setPhotos(photos);
			group.setPhotostamps(photostamps);
			groupService.update(group);
			
			user.setPhotos(photos);
			userService.update(user);
			dataMap.put("msg", "创建相册成功");
		}
		return "success";
	}



	/**
	 * 展示班级所有相册以及里面的相片
	 * @return
	 */
	@Action(value="/photostamp/show",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="show",location="/show.jsp")})
	public String show(){
		dataList.clear();
		User user=null;
		Group group=null;
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(group!=null){
			List<Photostamp> photostamps=new ArrayList<Photostamp>();
			if("-1".equals(page)){//-1表示客户端一次取全部
				photostamps	=photostampService.findPhotostampsByClassId(group.getId());
			}else{
				 photostamps=photostampService.findPhotostampsByClassIdForPage(group.getId(), (Integer.parseInt(page)-1)*Constants.ACTION_PHOTOSTAMP_PAGELIMIT, Constants.ACTION_PHOTOSTAMP_PAGELIMIT);
			}
			for (int i = 0; i < photostamps.size(); i++) {
				try {
					Map<String,Object> map=new IdentityHashMap<String,Object>();
					List<Photo> photos=photostamps.get(i).getPhotos();
					List<Map<String,Object>> picUrlIds=new ArrayList<Map<String,Object>>();
					for (int j = 0; j < photos.size(); j++) {
						HashMap<String, Object>m=new HashMap<String, Object>(2);
						int picId=photos.get(j).getId();
						String picUrl=Constants.ACTION_PHOTO_URLNOID+picId;
						m.put("id", picId);
						m.put("picUrl", picUrl);
						picUrlIds.add(m);
					}
					map.put("stampName", photostamps.get(i).getStampName());
					map.put("id", photostamps.get(i).getId());
					map.put("photoCount", photos.size());
					map.put("photos", picUrlIds);
				
					dataList.add(map);//[{"photos":{"picUrl":[]}}  ]
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "success";
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





	public String getStampName() {
		return stampName;
	}



	public void setStampName(String stampName) {
		this.stampName = stampName;
	}



	public String getPhotoId() {
		return photoId;
	}



	public void setPhotoId(String photoId) {
		this.photoId = photoId;
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





	public String getPhotostampId() {
		return photostampId;
	}



	public void setPhotostampId(String photostampId) {
		this.photostampId = photostampId;
	}



	public String getPage() {
		return page;
	}



	public void setPage(String page) {
		this.page = page;
	}



	public String getErrorMsg() {
		return errorMsg;
	}



	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
