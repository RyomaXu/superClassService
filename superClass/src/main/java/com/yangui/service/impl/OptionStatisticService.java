package com.yangui.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.yangui.dao.impl.OptionStatisticDao;
import com.yangui.entity.OptionStatistic;
import com.yangui.entity.User;
import com.yangui.service.BasService;

@Service(value="optionStatisticService")
public class OptionStatisticService implements BasService<OptionStatistic> {
	
	@Resource(name="optionStatisticDao")
	private OptionStatisticDao optionStatisticDao;

	public void insert(OptionStatistic t) {
		if(t!=null){
			optionStatisticDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			optionStatisticDao.delete(id);
		}
	}

	public void update(OptionStatistic t) {
		if(t!=null){
			optionStatisticDao.update(t);
		}
	}

	public OptionStatistic getOne(int id) {
		OptionStatistic t=optionStatisticDao.getOne(id);
		return t;
	}

	public List<OptionStatistic> findAll() {
		List<OptionStatistic> tes=optionStatisticDao.findAllList();
		return tes;
	}

	public OptionStatisticDao getOptionStatisticDao() {
		return optionStatisticDao;
	}

	public void setOptionStatisticDao(OptionStatisticDao optionStatisticDao) {
		this.optionStatisticDao = optionStatisticDao;
	}


	

   public boolean isCurrentUserVoted(int userId,int voteId){
	   String sql="SELECT u.id FROM tb_option_statistics os, tb_user u WHERE os.user_id=u.id AND os.vote_id="+voteId+" AND u.id="+userId+"";
	   List<User> users=new ArrayList<User>();
		Session session=optionStatisticDao.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query= session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(User.class));
		users=query.list();
		session.getTransaction().commit();
		if(users!=null && users.size()>0){
			return true;
		}else{
			 return false;
		}
   }
	


	

}
