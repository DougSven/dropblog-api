package com.dougsvendsen.dropblog.db

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.dougsvendsen.dropblog.core.Post;
import com.yammer.dropwizard.hibernate.AbstractDAO;

class PostDAO extends AbstractDAO<Post> {

	PostDAO(SessionFactory factory) {
		super(factory)
	}
	
	Post save(Post post) {
		return persist(post)
	}
	
	Post findById(Long id) {
		return currentSession()
			.createCriteria(Post)
			.add(Restrictions.eq('id', id))
			.uniqueResult() as Post
	}
	
}
