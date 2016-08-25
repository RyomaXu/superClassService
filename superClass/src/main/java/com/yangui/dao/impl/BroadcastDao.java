package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Broadcast;

@Repository(value="broadcastDao")
public class BroadcastDao implements Dao<Broadcast> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Broadcast t) {
		//		if(t.getId()==null){
		//			t.setId(UuidUtil.createUUId());
		//		}
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from Broadcast where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Broadcast update(Broadcast t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Broadcast getOne(int id) {
		String hql="from Broadcast where id = ? ";
		Broadcast broadcast=null;
		try {
			List<Broadcast> broadcasts=	(List<Broadcast>) hibernateTemplate.find(hql, id);
			if(broadcasts!=null && broadcasts.size()>=1){
				broadcast=broadcasts.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return broadcast;
	}

	public List<Broadcast> findAllList() {
		String hql="from Broadcast";
		List<Broadcast> broadcasts=(List<Broadcast>)hibernateTemplate.find(hql);
		return broadcasts;
	}



}
