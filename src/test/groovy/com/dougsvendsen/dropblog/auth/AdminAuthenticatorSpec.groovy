package com.dougsvendsen.dropblog.auth


import static com.yammer.dropwizard.testing.JsonHelpers.*

import javax.ws.rs.core.MediaType

import spock.lang.Shared
import spock.lang.Specification

import com.dougsvendsen.dropblog.core.Post
import com.dougsvendsen.dropblog.core.User
import com.dougsvendsen.dropblog.db.PostDAO
import com.dougsvendsen.dropblog.db.UserDAO
import com.dougsvendsen.dropblog.resources.PostResource
import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.UniformInterfaceException
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter
import com.yammer.dropwizard.auth.basic.BasicAuthProvider
import com.yammer.dropwizard.auth.basic.BasicCredentials;

class AdminAuthenticatorSpec extends Specification {
		
	UserDAO userDAO = Mock()
	BasicCredentials basicCredentials = Mock()
	AdminAuthenticator adminAuthenticator = new AdminAuthenticator(userDAO)
	User authenticatedUser = new User(name:'name', password:'password')
	
	def 'Valid username and password can authenticate'() {
		setup:
		_ * basicCredentials.username >> authenticatedUser.name
		_ * basicCredentials.password >> authenticatedUser.password
		_ * userDAO.findByName(authenticatedUser.name) >> authenticatedUser	
		
		expect:
		adminAuthenticator.authenticate(basicCredentials).get() == authenticatedUser
		
	}

	def 'Valid username invalid password cannot authenticate'() {
		setup:
		_ * basicCredentials.username >> authenticatedUser.name
		_ * basicCredentials.password >> "invalid"
		_ * userDAO.findByName(authenticatedUser.name) >> authenticatedUser	
		
		expect:
		! adminAuthenticator.authenticate(basicCredentials).present
		
	}
	
	def 'Invalid username invalid password cannot authenticate'() {
		setup:
		_ * userDAO.findByName(authenticatedUser.name) >> authenticatedUser
		
		expect:
		! adminAuthenticator.authenticate(basicCredentials).present
		
	}
}
	
