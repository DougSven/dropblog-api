package com.dougsvendsen.dropblog.resources

import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import javax.validation.Valid

import com.dougsvendsen.dropblog.core.Post
import com.dougsvendsen.dropblog.db.PostDAO
import com.yammer.dropwizard.hibernate.UnitOfWork

@Path('/posts')
@Produces(MediaType.APPLICATION_JSON)
class PostResource {
	private final PostDAO postDAO

	public PostResource(PostDAO postDAO) {
		this.postDAO = postDAO
	}
	
	@POST
	@UnitOfWork
	public Post createPost(@Valid Post post) {
		postDAO.save(post)
	}
	
	@GET
	@UnitOfWork
	public List<Post> getPosts() {
		postDAO.list()
	}

	@GET
	@Path('/{postId}')
	@UnitOfWork
	public Post getPost(@PathParam('postId') long postId) {
		Post post = postDAO.findById(postId)
		return post
	}
	
	@PUT
	@Path('/{postId}')
	@UnitOfWork
	public void updatePost(@PathParam('postId') long postId, @Valid Post post) {
		postDAO.save(post)
	}
	
	@DELETE
	@Path('/{postId}')
	@UnitOfWork
	public void deletePost(@PathParam('postId') long postId, @Valid Post post) {
		postDAO.delete(post)
	}
	
}
