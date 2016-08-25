package com.yangui.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yangui.dao.impl.NotifymessageDao;
import com.yangui.entity.Notifymessage;
import com.yangui.service.BasService;

@Service(value="notifymessageService")
public class NotifymessageService implements BasService<Notifymessage> {
	
	@Resource(name="notifymessageDao")
	private NotifymessageDao notifymessageDao;

	public void insert(Notifymessage t) {
		if(t!=null){
			notifymessageDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			notifymessageDao.delete(id);
		}
	}

	public void update(Notifymessage t) {
		if(t!=null){
			notifymessageDao.update(t);
		}
	}

	public Notifymessage getOne(int id) {
		Notifymessage t=notifymessageDao.getOne(id);
		return t;
	}

	public List<Notifymessage> findAll() {
		List<Notifymessage> tes=notifymessageDao.findAllList();
		return tes;
	}

	public NotifymessageDao getNotifymessageDao() {
		return notifymessageDao;
	}

	public void setNotifymessageDao(NotifymessageDao notifymessageDao) {
		this.notifymessageDao = notifymessageDao;
	}



	


	

}
