package com.yangui.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.yangui.dao.impl.VoteDao;
import com.yangui.entity.Broadcast;
import com.yangui.entity.User;
import com.yangui.entity.Vote;
import com.yangui.service.BasService;

@Service(value="voteService")
public class VoteService implements BasService<Vote> {
	
	@Resource(name="voteDao")
	private VoteDao voteDao;

	public void insert(Vote t) {
		if(t!=null){
			voteDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			voteDao.delete(id);
		}
	}

	public void update(Vote t) {
		if(t!=null){
			voteDao.update(t);
		}
	}

	public Vote getOne(int id) {
		Vote t=voteDao.getOne(id);
		return t;
	}

	public List<Vote> findAll() {
		List<Vote> tes=voteDao.findAllList();
		return tes;
	}

	public VoteDao getVoteDao() {
		return voteDao;
	}

	public void setVoteDao(VoteDao voteDao) {
		this.voteDao = voteDao;
	}


	/**
	 * 分页查找投票
	 * @param classId 班级Id 不能Integer,hibernate底层无法识别
	 * @param offset   返回的第一个结果，从0开始
	 * @param limit    每页数
	 * @return
	 */
	public List<Vote> findVotesForPage(final boolean isNewest,int classId,final int offset,final int limit){
		List<Vote> votes=new ArrayList<Vote>();
		//最新投票
		final String hql1="from Vote where DATEDIFF(CURDATE(),endTime)<0 and class_id="+classId+"order by endTime desc";
		final String hql2="from Vote where DATEDIFF(CURDATE(),endTime)>0 and class_id="+classId+"order by endTime desc";
		votes=(List<Vote>) voteDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public List<Vote> doInHibernate(Session session) throws HibernateException {
				Query query =null;
				if(isNewest){
					query =session.createQuery(hql1);
				}else{
					query =session.createQuery(hql2);
				}


				query.setFirstResult(offset);

				query.setMaxResults(limit);

				List<?> list = query.list();

				return (List<Vote>)list;

			}
		});
		return votes;
	}
	
}
