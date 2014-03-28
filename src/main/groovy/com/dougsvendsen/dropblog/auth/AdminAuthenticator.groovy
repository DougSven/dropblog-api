package com.dougsvendsen.dropblog.auth

import groovy.util.logging.Slf4j;

import com.dougsvendsen.dropblog.core.User;
import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator
import com.yammer.dropwizard.auth.basic.BasicCredentials;
@Slf4j
class AdminAuthenticator implements Authenticator<BasicCredentials, User> {

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		
		//TODO: Add user DAO to store users and retrieve creds
		if (credentials.getUsername() == System.getenv('ADMIN_USER') && credentials.getPassword() == System.getenv("ADMIN_PASSWORD")) {
			return Optional.of(new User(name: credentials.getUsername()));
		}
		return Optional.absent()
	}
}
