package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Primary;

import java.util.Date;
import java.util.List;


/**
 * 用户
 * The persistent class for the tb_user database table.
 * 
 */
@Entity
@Table(name="tb_user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User  extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String nickName;

	private String password;

	private String position;

	private String telNum;

	private String institute;//班级暗号

	private Integer avator;//头像Id

	//bi-directional many-to-one association to Chatmessage
	@OneToMany(mappedBy="sendUser")
	private List<Chatmessage> sendChatmessages;

	//bi-directional many-to-one association to Chatmessage
	@OneToMany(mappedBy="recUser")
	private List<Chatmessage> recChatmessages;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="User")
	private List<Comment> Comments;


	//bi-directional many-to-one association to Notifymessage
	@OneToMany(mappedBy="User")
	private List<Notifymessage> Notifymessages;

	//bi-directional many-to-one association to Notifymessage
	@OneToMany(mappedBy="User")
	private List<Photo> Photos;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="class_id")
	private Group Group;
	
	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="vote_id")
	private Vote Vote;



	public User() {
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

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTelNum() {
		return this.telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}



	public List<Chatmessage> getSendChatmessages() {
		return sendChatmessages;
	}

	public void setSendChatmessages(List<Chatmessage> sendChatmessages) {
		this.sendChatmessages = sendChatmessages;
	}

	public List<Chatmessage> getRecChatmessages() {
		return recChatmessages;
	}

	public void setRecChatmessages(List<Chatmessage> recChatmessages) {
		this.recChatmessages = recChatmessages;
	}

	public List<Comment> getComments() {
		return this.Comments;
	}

	public void setComments(List<Comment> Comments) {
		this.Comments = Comments;
	}



	public List<Notifymessage> getNotifymessages() {
		return this.Notifymessages;
	}

	public void setNotifymessages(List<Notifymessage> Notifymessages) {
		this.Notifymessages = Notifymessages;
	}


	public Group getGroup() {
		return this.Group;
	}

	public void setGroup(Group Group) {
		this.Group = Group;
	}




	public Integer getAvator() {
		return avator;
	}


	public void setAvator(Integer avator) {
		this.avator = avator;
	}












	public String getInstitute() {
		return institute;
	}






	public void setInstitute(String institute) {
		this.institute = institute;
	}


	public List<Photo> getPhotos() {
		return Photos;
	}


	public void setPhotos(List<Photo> photos) {
		Photos = photos;
	}


	public Vote getVote() {
		return Vote;
	}


	public void setVote(Vote vote) {
		Vote = vote;
	}



}