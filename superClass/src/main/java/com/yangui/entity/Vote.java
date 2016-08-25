package com.yangui.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;


/**
 * 投票
 * The persistent class for the tb_vote database table.
 * 
 */
@Entity
@Table(name="tb_vote")
@NamedQuery(name="Vote.findAll", query="SELECT v FROM Vote v")
public class Vote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;


	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;


	private String voteTitle;

	private Integer voteType;
	
	private Boolean isMulty;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="Vote")
	private List<Comment> Comments;

	//bi-directional many-to-one association to Feedback
	@OneToMany(mappedBy="Vote")
	private List<Feedback> Feedbacks;

	//bi-directional many-to-one association to Option
	@OneToMany(mappedBy="Vote",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode. SUBSELECT)
	private List<Option> Options;
	
	//bi-directional many-to-one association to Option
	@OneToMany(mappedBy="Vote",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode. SUBSELECT)
	private List<OptionStatistic> OptionStatistics;
	
	//bi-directional many-to-one association to Option
	@OneToMany(mappedBy="Vote")
	private List<User> Users;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="class_id")
	private Group Group;

	public Vote() {
	}




	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	

	



	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getVoteTitle() {
		return voteTitle;
	}



	public void setVoteTitle(String voteTitle) {
		this.voteTitle = voteTitle;
	}



	public Integer getVoteType() {
		return voteType;
	}




	public void setVoteType(Integer voteType) {
		this.voteType = voteType;
	}




	public Boolean getIsMulty() {
		return isMulty;
	}




	public void setIsMulty(Boolean isMulty) {
		this.isMulty = isMulty;
	}




	public List<Comment> getComments() {
		return this.Comments;
	}

	public void setComments(List<Comment> Comments) {
		this.Comments = Comments;
	}



	public List<Feedback> getFeedbacks() {
		return this.Feedbacks;
	}

	public void setFeedbacks(List<Feedback> Feedbacks) {
		this.Feedbacks = Feedbacks;
	}



	public List<Option> getOptions() {
		return this.Options;
	}

	public void setOptions(List<Option> Options) {
		this.Options = Options;
	}



	public Group getGroup() {
		return this.Group;
	}

	public void setGroup(Group Group) {
		this.Group = Group;
	}




	public List<User> getUsers() {
		return Users;
	}




	public void setUsers(List<User> users) {
		Users = users;
	}




	public List<OptionStatistic> getOptionStatistics() {
		return OptionStatistics;
	}




	public void setOptionStatistics(List<OptionStatistic> optionStatistics) {
		OptionStatistics = optionStatistics;
	}

}