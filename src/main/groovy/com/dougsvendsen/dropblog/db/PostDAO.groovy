package com.dougsvendsen.dropblog.db

import org.hibernate.SessionFactory

import com.dougsvendsen.dropblog.core.Post
import com.yammer.dropwizard.hibernate.AbstractDAO

class PostDAO extends AbstractDAO<Post> {

	PostDAO(SessionFactory factory) {
		super(factory)
	}
	
	/**
	 * Create or update a post
	 * 
	 * @param post
	 * @return created or updated post
	 */
	Post save(Post post) {
		persist(post)
	}
	
	/**
	 * Retrieve post by id
	 * 
	 * @param id
	 * @return
	 */
	Post findById(Long id) {
		get(id)
	}
	
	/**
	 * Retrieve all posts
	 * 
	 * @return
	 */
	List<Post> list() {
		list(criteria())
	}
	
	/**
	 * Delete a post
	 * 
	 * @param post
	 */
	void delete(Post post) {
		if (post) {
			currentSession().delete(post)	
		}
	}
	
	
}
