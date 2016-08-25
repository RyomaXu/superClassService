package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;


/**
 * 图片
 * The persistent class for the tb_photo database table.
 */
@Entity
@Table(name="tb_photo")
@NamedQuery(name="Photo.findAll", query="SELECT p FROM Photo p")
public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	private String compressPic;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	private String picUrl;

	private String actualName;

	private String displayName;

	private String actualUrl;


	//bi-directional many-to-one association to Photostamp
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="photoStamp_id")
	@Cascade({CascadeType.MERGE, CascadeType.SAVE_UPDATE})
	private Photostamp Photostamp;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User User;


	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="class_id")
	private Group Group;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="Photo")
	private List<Comment> Comments;

	public Photo() {
	}


	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCompressPic() {
		return compressPic;
	}


	public void setCompressPic(String compressPic) {
		this.compressPic = compressPic;
	}


	public String getPicUrl() {
		return picUrl;
	}


	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}




	public Photostamp getPhotostamp() {
		return this.Photostamp;
	}

	public void setPhotostamp(Photostamp Photostamp) {
		this.Photostamp = Photostamp;
	}


	public User getUser() {
		return User;
	}


	public void setUser(User user) {
		User = user;
	}


	public Group getGroup() {
		return Group;
	}


	public void setGroup(Group group) {
		Group = group;
	}


	public String getActualName() {
		return actualName;
	}


	public void setActualName(String actualName) {
		this.actualName = actualName;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getActualUrl() {
		return actualUrl;
	}


	public void setActualUrl(String actualUrl) {
		this.actualUrl = actualUrl;
	}


	public List<Comment> getComments() {
		return Comments;
	}


	public void setComments(List<Comment> comments) {
		Comments = comments;
	}







}