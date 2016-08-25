package com.yangui.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.yangui.dao.impl.HomeworkDao;
import com.yangui.entity.Broadcast;
import com.yangui.entity.Homework;
import com.yangui.service.BasService;

@Service(value="homeworkService")
public class HomeworkService implements BasService<Homework> {
	
	@Resource(name="homeworkDao")
	private HomeworkDao homeworkDao;

	public void insert(Homework t) {
		if(t!=null){
			homeworkDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			homeworkDao.delete(id);
		}
	}

	public void update(Homework t) {
		if(t!=null){
			homeworkDao.update(t);
		}
	}

	public Homework getOne(int id) {
		Homework t=homeworkDao.getOne(id);
		return t;
	}

	public List<Homework> findAll() {
		List<Homework> tes=homeworkDao.findAllList();
		return tes;
	}

	public HomeworkDao getHomeworkDao() {
		return homeworkDao;
	}

	public void setHomeworkDao(HomeworkDao homeworkDao) {
		this.homeworkDao = homeworkDao;
	}


	/**
	 * 通过是否最新和班级Id分页查找作业
	 * @param isNewest 是否最新
	 * @param classId  班级Id 不能Integer,hibernate底层无法识别
	 * @param offset   返回的第一个结果，从0开始
	 * @param limit    每页数
	 * @return
	 */
	public List<Homework>  findHomeworkByisNewestAndclassIdForPage(boolean isNewest,int classId,final int offset,final int limit){
		final String hql="from Homework  where isNewest="+isNewest+" and class_id="+classId+"order by uploadDate desc";
		List<Homework> homeworks=null;
		homeworks=(List<Homework>) homeworkDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public List<Homework> doInHibernate(Session session) throws HibernateException {


				Query query = session.createQuery(hql);

				query.setFirstResult(offset);

				query.setMaxResults(limit);

				List<?> list = query.list();

				return (List<Homework>)list;

			}
		});
		return homeworks;
	}
	
	/**
	 * 通过班级Id分页分类（是否最新）查找作业
	 * @param isNewest 是否最新
	 * @param classId  班级Id 不能Integer,hibernate底层无法识别
	 * @param offset   返回的第一个结果，从0开始
	 * @param limit    每页数
	 * @return
	 */
	public List<Homework>  findHomeworkByClassIdForPage(final boolean isNewest,int classId,final int offset,final int limit){
		//最新作业
	    final String hql1="from Homework  where DATEDIFF(CURDATE(),uploadDate)<0 and class_id="+classId+"order by uploadDate asc";
	    //过期作业
	    final String hql2="from Homework  where DATEDIFF(CURDATE(),uploadDate)>0 and class_id="+classId+"order by uploadDate asc";
		List<Homework> homeworks=null;
		homeworks=(List<Homework>) homeworkDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public List<Homework> doInHibernate(Session session) throws HibernateException {


				Query query =null;
				if(isNewest){
					query =session.createQuery(hql1);
				}else{
					query =session.createQuery(hql2);
				}
				 

				query.setFirstResult(offset);

				query.setMaxResults(limit);

				List<?> list = query.list();

				return (List<Homework>)list;

			}
		});
		return homeworks;
	}

	

}
