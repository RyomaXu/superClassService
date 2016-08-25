package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Group;

@Repository(value="groupDao")
public class GroupDao implements Dao<Group> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Group t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from Group where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Group update(Group t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Group getOne(int id) {
		String hql="from Group where id = ? ";
		Group group=null;
		try {
			List<Group> groups=	(List<Group>) hibernateTemplate.find(hql, id);
			if(groups!=null && groups.size()>=1){
				group=groups.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}

	public List<Group> findAllList() {
		String hql="from Group";
		List<Group> groups=(List<Group>)hibernateTemplate.find(hql);
		return groups;
	}



}
