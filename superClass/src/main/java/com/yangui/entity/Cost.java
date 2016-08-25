package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * 班级开销
 * The persistent class for the tb_cost database table.
 */
@Entity
@Table(name="tb_cost")
@NamedQuery(name="Cost.findAll", query="SELECT c FROM Cost c")
public class Cost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	private String cost;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String event;

	private String renew;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="class_id")
	private Group Group;

	public Cost() {
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


	public String getCost() {
		return cost;
	}



	public void setCost(String cost) {
		this.cost = cost;
	}



	public String getEvent() {
		return event;
	}



	public void setEvent(String event) {
		this.event = event;
	}



	public String getRenew() {
		return renew;
	}



	public void setRenew(String renew) {
		this.renew = renew;
	}



	public Group getGroup() {
		return this.Group;
	}

	public void setGroup(Group Group) {
		this.Group = Group;
	}

}