package com.yangui.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.List;


/**
 * 选项
 * The persistent class for the tb_option database table.
 */
@Entity
@Table(name="tb_option")
@NamedQuery(name="Option.findAll", query="SELECT o FROM Option o")
public class Option implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	private String code;

	private String itemName;
	
	
	private Boolean isSelected;
	
	private Integer supporterNum;

	//bi-directional many-to-one association to Vote
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="vote_id")
	private Vote Vote;


	public Option() {
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getCode() {
		return code;
	}





	public void setCode(String code) {
		this.code = code;
	}






	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}



	public Boolean getIsSelected() {
		return isSelected;
	}



	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}



	public Integer getSupporterNum() {
		return supporterNum;
	}



	public void setSupporterNum(Integer supporterNum) {
		this.supporterNum = supporterNum;
	}



	public Vote getVote() {
		return this.Vote;
	}

	public void setVote(Vote Vote) {
		this.Vote = Vote;
	}



}