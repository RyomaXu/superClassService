package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Feedback;

@Repository(value="feedbackDao")
public class FeedbackDao implements Dao<Feedback> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Feedback t) {
		hibernateTemplate.save(t);

	}
	public void delete(int id) {
		String hql="from Feedback where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}
	public Feedback update(Feedback t) {
		hibernateTemplate.update(t);
		return t;
	}


	public Feedback getOne(int id) {
		String hql="from Feedback where id = ? ";
		Feedback feedback=null;
		try {
			List<Feedback> feedbacks=	(List<Feedback>) hibernateTemplate.find(hql, id);
			if(feedbacks!=null && feedbacks.size()>=1){
				feedback=feedbacks.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedback;
	}


	public List<Feedback> findAllList() {
		String hql="from Feedback";
		List<Feedback> feedbacks=(List<Feedback>)hibernateTemplate.find(hql);
		return feedbacks;
	}



}
