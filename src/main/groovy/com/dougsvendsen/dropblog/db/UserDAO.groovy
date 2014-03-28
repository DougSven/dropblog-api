package com.dougsvendsen.dropblog.db

import com.dougsvendsen.dropblog.core.User

class UserDAO {

	private static final String ADMIN_NAME = System.getenv('ADMIN_USER')
	
	/**
	 * Retrieve user by name
	 * 
	 * @param name
	 * @return
	 */
	User findByName(String name) {
		if (name == ADMIN_NAME) {
			return new User(name: ADMIN_NAME,
							password: System.getenv('ADMIN_PASSWORD'))
		}
	}	
	
}
