package com.dougsvendsen.dropblog.resources

import groovy.json.StringEscapeUtils;
import groovy.util.logging.Slf4j

import javax.validation.Valid
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status

import com.dougsvendsen.dropblog.core.Post
import com.dougsvendsen.dropblog.db.PostDAO
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.metrics.annotation.Timed

@Path('/posts')
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
class PostResource {
	private final PostDAO postDAO

	public PostResource(PostDAO postDAO) {
		this.postDAO = postDAO
	}
	
	/**
	 * Create a post
	 * 
	 * @param post 
	 * <p>The post to create
	 * <p>Example: 
	 * <br>{
	 * <br>"title": "Post Title",
	 * <br>"content": "Content of the post",
	 * <br>"createDate": <em>milliseconds</em>,
	 * <br>"updateDate": <em>milliseconds </em>
	 * <br>}
	 * </p>
	 * @return the post created
	 */
	@POST
	@UnitOfWork
	@Timed(name = 'createPost')
	public Post createPost(@Valid Post post) {
		return postDAO.save(post)
	}
	
	/**
	 * Returns a list of all posts.
	 * 
	 * @return all posts
	 */
	@GET
	@UnitOfWork
	@Timed(name = 'getPosts')
	public List<Post> getPosts() {
		return postDAO.list()
	}

	/**
	 * Returns a known post by id.
	 * 
	 * @param {postId}
	 * <p> The id of a post
	 * @return post by id
	 */
	@GET
	@Path('/{postId}')
	@UnitOfWork
	@Timed(name = 'getPost')
	public Post getPost(@PathParam('postId') long postId) {
		Post post = postDAO.findById(postId)
		//Return 404 if not found
		if ( ! post) {
			log.info 'post not found for ${ postId }'
			throw new WebApplicationException(Status.NOT_FOUND)
		}
		
		return post
	}
	
	/**
	 * Update an existing post by id. If a post does not exist, it is created.
	 *
	 * @param post
	 * <p>The post to update
	 * <p>Example: 
	 * <br>{
	 * <br>"id: 1"
	 * <br>"title": "Post Title",
	 * <br>"content": "Content of the post",
	 * <br>"createDate": <em>milliseconds</em>,
	 * <br>"updateDate": <em>milliseconds</em>
	 * <br>}
	 * </p>
	 * @return the updated or created post
	 */
	@PUT
	@Path('/{postId}')
	@UnitOfWork
	@Timed(name = 'updatePost')
	public Post updatePost(@PathParam('postId') long postId, @Valid Post post) {
		//Set id from URI prior to save.
		post.id = postId
		return postDAO.save(post)
	}
	
	/**
	 * Delete an existing post by id.
	 * 
	 * @param {postId}
	 * <p> The id of existing post to delete
	 * 
	 */
	@DELETE
	@Path('/{postId}')
	@UnitOfWork
	@Timed(name = 'deletePost')
	public void deletePost(@PathParam('postId') long postId) {
		//Verify resource exists prior to delete
		Post post = postDAO.findById(postId)
		
		if ( ! post) {
			log.info 'post not found for ${ postId }'
			throw new WebApplicationException(Status.NOT_FOUND)
		} 
		
		postDAO.delete(post)
		
	}
	
}
