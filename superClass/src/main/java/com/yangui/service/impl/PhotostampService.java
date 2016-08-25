package com.yangui.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.yangui.dao.impl.PhotostampDao;
import com.yangui.entity.Broadcast;
import com.yangui.entity.Photo;
import com.yangui.entity.Photostamp;
import com.yangui.service.BasService;

@Service(value="photostampService")
public class PhotostampService implements BasService<Photostamp> {
	
	@Resource(name="photostampDao")
	private PhotostampDao photostampDao;

	public void insert(Photostamp t) {
		if(t!=null){
			photostampDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			photostampDao.delete(id);
		}
	}

	public void update(Photostamp t) {
		if(t!=null){
			photostampDao.update(t);
		}
	}

	public Photostamp getOne(int id) {
		Photostamp t=photostampDao.getOne(id);
		return t;
	}

	public List<Photostamp> findAll() {
		List<Photostamp> tes=photostampDao.findAllList();
		return tes;
	}

	public PhotostampDao getPhotostampDao() {
		return photostampDao;
	}

	public void setPhotostampDao(PhotostampDao photostampDao) {
		this.photostampDao = photostampDao;
	}

	public List<Photostamp> findPhotostampsByClassIdForPage(int classId,final int offset,final int limit){
		List<Photostamp> photostamps=new ArrayList<Photostamp>();
		final String hql="from Photostamp where class_id="+classId+"order by createTime desc";
		photostamps=(List<Photostamp>) photostampDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public List<Photostamp> doInHibernate(Session session) throws HibernateException {


				Query query = session.createQuery(hql);

				query.setFirstResult(offset);

				query.setMaxResults(limit);

				List<Photostamp> list = query.list();

				return (List<Photostamp>)list;

			}
		});
		return photostamps;
	}
	public List<Photostamp> findPhotostampsByClassId(int classId){
		List<Photostamp> photostamps=new ArrayList<Photostamp>();
		final String hql="from Photostamp where class_id=? order by createTime desc";
		photostamps=(List<Photostamp>) photostampDao.getHibernateTemplate().find(hql, classId);
		return photostamps;
	}
}
