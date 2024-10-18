package rs.raf.demo.resources;

import rs.raf.demo.entities.Category;
import rs.raf.demo.services.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;


    @GET//radi
    @Produces(MediaType.APPLICATION_JSON)
    public Response allCategories() {
        return Response.ok(this.categoryService.allCategories()).build();
    }

    @GET//radi
    @Produces(MediaType.APPLICATION_JSON)
    @Path("page/{num}")
    public List<Category> categoriesByPage(@PathParam("num") Integer num) {
        return this.categoryService.categoriesByPage(num);
    }

    @GET//radi
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category findCategory(@PathParam("id") Integer id) {
        return this.categoryService.findCategory(id);
    }

    @GET//radi
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category findCategoryByName(@PathParam("name") String name) {
        return this.categoryService.findCategoryByName(name);
    }
}
