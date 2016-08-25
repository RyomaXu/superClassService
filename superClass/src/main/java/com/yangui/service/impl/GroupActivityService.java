package com.yangui.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yangui.dao.impl.GroupActivityDao;
import com.yangui.entity.GroupActivity;
import com.yangui.service.BasService;

@Service(value="groupActivityService")
public class GroupActivityService implements BasService<GroupActivity> {
	
	@Resource(name="groupActivityDao")
	private GroupActivityDao groupActivityDao;

	public void insert(GroupActivity t) {
		if(t!=null){
			groupActivityDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			groupActivityDao.delete(id);
		}
	}

	public void update(GroupActivity t) {
		if(t!=null){
			groupActivityDao.update(t);
		}
	}

	public GroupActivity getOne(int id) {
		GroupActivity t=groupActivityDao.getOne(id);
		return t;
	}

	public List<GroupActivity> findAll() {
		List<GroupActivity> tes=groupActivityDao.findAllList();
		return tes;
	}

	public GroupActivityDao getGroupActivityDao() {
		return groupActivityDao;
	}

	public void setGroupActivityDao(GroupActivityDao groupActivityDao) {
		this.groupActivityDao = groupActivityDao;
	}




	


	

}
