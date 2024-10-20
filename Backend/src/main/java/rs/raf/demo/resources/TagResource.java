package rs.raf.demo.resources;

import rs.raf.demo.entities.Tag;
import rs.raf.demo.services.TagService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tags")
public class TagResource {

    @Inject
    private TagService tagService;

    @GET//radi
    @Produces(MediaType.APPLICATION_JSON)
    public Response allTags() {
        return Response.ok(this.tagService.allTags()).build();
    }

    @POST//radi
    @Produces(MediaType.APPLICATION_JSON)
    public Tag addTag (Tag tag){
        return this.tagService.addTag(tag);
    }

    @POST
    @Path("/tags_articles/{tagId}/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addTagsArticles(@PathParam("tagId") Integer tagId, @PathParam("articleId") Integer articleId) {
        this.tagService.addTagsArticles(tagId, articleId);
    }
}
