package test.com.h2rd.refactoring.integration;

import static org.junit.Assert.assertEquals;
import com.h2rd.refactoring.usermanagement.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

public class UserResourceIntegrationTest extends BaseEndpointIntegrationTest {
    static final String END_POINT = "/v2/users";

    @Test
    public void getUsers() throws Exception {
        User user = new User();
        user.setEmail("user2@mail.com");
        user.setName("Jane Doe");
        user.setRoles(Arrays.asList("supertester", "superdeveloper"));
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        Response response = target(END_POINT).request().accept(MediaType.APPLICATION_JSON).post(userEntity);
        assertEquals(END_POINT + "/" + user.getEmail(), URLDecoder.decode(new URL(response.getHeaderString("Location")).getPath(), "utf-8"));
        User createdUser = response.readEntity(User.class);
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getRoles(), createdUser.getRoles());

        response = target("v2/users").request().accept(MediaType.APPLICATION_JSON).get();
        List<User> users = response.readEntity(new GenericType<List<User>>() {
        });
        assertEquals(1, users.size());
        User foundUser = users.get(0);
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getRoles(), foundUser.getRoles());
        response.close();
    }

    @Test
    public void addUserInvalidEmailInput() {
        User user = new User();
        user.setName("Jane Doe");
        user.setRoles(Arrays.asList("supertester", "superdeveloper"));
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        //missing email
        user.setEmail(null);
        Response response = target(END_POINT).request().accept(MediaType.APPLICATION_JSON).post(userEntity);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        //empty email
        user.setEmail("");
        response = target(END_POINT).request().accept(MediaType.APPLICATION_JSON).post(userEntity);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());


        //bad email
        user.setEmail("a@");
        response = target(END_POINT).request().accept(MediaType.APPLICATION_JSON).post(userEntity);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        //bad email
        user.setEmail("a@a.");
        response = target(END_POINT).request().accept(MediaType.APPLICATION_JSON).post(userEntity);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void addUserInvalidNameInput() {
        User user = new User();
        user.setEmail("user2@mail.com");
        user.setRoles(Arrays.asList("supertester", "superdeveloper"));
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        //missing name
        user.setName(null);
        Response response = target(END_POINT).request().accept(MediaType.APPLICATION_JSON).post(userEntity);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        //empty name
        user.setName("");
        response = target(END_POINT).request().accept(MediaType.APPLICATION_JSON).post(userEntity);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void addUserInvalidRolesInput() {
        User user = new User();
        user.setEmail("user2@mail.com");
        user.setName("Jane Doe");
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        //empty roles
        user.getRoles().clear();
        Response response = target(END_POINT).request().accept(MediaType.APPLICATION_JSON).post(userEntity);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    //TODO
    //add more tests here
    //i just run out of time

}
