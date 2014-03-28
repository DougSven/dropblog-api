package com.dougsvendsen.dropblog.auth

import groovy.util.logging.Slf4j

import com.dougsvendsen.dropblog.core.User
import com.dougsvendsen.dropblog.db.UserDAO
import com.google.common.base.Optional
import com.yammer.dropwizard.auth.AuthenticationException
import com.yammer.dropwizard.auth.Authenticator
import com.yammer.dropwizard.auth.basic.BasicCredentials

@Slf4j
class AdminAuthenticator implements Authenticator<BasicCredentials, User> {

	private final UserDAO userDAO

	public AdminAuthenticator(UserDAO userDAO) {
		super()
		this.userDAO = userDAO
	}
	
	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		User user = userDAO.findByName(credentials?.username)
		
		if (user && credentials && user.password == credentials.password) {
			return Optional.of(user)
		}
		return Optional.absent()
		
	}
}
