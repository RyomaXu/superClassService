package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Option;

@Repository(value="optionDao")
public class OptionDao implements Dao<Option> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Option t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from Option where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Option update(Option t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Option getOne(int id) {
		String hql="from Option where id = ? ";
		Option option=	null;
		try {
			List<Option> options=	(List<Option>) hibernateTemplate.find(hql, id);
			if(options!=null && options.size()>=1){
				option=options.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return option;
	}

	public List<Option> findAllList() {
		String hql="from Option";
		List<Option> options=(List<Option>)hibernateTemplate.find(hql);
		return options;
	}



}
