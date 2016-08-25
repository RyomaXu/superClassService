package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Homework;

@Repository(value="homeworkDao")
public class HomeworkDao implements Dao<Homework> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Homework t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from Homework where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Homework update(Homework t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Homework getOne(int id) {
		String hql="from Homework where id = ? ";
		Homework homework=	null;
		try {
			List<Homework> homeworks=	(List<Homework>) hibernateTemplate.find(hql, id);
			if(homeworks!=null && homeworks.size()>=1){
				homework=homeworks.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return homework;
	}

	public List<Homework> findAllList() {
		String hql="from Homework";
		List<Homework> homeworks=(List<Homework>)hibernateTemplate.find(hql);
		return homeworks;
	}



}
