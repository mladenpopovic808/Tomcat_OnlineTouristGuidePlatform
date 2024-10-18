package rs.raf.demo.filters;



import rs.raf.demo.entities.User;
import rs.raf.demo.resources.UserResource;
import rs.raf.demo.resources.cms.CmsUserResource;
import rs.raf.demo.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        //dobijamo zahtev
        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }
            User user=this.userService.returnUserFromToken(token);
            if(user!=null && !user.getRole().equals("admin") && isAccessingUserRepository(requestContext)){
                System.out.println("Nemate pristup ovoj stranici jer ste content creator!");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

            //da li je token validan
            if (!this.userService.isAuthorized(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

    }
    private boolean isAuthRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false; //ne treba ti autentifikacija da udjes na login page
        }
        if (req.getUriInfo().getPath().contains("articles")) {
            return false;
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof CmsUserResource) {
                return true;
            }
        }

        return false;
    }

    private boolean isAccessingUserRepository(ContainerRequestContext req) {
        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof CmsUserResource) { //za userResource ne treba,jer mozemo da gledamo javne artikle,i da vidimo ko je komentarisao
                return true;
            }
        }

        return false;
    }
}
