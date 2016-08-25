package com.yangui.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.yangui.dao.impl.PhotoDao;
import com.yangui.entity.Group;
import com.yangui.entity.Photo;
import com.yangui.entity.Photostamp;
import com.yangui.entity.User;
import com.yangui.service.BasService;

@Service(value="photoService")
public class PhotoService implements BasService<Photo> {
	/**
	    service.xml添加：
	 	<!-- 配置文件映射D:\superClass/upload-->
        <Context docBase="D:\superClass/upload/" path="/upload"  reloadable="true" crossContext="true"/>
		<Context docBase="CS_Report" path="/CS_Report" reloadable="true" 
	 	source="org.eclipse.jst.jee.server:CS_Report"/></host>
	 **/
	protected final String FilePath = "/upload";
	@Resource(name="photoDao")
	private PhotoDao photoDao;

	public void insert(Photo t) {
		if(t!=null){
			photoDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			photoDao.delete(id);
		}
	}

	public void update(Photo t) {
		if(t!=null){
			photoDao.update(t);
		}
	}

	public Photo getOne(int id) {
		Photo t=photoDao.getOne(id);
		return t;
	}

	public List<Photo> findAll() {
		List<Photo> tes=photoDao.findAllList();
		return tes;
	}

	public PhotoDao getPhotoDao() {
		return photoDao;
	}

	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}


	public Photo savePhotoInfo(String displayName, Photo photo, User user,
			Group group) {
		String fileSuffix = displayName.substring(displayName.lastIndexOf(".")).toLowerCase();// 后缀名
		//文件夹位置
		String currentDate=getCurrentDate();
		String targetPath =getBasPath() + File.separator + currentDate;
		String netPath=FilePath + File.separator +currentDate;
		File newFile = new File(targetPath);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		String randomName = UUID.randomUUID().toString();
		String actualName = randomName + fileSuffix;
		String actualUrl = targetPath +File.separator + actualName;
		String picUrl = netPath+File.separator+ actualName;
//		String picUrl = FilePath + File.separator +actualUrl;
		
		photo.setDisplayName(displayName);
		photo.setPicUrl(picUrl);
		photo.setActualName(actualName);
		photo.setActualUrl(actualUrl);
		photo.setUser(user);
		photo.setGroup(group);

		return photo;
	}

	private static String getBasPath(){
		return "D:\\superClass/upload";
	}

	private static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		return year + "" + mon + File.separator + day;
	}

	public List<Photo> findPhotosByClassIdForPage(int classId,final int offset,final int limit){
		List<Photo> photos=new ArrayList<Photo>();
		final String hql="from Photo where class_id="+classId+"order by createDate desc";
		try {
		photos=(List<Photo>) photoDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public List<Photo> doInHibernate(Session session) throws HibernateException {


				Query query = session.createQuery(hql);

				query.setFirstResult(offset);

				query.setMaxResults(limit);

				List<Photo> list = query.list();

				return (List<Photo>)list;

			}
		});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photos;
	}
	public List<Photo> findPhotosByClassId(int classId){
		List<Photo> photos=new ArrayList<Photo>();
		final String hql="from Photo where class_id= ?";
		List<Photo> ps=new ArrayList<Photo>();
		ps=(List<Photo>) photoDao.getHibernateTemplate().find(hql, classId);
		return ps;
	}
}
