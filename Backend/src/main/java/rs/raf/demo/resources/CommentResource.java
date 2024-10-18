package rs.raf.demo.resources;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.services.CommentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @GET
    @Path("/article/{id}")//radi
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCommentsForArticle(@PathParam("id") Integer articleId) {
        return Response.ok(this.commentService.findCommentsForArticle(articleId)).build();
    }

    @POST//radi
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment (Comment comment){
        return this.commentService.addComment(comment);
    }
}
