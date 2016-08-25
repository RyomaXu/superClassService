package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * 通知消息
 * The persistent class for the tb_notifymessage database table.
 */
@Entity
@Table(name="tb_notifymessage")
@NamedQuery(name="Notifymessage.findAll", query="SELECT n FROM Notifymessage n")
public class Notifymessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	private String broadcastMessage;

	private String homeWorkMessage;

	private int msgType;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="class_id")
	private Group Group;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User User;

	public Notifymessage() {
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getBroadcastMessage() {
		return this.broadcastMessage;
	}

	public void setBroadcastMessage(String broadcastMessage) {
		this.broadcastMessage = broadcastMessage;
	}

	public String getHomeWorkMessage() {
		return this.homeWorkMessage;
	}

	public void setHomeWorkMessage(String homeWorkMessage) {
		this.homeWorkMessage = homeWorkMessage;
	}

	public int getMsgType() {
		return this.msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public Group getGroup() {
		return this.Group;
	}

	public void setGroup(Group Group) {
		this.Group = Group;
	}

	public User getUser() {
		return this.User;
	}

	public void setUser(User User) {
		this.User = User;
	}

}