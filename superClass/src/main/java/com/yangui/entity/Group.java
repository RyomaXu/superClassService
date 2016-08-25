package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;


/**
 * 班级
 * The persistent class for the tb_group database table.
 */
@Entity
@Table(name="tb_group")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String flockId;

	private String flockName;//群名

	private String flockSignal;

	private int maxNum;

	private int ownerId;

	
	private String schoolName;//学校
	
    private String major;//专业
    
    private String grade;//年级
    
    private String secret;//暗号
    
    private Integer flockAvator;//群头像

	//bi-directional many-to-one association to Broadcast
	@OneToMany(mappedBy="Group",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Broadcast> Broadcasts;

	//bi-directional many-to-one association to Cost
	@OneToMany(mappedBy="Group")
	private List<Cost> Costs;


	//bi-directional many-to-one association to GroupActivity
	@OneToMany(mappedBy="Group")
	private List<GroupActivity> GroupActivities;

	//bi-directional many-to-one association to Homework
	@OneToMany(mappedBy="Group",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Homework> Homeworks;

	//bi-directional many-to-one association to Homework
	@OneToMany(mappedBy="Group")
	private List<Vote> Votes;
	
	
	//bi-directional many-to-one association to Notifymessage
	@OneToMany(mappedBy="Group")
	private List<Notifymessage> Notifymessages;
	
	//bi-directional many-to-one association to Notifymessage
	@OneToMany(mappedBy="Group")
	private List<Photo> Photos;

	//bi-directional many-to-one association to Photostamp
	@OneToMany(mappedBy="Group",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Photostamp> Photostamps;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="Group")
	private List<User> Users;


	public Group() {
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


	public String getFlockId() {
		return flockId;
	}



	public void setFlockId(String flockId) {
		this.flockId = flockId;
	}



	public String getFlockName() {
		return flockName;
	}



	public void setFlockName(String flockName) {
		this.flockName = flockName;
	}



	public String getFlockSignal() {
		return flockSignal;
	}



	public void setFlockSignal(String flockSignal) {
		this.flockSignal = flockSignal;
	}



	public int getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public List<Broadcast> getBroadcasts() {
		return this.Broadcasts;
	}

	public void setBroadcasts(List<Broadcast> Broadcasts) {
		this.Broadcasts = Broadcasts;
	}


	public List<Cost> getCosts() {
		return this.Costs;
	}

	public void setCosts(List<Cost> Costs) {
		this.Costs = Costs;
	}



	public Integer getFlockAvator() {
		return flockAvator;
	}

	public void setFlockAvator(Integer flockAvator) {
		this.flockAvator = flockAvator;
	}

	public List<GroupActivity> getGroupActivities() {
		return this.GroupActivities;
	}

	public void setGroupActivities(List<GroupActivity> GroupActivities) {
		this.GroupActivities = GroupActivities;
	}


	public List<Homework> getHomeworks() {
		return this.Homeworks;
	}

	public void setHomeworks(List<Homework> Homeworks) {
		this.Homeworks = Homeworks;
	}


	public List<Notifymessage> getNotifymessages() {
		return this.Notifymessages;
	}

	public void setNotifymessages(List<Notifymessage> Notifymessages) {
		this.Notifymessages = Notifymessages;
	}


	public List<Photostamp> getPhotostamps() {
		return this.Photostamps;
	}

	public void setPhotostamps(List<Photostamp> Photostamps) {
		this.Photostamps = Photostamps;
	}


	public List<User> getUsers() {
		return this.Users;
	}

	public void setUsers(List<User> Users) {
		this.Users = Users;
	}




	public List<Vote> getVotes() {
		return Votes;
	}



	public void setVotes(List<Vote> votes) {
		Votes = votes;
	}





	public String getSchoolName() {
		return schoolName;
	}





	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}





	public String getMajor() {
		return major;
	}





	public void setMajor(String major) {
		this.major = major;
	}





	public String getGrade() {
		return grade;
	}





	public void setGrade(String grade) {
		this.grade = grade;
	}





	public String getSecret() {
		return secret;
	}





	public void setSecret(String secret) {
		this.secret = secret;
	}

	public List<Photo> getPhotos() {
		return Photos;
	}

	public void setPhotos(List<Photo> photos) {
		Photos = photos;
	}

}