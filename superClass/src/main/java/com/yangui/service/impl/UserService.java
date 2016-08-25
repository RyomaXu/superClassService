package com.yangui.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.yangui.dao.impl.UserDao;
import com.yangui.dto.comment.CommentPhotoDTO;
import com.yangui.entity.User;
import com.yangui.service.BasService;

@Service(value="userService")
public class UserService implements BasService<User> {
	
	@Resource(name="userDao")
	private UserDao userDao;

	public void insert(User t) {
		if(t!=null){
			userDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			userDao.delete(id);
		}
	}

	public void update(User t) {
		if(t!=null){
			userDao.update(t);
		}
	}

	public User getOne(int id) {
		User t=userDao.getOne(id);
		return t;
	}

	public List<User> findAll() {
		List<User> tes=userDao.findAllList();
		return tes;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 通过电话号码查找用户
	 * @param telNum
	 * @param password
	 * @return User对象
	 */
	public User getUserByTelNum(String telNum) {
		String hql="from User where telNum=?";
    	User user=null;
    	try {
    		List<User>	users=(List<User>) userDao.getHibernateTemplate().find(hql,telNum);
    		if(users==null || users.size()==0){
    			user=null;
    		}else{
    			user=users.get(0);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return user;
	}
	
	/**
	 * 通过电话号码和密码查找用户
	 * @param telNum
	 * @param password
	 * @return User对象
	 */
	public User getUserByTelNumAndPassword(String telNum,String password) {
		String hql="from User where telNum=? and password=?";
		User user=null;
		try {
			List<User>	users=(List<User>) userDao.getHibernateTemplate().find(hql,telNum,password);
			if(users==null || users.size()==0){
				user=null;
			}else{
				user=users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 判断是否已经注册
	 * @param telNum
	 * @return 
	 */
	public boolean isTelNum(String telNum){
		boolean flag=false;
		String hql="select u.telNum from User u where u.telNum=?";
		List<String> returnTelNums= (List<String>) userDao.getHibernateTemplate().find(hql,telNum);
		if(returnTelNums==null || returnTelNums.size()==0){
			flag=true;
		}else{
			//donothing
		}
		return flag;
	}
	
	/**
	 * 查找该投票下的用户
	 * @param voteId
	 * @return
	 */
	public List<User> findUsersForVote(int voteId){
		String sql="SELECT u.id,u.nickName FROM tb_user u, tb_option_statistics os,tb_vote v WHERE v.id="+ voteId+" AND os.user_id=u.id AND os.vote_id=v.id  GROUP BY u.id";
		List<User> users=new ArrayList<User>();
		Session session=userDao.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query= session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(User.class));
		users=query.list();
		session.getTransaction().commit();
		return users;
	}


	/**
	 * 查找该班级所有用户
	 * @param classId
	 * @return  Users
	 */
	public List<User> findUsersByClassId(int classId){
		List<User> users=new ArrayList<User>();
		String hql="from User where class_id=?";
		users=(List<User>) userDao.getHibernateTemplate().find(hql,classId);
		return users;
	}
}
