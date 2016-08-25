package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;


/**
 * 团体活动
 * The persistent class for the tb_group_activity database table.
 */
@Entity
@Table(name="tb_group_activity")
@NamedQuery(name="GroupActivity.findAll", query="SELECT g FROM GroupActivity g")
public class GroupActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	private String actName;

	private String cost;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	private String locationString;

	private int signCount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="GroupActivity")
	private List<Comment> Comments;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="class_id")
	private Group Group;

	public GroupActivity() {
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


	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getActName() {
		return actName;
	}



	public void setActName(String actName) {
		this.actName = actName;
	}



	public String getCost() {
		return cost;
	}



	public void setCost(String cost) {
		this.cost = cost;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getLocationString() {
		return locationString;
	}



	public void setLocationString(String locationString) {
		this.locationString = locationString;
	}



	public int getSignCount() {
		return this.signCount;
	}

	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<Comment> getComments() {
		return this.Comments;
	}

	public void setComments(List<Comment> Comments) {
		this.Comments = Comments;
	}


	public Group getGroup() {
		return this.Group;
	}

	public void setGroup(Group Group) {
		this.Group = Group;
	}

}