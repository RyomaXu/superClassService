package com.yangui.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yangui.dao.impl.ChatmessageDao;
import com.yangui.entity.Chatmessage;
import com.yangui.service.BasService;

@Service(value="chatmessageService")
public class ChatmessageService implements BasService<Chatmessage> {
	
	@Resource(name="chatmessageDao")
	private ChatmessageDao chatmessageDao;

	public void insert(Chatmessage t) {
		if(t!=null){
			chatmessageDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			chatmessageDao.delete(id);
		}
	}

	public void update(Chatmessage t) {
		if(t!=null){
			chatmessageDao.update(t);
		}
	}

	public Chatmessage getOne(int id) {
		Chatmessage t=chatmessageDao.getOne(id);
		return t;
	}

	public List<Chatmessage> findAll() {
		List<Chatmessage> tes=chatmessageDao.findAllList();
		return tes;
	}

	public ChatmessageDao getChatmessageDao() {
		return chatmessageDao;
	}

	public void setChatmessageDao(ChatmessageDao chatmessageDao) {
		this.chatmessageDao = chatmessageDao;
	}


	

}
