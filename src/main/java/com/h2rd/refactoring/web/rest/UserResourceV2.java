package com.h2rd.refactoring.web.rest;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Set;

@Path("v2/users")
public class UserResourceV2 {
    static Logger LOGGER = LoggerFactory.getLogger(UserResourceV2.class);

    @Context
    UriInfo uriInfo;

    @Autowired
    UserDao userDao;

    //just for testing
    @Deprecated
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addUserFormEncoded(@FormParam("name") String name,
                                       @FormParam("email") String email,
                                       @FormParam("role") List<String> roles) {
        User foundUser = userDao.findUser(email);
        if (foundUser != null) {
            LOGGER.debug("A User with email " + foundUser.getEmail() + " already exist");
            return Response.status(Response.Status.CONFLICT).build();
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);
        userDao.saveUser(user);
        URI uri = uriInfo.getAbsolutePathBuilder().path(user.getEmail()).build();
        return Response.created(uri).entity(user).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addUser(@Valid final User user) {
        User foundUser = userDao.findUser(user.getEmail());
        if (foundUser != null) {
            LOGGER.debug("A User with email " + foundUser.getEmail() + " already exist");
            return Response.status(Response.Status.CONFLICT).build();
        }
        userDao.saveUser(user);
        URI uri = uriInfo.getAbsolutePathBuilder().path(user.getEmail()).build();
        return Response.created(uri).entity(user).build();
    }


    //just for testing
    @Deprecated
//    @PUT
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Path("{email}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateUserFormEncoded(@PathParam("name") String name,
                                          @FormParam("email") String email,
                                          @FormParam("role") List<String> roles) {
        LOGGER.debug("Updating User " + name);
        User existingUser = userDao.findUser(email);
        if (existingUser == null) {
            LOGGER.debug("User with email " + email + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);
        userDao.updateUser(user);
        return Response.ok().entity(user).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("email") String email,
                               @Valid final User user) {
        LOGGER.debug("Updating User " + email);
        User existingUser = userDao.findUser(email);
        if (existingUser == null) {
            LOGGER.debug("User with email " + email + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.setEmail(email);
        userDao.updateUser(user);
        return Response.ok().entity(user).build();
    }

    @DELETE
    @Path("{email}")
    public Response deleteUser(@PathParam("email") String email) {
        LOGGER.debug("Deleting User " + email);
        User existingUser = userDao.findUser(email);
        if (existingUser == null) {
            LOGGER.debug("User with email " + email + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userDao.deleteUser(existingUser);
        return Response.noContent().build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getUsers() {
        Set<User> users = userDao.getUsers();
        if (users.isEmpty()) {
            return Response.noContent().build();
        }
        GenericEntity<Set<User>> usersEntity = new GenericEntity<Set<User>>(users) {
        };
        return Response.ok().entity(usersEntity).build();
    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findUser(@PathParam("email") String email) {
        LOGGER.debug("findUserByEmail=" + email);
        User user = userDao.findUser(email);
        if (user == null) {
            LOGGER.debug("User with email " + email + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(user).build();
    }
}
