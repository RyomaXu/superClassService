package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * 作业
 * The persistent class for the tb_homework database table.
 */
@Entity
@Table(name="tb_homework")
@NamedQuery(name="Homework.findAll", query="SELECT h FROM Homework h")
public class Homework implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String subjectRequire;//作业要求

	private String subjectName;//作业题目
	

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;

	private String submitWeek;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="class_id")
	private Group Group;

	public Homework() {
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

	public Date getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}


	public String getSubjectRequire() {
		return subjectRequire;
	}


	public void setSubjectRequire(String subjectRequire) {
		this.subjectRequire = subjectRequire;
	}


	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}


	public String getSubmitWeek() {
		return submitWeek;
	}


	public void setSubmitWeek(String submitWeek) {
		this.submitWeek = submitWeek;
	}


	public Group getGroup() {
		return this.Group;
	}

	public void setGroup(Group Group) {
		this.Group = Group;
	}


	
}