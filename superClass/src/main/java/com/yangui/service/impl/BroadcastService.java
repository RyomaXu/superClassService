package com.yangui.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.yangui.dao.impl.BroadcastDao;
import com.yangui.entity.Broadcast;
import com.yangui.entity.Vote;
import com.yangui.service.BasService;

@Service(value="broadcastService")
public class BroadcastService implements BasService<Broadcast> {
	
	@Resource(name="broadcastDao")
	private BroadcastDao broadcastDao;

	public void insert(Broadcast t) {
		if(t!=null){
			broadcastDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			broadcastDao.delete(id);
		}
	}

	public void update(Broadcast t) {
		if(t!=null){
			broadcastDao.update(t);
		}
	}

	public Broadcast getOne(int id) {
		Broadcast t=broadcastDao.getOne(id);
		return t;
	}

	public List<Broadcast> findAll() {
		List<Broadcast> tes=broadcastDao.findAllList();
		return tes;
	}

	public BroadcastDao getBroadcastDao() {
		return broadcastDao;
	}

	public void setBroadcastDao(BroadcastDao broadcastDao) {
		this.broadcastDao = broadcastDao;
	}

	/**
	 * 分页查找公告
	 * @param classId 班级Id 不能Integer,hibernate底层无法识别
	 * @param offset   返回的第一个结果，从0开始
	 * @param limit    每页数
	 * @return
	 */
	public List<Broadcast> findBroadcastsForPage(int classId,final int offset,final int limit){
		List<Broadcast> broadcasts=null;
		final String hql="from Broadcast b where class_id="+classId+"order by createTime desc";
		broadcasts=(List<Broadcast>) broadcastDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public List<Broadcast> doInHibernate(Session session) throws HibernateException {


				Query query = session.createQuery(hql);

				query.setFirstResult(offset);

				query.setMaxResults(limit);

				List<?> list = query.list();

				return (List<Broadcast>)list;

			}
		});
		return broadcasts;
	}
}
