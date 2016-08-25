package com.yangui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangui.dao.Dao;
import com.yangui.entity.OptionStatistic;

@Repository(value="optionStatisticDao")
public class OptionStatisticDao implements Dao<OptionStatistic> {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void insert(OptionStatistic t) {
		hibernateTemplate.save(t);

	}

	public void delete(int id) {
		String hql="from OptionStatistic where id = ? ";
		hibernateTemplate.delete(this.getOne(id));
	}

	public OptionStatistic update(OptionStatistic t) {
		hibernateTemplate.update(t);
		return t;
	}

	public OptionStatistic getOne(int id) {
		String hql="from OptionStatistic where id = ? ";
		OptionStatistic optionStatistic=null;
		try {
			List<OptionStatistic> optionStatistics=	(List<OptionStatistic>) hibernateTemplate.find(hql, id);
			if(optionStatistics!=null && optionStatistics.size()>=1){
				optionStatistic=optionStatistics.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return optionStatistic;
	}

	public List<OptionStatistic> findAllList() {
		String hql="from OptionStatistic";
		List<OptionStatistic> optionStatistics=(List<OptionStatistic>)hibernateTemplate.find(hql);
		return optionStatistics;
	}



}
