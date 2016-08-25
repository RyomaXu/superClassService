package com.yangui.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yangui.dao.impl.GroupDao;
import com.yangui.entity.Group;
import com.yangui.service.BasService;

@Service(value="groupService")
public class GroupService implements BasService<Group> {
	
	@Resource(name="groupDao")
	private GroupDao groupDao;

	public void insert(Group t) {
		if(t!=null){
			groupDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			groupDao.delete(id);
		}
	}

	public void update(Group t) {
		if(t!=null){
			groupDao.update(t);
		}
	}

	public Group getOne(int id) {
		Group t=groupDao.getOne(id);
		return t;
	}

	public List<Group> findAll() {
		List<Group> tes=groupDao.findAllList();
		return tes;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}



	/**
	 * 判断用户暗号与班级暗号是否匹配
	 * @param    institute用户暗号
	 * @param id  用户ID
	 * @return
	 */
	public boolean isInstituteEqualSecret(String institute,int id){
		boolean flag=false;
		String hql="select count(*)  FROM User u,Group g WHERE g.secret =? AND u.id=?";
		List<Long> institutes=(List<Long>)groupDao.getHibernateTemplate().find(hql,institute,id);
		try {
			if(institutes!=null && institutes.size()==1){
				if(1==new BigDecimal(institutes.get(0)).intValueExact()){
					flag=true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	/**
	 * 根据班级暗号查询班级对象
	 * @param secret 班级暗号
	 * @return Group
	 */
	public Group getGroupBySecret(String secret){
		Group group=null;
		String hql="from Group g where g.secret=?";
		try {
			List<Group> groups=(List<Group>)groupDao.getHibernateTemplate().find(hql,secret);
			if(groups!=null && groups.size() >= 1){
				group=groups.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}
	
	/**
	 * 判断班级暗号是否存在
	 * @param secret 班级暗号
	 * @return 已经存在为true,不存在为false
	 */
	public boolean isSecretExisted(String secret){
		boolean flag=false;
		String hql="select g.secret from Group g where g.secret=?";
		List<String> secrets=(List<String>)groupDao.getHibernateTemplate().find(hql,secret);
		if(secrets!=null && secrets.size()>=1){
			flag=true;
		}
		return flag;
	}
	

}
