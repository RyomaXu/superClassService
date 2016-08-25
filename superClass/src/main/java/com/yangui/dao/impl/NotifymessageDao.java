package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Notifymessage;

@Repository(value="notifymessageDao")
public class NotifymessageDao implements Dao<Notifymessage> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Notifymessage t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from Notifymessage where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Notifymessage update(Notifymessage t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Notifymessage getOne(int id) {
		String hql="from Notifymessage where id = ? ";
		Notifymessage notifymessage=null;
		try {
			List<Notifymessage> notifymessages=	(List<Notifymessage>) hibernateTemplate.find(hql, id);
			if(notifymessages!=null && notifymessages.size()>=1){
				notifymessage=notifymessages.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifymessage;
	}

	public List<Notifymessage> findAllList() {
		String hql="from Notifymessage";
		List<Notifymessage> notifymessagees=(List<Notifymessage>)hibernateTemplate.find(hql);
		return notifymessagees;
	}



}
