package com.yangui.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yangui.dao.impl.OptionDao;
import com.yangui.entity.Option;
import com.yangui.service.BasService;


@Service(value="optionService")
public class OptionService implements BasService<Option> {
	
	@Resource(name="optionDao")
	private OptionDao optionDao;

	public void insert(Option t) {
		if(t!=null){
			optionDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			optionDao.delete(id);
		}
	}

	public void update(Option t) {
		if(t!=null){
			optionDao.update(t);
		}
	}

	public Option getOne(int id) {
		Option t=optionDao.getOne(id);
		return t;
	}

	public List<Option> findAll() {
		List<Option> tes=optionDao.findAllList();
		return tes;
	}

	public OptionDao getOptionDao() {
		return optionDao;
	}

	public void setOptionDao(OptionDao optionDao) {
		this.optionDao = optionDao;
	}

	


	


	

}
