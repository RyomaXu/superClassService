package com.yangui.action;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yangui.common.Constants;
import com.yangui.dto.comment.CommentPhotoDTO;
import com.yangui.entity.Comment;
import com.yangui.entity.Photo;
import com.yangui.entity.User;
import com.yangui.service.impl.CommentService;
import com.yangui.service.impl.GroupService;
import com.yangui.service.impl.PhotoService;
import com.yangui.service.impl.UserService;

@ParentPackage("dataJsonDefault")
@Service
public class CommentAction {
	//交互数据
	//安卓传过来的数据
	private String id;//用户Id
	private String photoId;//照片ID
	private String content;//评论内容
	private String page;//页码
	//传给安卓的数据
	private int fromUserId;
	private String fromUserAvatorUrl;
	private String fromUserContent;
	private String fromUserName;

	//单个Json
	private Map<String, Object> dataMap=new HashMap<String, Object>();
	//Json数组
	private List<Object> dataList=new ArrayList<Object>();


	@Autowired
	private UserService userService;
	@Autowired
	private GroupService  groupService;
	@Autowired
	private CommentService  commentService;
	@Autowired
	private PhotoService  photoService;



	




	/**
	 * 发表照片评论
	 * @return
	 */
	@Action(value="/comment/photopublish",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String photopublish(){
		// dataList中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		User user=null;
		Photo photo=null;
		if(id!=null && photoId!=null && content!=null){
			user=userService.getOne(Integer.parseInt(id));
			photo=photoService.getOne(Integer.parseInt(photoId));
		}else{
			dataMap.put("errorMsg", "未评论任何内容");
		}
		if(user!=null && photo!=null ){
			Comment comment=new Comment();
			comment.setContent(content);
			comment.setPhoto(photo);
			comment.setUser(user);
			commentService.insert(comment);
			
			List<Comment> comments=new ArrayList<Comment>();
			comments.add(comment);
			user.setComments(comments);
			userService.update(user);
			photo.setComments(comments);
			photoService.update(photo);
			dataMap.put("msg", "照片评论成功");
		}
		return "success";
	}

	/**
	 * 回显照片评论
	 * @return
	 */
	@Action(value="/comment/photoshow",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String photoshow(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataList.clear();
		Photo photo=null;
		List<CommentPhotoDTO> commentPhotoDTOs=new ArrayList<CommentPhotoDTO>();
		if(photoId!=null){
			photo=photoService.getOne(Integer.parseInt(photoId));
		}else{
			dataMap.put("errorMsg", "照片不存在");
		}
		if(photo!=null){
			commentPhotoDTOs=commentService.findCommentPhotoDTOByPhotoId(photo.getId(),(Integer.parseInt(page)-1)*Constants.ACTION_COMMENT_PHOTO_PAGE, Constants.ACTION_COMMENT_PHOTO_PAGE);
			for (int i = 0; i < commentPhotoDTOs.size(); i++) {
				Map<String,Object> map=new HashMap<String,Object>();
				CommentPhotoDTO commentPhotoDTO=commentPhotoDTOs.get(i);
				map.put("fromUserId", commentPhotoDTO.getFromUserId());
				map.put("fromUserName", commentPhotoDTO.getFromUserName());
				map.put("fromUserAvatorUrl", Constants.ACTION_PHOTO_URLNOID+commentPhotoDTO.getFromUserAvatorUrl());
				map.put("fromUserContent", commentPhotoDTO.getFromUserContent());
				dataList.add(map);
				map=null;
				commentPhotoDTO=null;
			}
		}
		return "success";
	}





	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}




	public String getPhotoId() {
		return photoId;
	}




	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}




	public int getFromUserId() {
		return fromUserId;
	}




	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}




	public String getFromUserAvatorUrl() {
		return fromUserAvatorUrl;
	}




	public void setFromUserAvatorUrl(String fromUserAvatorUrl) {
		this.fromUserAvatorUrl = fromUserAvatorUrl;
	}




	public String getFromUserContent() {
		return fromUserContent;
	}




	public void setFromUserContent(String fromUserContent) {
		this.fromUserContent = fromUserContent;
	}




	public String getFromUserName() {
		return fromUserName;
	}




	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}




	public String getContent() {
		return content;
	}




	public void setContent(String content) {
		this.content = content;
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
