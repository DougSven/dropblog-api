package com.dougsvendsen.dropblog.resources


import static com.yammer.dropwizard.testing.JsonHelpers.*

import javax.ws.rs.core.MediaType

import spock.lang.Shared

import com.dougsvendsen.dropblog.auth.AdminAuthenticator
import com.dougsvendsen.dropblog.core.Post
import com.dougsvendsen.dropblog.core.User
import com.dougsvendsen.dropblog.db.PostDAO
import com.google.common.base.Optional
import com.sun.jersey.api.client.UniformInterfaceException
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter
import com.yammer.dropwizard.auth.basic.BasicAuthProvider
import com.yammer.dropwizard.auth.basic.BasicCredentials
import com.yammer.dropwizard.testing.ResourceTest

class PostResourceSpec extends ResourceSpec {
	

	@Shared
	Post post = new Post(id:1)
	@Shared
	User authenticatedUser = new User(name:'name', password:'password')	
	
	@Override
    void setUpResources() {
		AdminAuthenticator adminAuthenticator = Mock(AdminAuthenticator) {
			_ * authenticate(new BasicCredentials(authenticatedUser.name, authenticatedUser.password)) >> Optional.of(authenticatedUser)
		}
		PostDAO postDAO = Mock(PostDAO) {
			_ * findById(1) >> post
			_ * list() >> [post, post]
			_ * save(_) >> post
		}
		jersey.addResource(new PostResource(postDAO))
		jersey.addProvider(new BasicAuthProvider<User>(adminAuthenticator, 'Blog Admin'))
	}

	def 'POST /posts request no auth returns 401 unauthorized'() {		
		when:
		jersey.client().resource('/posts').entity(jsonFixture('fixtures/simplePost.json'), MediaType.APPLICATION_JSON).post(Post.class)
		
		then:
		UniformInterfaceException e = thrown()
		e.response.status == 401
		
	}
	
	def 'POST /posts with authorized user'() {
		setup:
		setupAuth()
		
		when:
		Post response = jersey.client().resource('/posts').entity(jsonFixture('fixtures/simplePost.json'), MediaType.APPLICATION_JSON).post(Post.class)

		then:
		response == post
		
		cleanup:
		cleanupAuth()
		
	}
	
	def 'GET requests fetch blogposts'() {
		expect:
		jersey.client().resource('/posts').get(List.class).size() == 2
		
	}

	def 'GET request for existing fetch blog post'() {
		expect:
		jersey.client().resource('/posts/1').get(Post.class) == post

	}
	
	def 'GET request for non existing returns 404 not found'() {
		when:
		jersey.client().resource('/posts/404').get(Post.class)
		
		then:
		UniformInterfaceException e = thrown()
		e.response.status == 404

	}
	
	def 'PUT /posts/{id} request no auth returns 401 unauthorized'() {
		when:
		Post response = jersey.client().resource('/posts/1').entity(jsonFixture('fixtures/simplePost.json'), MediaType.APPLICATION_JSON).put(Post.class)

		then:
		UniformInterfaceException e = thrown()
		e.response.status == 401
		
	}
	
	def 'PUT /posts/1 update with authorized user update exectues save'() {
		setup:
		setupAuth()
		
		when:
		Post response = jersey.client().resource('/posts/1').entity(jsonFixture('fixtures/simplePost.json'), MediaType.APPLICATION_JSON).put(Post.class)

		then:
		response == post
		
		cleanup:
		cleanupAuth()
		
	}
	
	def 'PUT /posts/2 create with authorized user executes save'() {
		setup:
		setupAuth()
		
		when:
		Post response = jersey.client().resource('/posts/2').entity(jsonFixture('fixtures/simplePost.json'), MediaType.APPLICATION_JSON).put(Post.class)

		then:
		response == post
		
		cleanup:
		cleanupAuth()		
	}
	
	def 'Delete /posts/{id} request no auth returns 401 unauthorized'() {	
		when:
		Post response = jersey.client().resource('/posts/1').entity(jsonFixture('fixtures/simplePost.json'), MediaType.APPLICATION_JSON).put(Post.class)

		then:
		UniformInterfaceException e = thrown()
		e.response.status == 401
		
	}
	
	def 'DELETE request for non existing returns 404 not found'() {
		setup:
		setupAuth()
		
		when:
		jersey.client().resource('/posts/404').delete()
		
		then:
		UniformInterfaceException e = thrown()
		e.response.status == 404

		cleanup:
		cleanupAuth()
	}
	
	def 'DELETE request for existing post'() {
		setup:
		setupAuth()
		
		when:
		jersey.client().resource('/posts/1').delete()
		
		then:
		notThrown(UniformInterfaceException)

		cleanup:
		cleanupAuth()
	}
	
	def setupAuth() {
		jersey.client().addFilter(new HTTPBasicAuthFilter(authenticatedUser.name, authenticatedUser.password))
	}
	
	def cleanupAuth() {
		jersey.client().removeAllFilters()
	}
}
	
