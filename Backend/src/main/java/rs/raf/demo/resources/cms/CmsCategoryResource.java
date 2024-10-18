package rs.raf.demo.resources.cms;

import rs.raf.demo.entities.Category;
import rs.raf.demo.repositories.implementations.SqlCategoryRepository;
import rs.raf.demo.services.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/cms_category")
public class CmsCategoryResource {

    @Inject
    private CategoryService categoryService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Category addCategory (Category category) throws SqlCategoryRepository.CategoryAlreadyExistsException {
        return this.categoryService.addCategory(category);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Category updateCategory (Category category){
        return this.categoryService.updateCategory(category);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteCategory(@PathParam("id") Integer id) throws Exception {
        this.categoryService.deleteCategory(id);
    }


    //todo mozda trebas da dodaj opet find name find id

}
