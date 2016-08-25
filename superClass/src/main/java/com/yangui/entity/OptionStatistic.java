package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 *选项统计 
 * The persistent class for the tb_option_statistics database table.
 */
@Entity
@Table(name="tb_option_statistics")
@NamedQuery(name="OptionStatistic.findAll", query="SELECT o FROM OptionStatistic o")
public class OptionStatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date actualtime;

	private int count;

	@Column(name="option_id")
	private Integer optionId;


	@Column(name="user_id")
	private Integer userId;

	//bi-directional many-to-one association to Vote
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="vote_id")
	private Vote Vote;

	public OptionStatistic() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getActualtime() {
		return this.actualtime;
	}

	public void setActualtime(Date actualtime) {
		this.actualtime = actualtime;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	public Integer getOptionId() {
		return optionId;
	}


	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Vote getVote() {
		return Vote;
	}


	public void setVote(Vote vote) {
		Vote = vote;
	}




}