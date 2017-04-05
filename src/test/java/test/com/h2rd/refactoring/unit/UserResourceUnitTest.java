package test.com.h2rd.refactoring.unit;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;
import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.dao.UserDao;
import com.h2rd.refactoring.web.rest.UserResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceUnitTest {

    @Mock
    UserDao userDao;

    @InjectMocks
    UserResource userResource;

    @Test
    public void getUsers() {
        User user = new User("test@test.com", "test", Arrays.asList("role1"));
        Set<User> users = new HashSet<User>(Arrays.asList(user));
        when(userDao.getUsers()).thenReturn(users);
        Response response = userResource.getUsers();
        assert HttpStatus.OK.value() == response.getStatus();
        List<User> outputUsers = new ArrayList((Set<User>) response.getEntity());
        assertEquals(1, outputUsers.size());
        assertEquals(user.getName(), outputUsers.get(0).getName());
        assertEquals(user.getEmail(), outputUsers.get(0).getEmail());
        assertEquals(user.getRoles(), outputUsers.get(0).getRoles());

        reset(userDao);
        when(userDao.getUsers()).thenReturn(new HashSet<User>());
        response = userResource.getUsers();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        outputUsers = new ArrayList((Set<User>) response.getEntity());
        assertEquals(0, outputUsers.size());
    }
}
