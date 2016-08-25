package com.yangui.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yangui.dao.impl.CostDao;
import com.yangui.entity.Cost;
import com.yangui.service.BasService;

@Service(value="costService")
public class CostService implements BasService<Cost> {
	
	@Resource(name="costDao")
	private CostDao costDao;

	public void insert(Cost t) {
		if(t!=null){
			costDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			costDao.delete(id);
		}
	}

	public void update(Cost t) {
		if(t!=null){
			costDao.update(t);
		}
	}

	public Cost getOne(int id) {
		Cost t=costDao.getOne(id);
		return t;
	}

	public List<Cost> findAll() {
		List<Cost> tes=costDao.findAllList();
		return tes;
	}

	public CostDao getCostDao() {
		return costDao;
	}

	public void setCostDao(CostDao costDao) {
		this.costDao = costDao;
	}

	


	

}
