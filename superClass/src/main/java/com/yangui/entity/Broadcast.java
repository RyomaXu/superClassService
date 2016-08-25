package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;


/**
 * 班级公告内容
 * The persistent class for the tb_broadcast database table.
 */
@Entity
@Table(name="tb_broadcast")
@NamedQuery(name="Broadcast.findAll", query="SELECT b FROM Broadcast b")
public class Broadcast implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	private String  content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="class_id")
	private Group Group;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="Broadcast")
	private List<Comment> Comments;

	public Broadcast() {
	}

	












	public Integer getId() {
		return id;
	}














	public void setId(Integer id) {
		this.id = id;
	}














	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Group getGroup() {
		return this.Group;
	}

	public void setGroup(Group Group) {
		this.Group = Group;
	}

	public List<Comment> getComments() {
		return this.Comments;
	}

	public void setComments(List<Comment> Comments) {
		this.Comments = Comments;
	}


}