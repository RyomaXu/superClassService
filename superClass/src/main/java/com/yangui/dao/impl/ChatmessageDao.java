package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Chatmessage;

@Repository(value="chatmessageDao")
public class ChatmessageDao implements Dao<Chatmessage> {
	
	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Chatmessage t) {
		hibernateTemplate.save(t);
		
	}

	public void delete(int id) {
		String hql="from Chatmessage where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}
	
	public Chatmessage update(Chatmessage t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Chatmessage getOne(int id) {
		String hql="from Chatmessage where id = ? ";
		Chatmessage chatmessage=null;
		try {
			List<Chatmessage> chatmessages=	(List<Chatmessage>) hibernateTemplate.find(hql, id);
			if(chatmessages!=null && chatmessages.size()>=1){
				chatmessage=chatmessages.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chatmessage;
	}

	public List<Chatmessage> findAllList() {
		String hql="from Chatmessage";
		List<Chatmessage> chatmessages=(List<Chatmessage>)hibernateTemplate.find(hql);
		return chatmessages;
	}



}
