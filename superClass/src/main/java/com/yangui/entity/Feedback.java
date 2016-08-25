package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * 反馈
 * The persistent class for the tb_feedback database table.
 */
@Entity
@Table(name="tb_feedback")
@NamedQuery(name="Feedback.findAll", query="SELECT f FROM Feedback f")
public class Feedback implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	private String details;

	@Temporal(TemporalType.TIMESTAMP)
	private Date feedbackDate;

	private String nickName;

	private String telNum;

	//bi-directional many-to-one association to Vote
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="vote_id")
	private Vote Vote;

	public Feedback() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getFeedbackDate() {
		return this.feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTelNum() {
		return this.telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public Vote getVote() {
		return this.Vote;
	}

	public void setVote(Vote Vote) {
		this.Vote = Vote;
	}

}