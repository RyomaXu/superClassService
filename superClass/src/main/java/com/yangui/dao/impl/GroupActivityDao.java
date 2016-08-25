package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.GroupActivity;

@Repository(value="groupActivityDao")
public class GroupActivityDao implements Dao<GroupActivity> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(GroupActivity t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from GroupActivity where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public GroupActivity update(GroupActivity t) {
		hibernateTemplate.update(t);
		return t;
	}
	public GroupActivity getOne(int id) {
		String hql="from GroupActivity where id = ? ";
		GroupActivity groupActivity=	null;
		try {
			List<GroupActivity> groupActivitys=	(List<GroupActivity>) hibernateTemplate.find(hql, id);
			if(groupActivitys!=null && groupActivitys.size()>=1){
				groupActivity=groupActivitys.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupActivity;
	}
	public List<GroupActivity> findAllList() {
		String hql="from GroupActivity";
		List<GroupActivity> groupActivitys=(List<GroupActivity>)hibernateTemplate.find(hql);
		return groupActivitys;
	}



}
