package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * 评论
 * The persistent class for the tb_comment database table.
 */
@Entity
@Table(name="tb_comment")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private int pid;

	private String Content;

	private String type;

	//bi-directional many-to-one association to Broadcast
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="broadcast_id")
	private Broadcast Broadcast;

	//bi-directional many-to-one association to GroupActivity
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="groupActivity_id")
	private GroupActivity GroupActivity;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User User;

	//bi-directional many-to-one association to Vote
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="vote_id")
	private Vote Vote;

	//bi-directional many-to-one association to Broadcast
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="photo_id")
	private Photo Photo;

	public Comment() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}



	public String getContent() {
		return Content;
	}



	public void setContent(String content) {
		Content = content;
	}



	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Broadcast getBroadcast() {
		return this.Broadcast;
	}

	public void setBroadcast(Broadcast Broadcast) {
		this.Broadcast = Broadcast;
	}

	public GroupActivity getGroupActivity() {
		return this.GroupActivity;
	}

	public void setGroupActivity(GroupActivity GroupActivity) {
		this.GroupActivity = GroupActivity;
	}

	public User getUser() {
		return this.User;
	}

	public void setUser(User User) {
		this.User = User;
	}

	public Vote getVote() {
		return this.Vote;
	}

	public void setVote(Vote Vote) {
		this.Vote = Vote;
	}


	public Photo getPhoto() {
		return Photo;
	}


	public void setPhoto(Photo photo) {
		Photo = photo;
	}

	
}