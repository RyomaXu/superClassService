package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Comment;

@Repository(value="commentDao")
public class CommentDao implements Dao<Comment> {
	
	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Comment t) {
		hibernateTemplate.save(t);
		
	}

	public void delete(int id) {
		String hql="from Comment where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Comment update(Comment t) {
		hibernateTemplate.update(t);
		return t;
	}

	
	public Comment getOne(int id) {
		String hql="from Comment where id = ? ";
		Comment comment=null;
		try {
			List<Comment> comments=	(List<Comment>) hibernateTemplate.find(hql, id);
			if(comments!=null && comments.size()>=1){
				comment=comments.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comment;
	}

	public List<Comment> findAllList() {
		String hql="from Comment";
		List<Comment> comments=(List<Comment>)hibernateTemplate.find(hql);
		return comments;
	}



}
