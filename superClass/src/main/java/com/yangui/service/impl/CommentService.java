package com.yangui.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.yangui.dao.impl.CommentDao;
import com.yangui.dto.comment.CommentPhotoDTO;
import com.yangui.entity.Broadcast;
import com.yangui.entity.Comment;
import com.yangui.service.BasService;

@Service(value="commentService")
public class CommentService implements BasService<Comment> {

	@Resource(name="commentDao")
	private CommentDao commentDao;

	public void insert(Comment t) {
		if(t!=null){
			commentDao.insert(t);
		}
	}

	public void delete(int id) {
		if(id!=0){
			commentDao.delete(id);
		}
	}

	public void update(Comment t) {
		if(t!=null){
			commentDao.update(t);
		}
	}

	public Comment getOne(int id) {
		Comment t=commentDao.getOne(id);
		return t;
	}

	public List<Comment> findAll() {
		List<Comment> tes=commentDao.findAllList();
		return tes;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}



	/**
	 * 通过照片ID查找该照片评论的详情
	 * @return CommentPhotoDTO集合
	 */
	public  List<CommentPhotoDTO> findCommentPhotoDTOByPhotoId(int photoId,final int offset,final int limit){
		List<CommentPhotoDTO> commentPhotoDTOs=new ArrayList<CommentPhotoDTO>();
		final String sql="SELECT u.id as fromUserId,u.nickName as fromUserName,u.avator as fromUserAvatorUrl,c.content as fromUserContent FROM tb_comment c,tb_user u,tb_photo p WHERE p.id="+photoId+" AND c.photo_id="+photoId+" AND c.user_id=u.id order by c.createTime desc";
		try {
			commentPhotoDTOs=(List<CommentPhotoDTO>) getCommentDao().getHibernateTemplate().execute(new HibernateCallback<Object>() {

				public List<CommentPhotoDTO> doInHibernate(Session session) throws HibernateException {

					session.beginTransaction();
					
					Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(CommentPhotoDTO.class));

					query.setFirstResult(offset);

					query.setMaxResults(limit);

					List<CommentPhotoDTO> list = query.list();

					session.getTransaction().commit();
					
					return (List<CommentPhotoDTO>)list;

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentPhotoDTOs;
	}


}
