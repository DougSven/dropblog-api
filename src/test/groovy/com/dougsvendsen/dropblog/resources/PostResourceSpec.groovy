package com.dougsvendsen.dropblog.resources


import static com.yammer.dropwizard.testing.JsonHelpers.*

import java.util.List

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType
import org.junit.Test
import spock.lang.Shared
import spock.lang.Specification
import com.dougsvendsen.dropblog.core.Post
import com.dougsvendsen.dropblog.db.PostDAO
import com.sun.jersey.api.client.UniformInterfaceException;
import com.yammer.dropwizard.testing.ResourceTest
import com.dougsvendsen.dropblog.resources.PostResource

class PostResourceSpec extends Specification {

	@Shared
	Post post
	@Shared
	PostDAO postDAO
	@Shared
	ResourceTest jersey = new ResourceTest() {
		  @Override
		  protected void setUpResources() {}
	   }
	 
   void setupSpec(){
	  setUpResources()
	  jersey.setUpJersey()
   }
 
   void cleanupSpec() {
	  jersey.tearDownJersey()
   }
	
   void setUpResources(){
		post = new Post(id:1)
		postDAO = Mock(PostDAO){
			_ * findById(1) >> post
			1 * list() >> [post, post]
			1 * save(_) >> post
		}
		jersey.addResource(new PostResource(postDAO))
	}

	def 'GET requests fetch blogposts'() {
		expect:
		jersey.client().resource("/posts").get(List.class).size() == 2
		
	}

	def 'GET request for existing fetch blog post'() {
		expect:
		jersey.client().resource("/posts/1").get(Post.class) == post

	}
	
	def 'GET request for non existing returns not found'() {
		when:
		jersey.client().resource("/posts/404").get(Post.class)
		then:
		thrown UniformInterfaceException

	}
	
	def 'POST request for existing fetch blog post'() {
		expect:
		jersey.client().resource("/posts").entity(jsonFixture("fixtures/simplePost.json"), MediaType.APPLICATION_JSON).post(Post.class) == post
	}
	
}
	
