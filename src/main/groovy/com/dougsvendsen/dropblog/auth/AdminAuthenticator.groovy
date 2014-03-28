package com.dougsvendsen.dropblog.auth

import com.dougsvendsen.dropblog.core.User;
import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator
import com.yammer.dropwizard.auth.basic.BasicCredentials;

class AdminAuthenticator implements Authenticator<BasicCredentials, User> {

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		
		//TODO: Add user DAO to store users
		if (credentials.getPassword() == "hunter2" && credentials.getUsername() == "admin") {
			return Optional.of(new User(name: credentials.getUsername()));
		}
		return Optional.absent()
	}
}
