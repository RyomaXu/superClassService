package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Photo;

@Repository(value="photoDao")
public class PhotoDao implements Dao<Photo> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Photo t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from Photo where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Photo update(Photo t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Photo getOne(int id) {
		String hql="from Photo where id = ? ";
		Photo photo=null;
		try {
			List<Photo> photos=	(List<Photo>) hibernateTemplate.find(hql, id);
			if(photos!=null && photos.size()>=1){
				photo=photos.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photo;
	}

	public List<Photo> findAllList() {
		String hql="from Photo";
		List<Photo> photoes=(List<Photo>)hibernateTemplate.find(hql);
		return photoes;
	}



}
