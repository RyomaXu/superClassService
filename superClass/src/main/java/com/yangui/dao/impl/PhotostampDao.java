package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Photostamp;

@Repository(value="photostampDao")
public class PhotostampDao implements Dao<Photostamp> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Photostamp t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from Photostamp where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Photostamp update(Photostamp t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Photostamp getOne(int id) {
		String hql="from Photostamp where id = ? ";
		Photostamp photostamp=	null;
		try {
			List<Photostamp> photostamps=	(List<Photostamp>) hibernateTemplate.find(hql, id);
			if(photostamps!=null && photostamps.size()>=1){
				photostamp=photostamps.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photostamp;
	}

	public List<Photostamp> findAllList() {
		String hql="from Photostamp";
		List<Photostamp> photostamps=(List<Photostamp>)hibernateTemplate.find(hql);
		return photostamps;
	}



}
