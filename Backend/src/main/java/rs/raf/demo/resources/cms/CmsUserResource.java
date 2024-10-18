package rs.raf.demo.resources.cms;

import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.implementations.SqlUserRepository;
import rs.raf.demo.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/cms_users")
public class CmsUserResource {

    @Inject
    private UserService userService;



    @POST//radi
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser (User user) throws SqlUserRepository.UserWithEmailExistsException {

        return this.userService.addUser(user);

    }

    @PUT//radi
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User editUser (User user){
        return this.userService.editUser(user);
    }

    @DELETE//radi
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("id") Integer id){
        this.userService.deleteUser(id);
    }

    @PUT//radi
    @Path("/activate/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void activateUser(@PathParam("id")Integer id){
        this.userService.activateUser(id);
    }

    @PUT//radi
    @Path("/deactivate/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deactivateUser(@PathParam("id")Integer id){
        this.userService.deactivateUser(id);
    }


}
