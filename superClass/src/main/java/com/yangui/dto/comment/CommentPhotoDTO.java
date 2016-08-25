package com.yangui.dto.comment;

/**
 * 照片评论DTO
 * @author yan gui
 *
 */
public class CommentPhotoDTO {
	private int fromUserId;
	private String fromUserName;
	private int fromUserAvatorUrl;
	private String fromUserContent;
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getFromUserContent() {
		return fromUserContent;
	}
	public void setFromUserContent(String fromUserContent) {
		this.fromUserContent = fromUserContent;
	}
	
	public int getFromUserAvatorUrl() {
		return fromUserAvatorUrl;
	}
	public void setFromUserAvatorUrl(int fromUserAvatorUrl) {
		this.fromUserAvatorUrl = fromUserAvatorUrl;
	}
	public CommentPhotoDTO() {
	}
	public CommentPhotoDTO(int fromUserId, String fromUserName,
			int fromUserAvatorUrl, String fromUserContent) {
		super();
		this.fromUserId = fromUserId;
		this.fromUserName = fromUserName;
		this.fromUserAvatorUrl = fromUserAvatorUrl;
		this.fromUserContent = fromUserContent;
	}

}
