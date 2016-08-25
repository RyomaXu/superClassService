package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * 聊天消息
 * The persistent class for the tb_chatmessage database table.
 */
@Entity
@Table(name="tb_chatmessage")
@NamedQuery(name="Chatmessage.findAll", query="SELECT c FROM Chatmessage c")
public class Chatmessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String jsonMsg;

	private int msgType;

	private String notifyContent;

	private int notifyType;

	private String picPath;

	private String picUrl;

	private String textMsg;

	private String voicePath;

	private String voiceUrl;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="send_user__id")
	private User sendUser;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="rec_user__id")
	private User recUser;

	public Chatmessage() {
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public int getMsgType() {
		return this.msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}



	public int getNotifyType() {
		return this.notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}





	public String getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTextMsg() {
		return textMsg;
	}

	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}

	public String getVoicePath() {
		return voicePath;
	}

	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}


	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}

	public User getRecUser() {
		return recUser;
	}

	public void setRecUser(User recUser) {
		this.recUser = recUser;
	}


}