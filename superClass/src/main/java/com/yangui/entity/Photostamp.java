package com.yangui.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * 相册集
 * The persistent class for the tb_photostamp database table.
 * 
 */
@Entity
@Table(name="tb_photostamp")
@NamedQuery(name="Photostamp.findAll", query="SELECT p FROM Photostamp p")
public class Photostamp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String stampName;

	//bi-directional many-to-one association to Photo
	@OneToMany(fetch = FetchType.EAGER,mappedBy="Photostamp", cascade = javax.persistence.CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Photo> Photos;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="class_id")
	private Group Group;

	public Photostamp() {
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

	public String getStampName() {
		return this.stampName;
	}

	public void setStampName(String stampName) {
		this.stampName = stampName;
	}

	public List<Photo> getPhotos() {
		return this.Photos;
	}

	public void setPhotos(List<Photo> Photos) {
		this.Photos = Photos;
	}
	


	public Group getGroup() {
		return this.Group;
	}

	public void setGroup(Group Group) {
		this.Group = Group;
	}





}