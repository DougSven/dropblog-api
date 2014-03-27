package com.dougsvendsen.dropblog.db

import org.hibernate.SessionFactory

import com.dougsvendsen.dropblog.core.Post
import com.yammer.dropwizard.hibernate.AbstractDAO

class PostDAO extends AbstractDAO<Post> {

	PostDAO(SessionFactory factory) {
		super(factory)
	}
	
	Post save(Post post) {
		persist(post)
	}
	
	Post findById(Long id) {
		get(id)
	}
	
	List<Post> list() {
		list(criteria())
	}
	
	void delete(Post post) {
		if (post) {
			currentSession().delete(post)	
		}
	}
	
	
}
