package com.yangui.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yangui.dao.impl.FeedbackDao;
import com.yangui.entity.Feedback;
import com.yangui.service.BasService;

@Service(value="feedbackService")
public class FeedbackService implements BasService<Feedback> {
	
	@Resource(name="feedbackDao")
	private FeedbackDao feedbackDao;

	public void insert(Feedback t) {
		if(t!=null){
			feedbackDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			feedbackDao.delete(id);
		}
	}

	public void update(Feedback t) {
		if(t!=null){
			feedbackDao.update(t);
		}
	}

	public Feedback getOne(int id) {
		Feedback t=feedbackDao.getOne(id);
		return t;
	}

	public List<Feedback> findAll() {
		List<Feedback> tes=feedbackDao.findAllList();
		return tes;
	}

	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}

	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}


	


	

}
