package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Cost;

@Repository(value="costDao")
public class CostDao implements Dao<Cost> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Cost t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from Cost where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Cost update(Cost t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Cost getOne(int id) {
		String hql="from Cost where id = ? ";
		Cost cost=	null;
		try {
			List<Cost> costs=	(List<Cost>) hibernateTemplate.find(hql, id);
			if(costs!=null && costs.size()>=1){
				cost=costs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cost;
	}

	public List<Cost> findAllList() {
		String hql="from Cost";
		List<Cost> costs=(List<Cost>)hibernateTemplate.find(hql);
		return costs;
	}



}
