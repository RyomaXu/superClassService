package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.User;

@Repository(value="userDao")
public class UserDao implements Dao<User> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public void insert(User t) {
		//		if(t.getId()==null){
		//			t.setId(UuidUtil.createUUId().hashCode());
		//		}
		hibernateTemplate.save(t);
	}

	public void delete(int id) {
		String hql="from User where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public User update(User t) {
		hibernateTemplate.update(t);
		return t;
	}

	public User getOne(int id) {
		String hql="from User where id = ? ";
		User user=	null;
		try {
			List<User> users=	(List<User>) hibernateTemplate.find(hql, id);
			if(users!=null && users.size()>=1){
				user=users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<User> findAllList() {
		String hql="from User";
		List<User> users=(List<User>)hibernateTemplate.find(hql);
		return users;
	}



}
