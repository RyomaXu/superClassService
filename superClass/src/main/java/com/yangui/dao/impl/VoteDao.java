package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.Vote;

@Repository(value="voteDao")
public class VoteDao implements Dao<Vote> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(Vote t) {
		hibernateTemplate.save(t);
	}

	public void delete(int id) {
		String hql="from Vote where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public Vote update(Vote t) {
		hibernateTemplate.update(t);
		return t;
	}

	public Vote getOne(int id) {
		String hql="from Vote where id = ? ";
		Vote vote=	null;
		try {
			List<Vote> votes=	(List<Vote>) hibernateTemplate.find(hql, id);
			if(votes!=null && votes.size()>=1){
				vote=votes.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vote;
	}

	public List<Vote> findAllList() {
		String hql="from Vote";
		List<Vote> votes=(List<Vote>)hibernateTemplate.find(hql);
		return votes;
	}



}
