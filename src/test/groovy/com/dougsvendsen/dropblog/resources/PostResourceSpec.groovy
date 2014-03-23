package com.dougsvendsen.dropblog.resources


	
import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Shared
import spock.lang.Specification

import com.dougsvendsen.dropblog.core.Post
import com.dougsvendsen.dropblog.db.PostDAO
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
		post = Mock(Post)
		postDAO = Mock(PostDAO){
			1 * getPost(1) >> post
		}
		jersey.addResource(new PostResource(postDAO))
	}

	def 'GET requests fetch the Post by ID'() {
		when:
		jersey.client().resource("/posts/1").method("GET")

		then:
		1 * postDAO.findById(1)
	}

}
	
