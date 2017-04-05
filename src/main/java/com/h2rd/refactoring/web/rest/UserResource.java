package com.h2rd.refactoring.web.rest;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

/**
 * I left this class here for discussion
 * I clean it up so its not so messary but the core of the logic stays in place
 * I did not remove its usage it out since the idea
 * is that I am assuming that there are clients
 * using it could be used currently
 * even though it doesn't enforce all the rules correctly
 */
@Path("/users")
public class UserResource {

    @Autowired
    UserDao userDao;

    @GET
    @Path("add/")
    public Response addUser(@QueryParam("name") String name,
                            @QueryParam("email") String email,
                            @QueryParam("role") List<String> roles) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);
        userDao.saveUser(user);
        return Response.ok().entity(user).build();
    }

    @GET
    @Path("update/")
    public Response updateUser(@QueryParam("name") String name,
                               @QueryParam("email") String email,
                               @QueryParam("role") List<String> roles) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);
        userDao.updateUser(user);
        return Response.ok().entity(user).build();
    }

    @GET
    @Path("delete/")
    public Response deleteUser(@QueryParam("name") String name,
                               @QueryParam("email") String email,
                               @QueryParam("role") List<String> roles) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);

        userDao.deleteUser(user);
        return Response.ok().entity(user).build();
    }

    @GET
    @Path("find/")
    public Response getUsers() {
        Set<User> users = userDao.getUsers();
        GenericEntity<Set<User>> usersEntity = new GenericEntity<Set<User>>(users) {
        };
        return Response.ok().entity(usersEntity).build();
    }

    @GET
    @Path("search/")
    public Response findUser(@QueryParam("name") String name) {
        User user = userDao.findUserByName(name);
        return Response.ok().entity(user).build();
    }
}
