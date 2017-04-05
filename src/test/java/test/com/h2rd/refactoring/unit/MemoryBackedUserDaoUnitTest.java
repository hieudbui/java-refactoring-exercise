package test.com.h2rd.refactoring.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.dao.MemoryBackedUserDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoryBackedUserDaoUnitTest {

    MemoryBackedUserDao userDao = new MemoryBackedUserDao();

    @Test
    public void getUsers() {
        String email = "fake@email.com";
        String name = "Fake name";
        String role1 = "admin";
        String role2 = "master";
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(Arrays.asList(role1, role2));
        userDao.saveUser(user);
        List<User> users = new ArrayList(userDao.getUsers());
        assert users.size() == 1;
        assert users.get(0).equals(user);
    }

    @Test
    public void saveUser() {
        String email = "fake@email.com";
        String name = "Fake name";
        String role1 = "admin";
        String role2 = "master";
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(Arrays.asList(role1, role2));
        userDao.saveUser(user);
        assert 1 == userDao.getUsers().size();
        User foundUser = userDao.findUser(email);
        assert user.getName().equals(foundUser.getName());
        assert user.getEmail().equals(foundUser.getEmail());
        assert user.getRoles().equals(foundUser.getRoles());

        userDao.saveUser(user);
        assert 1 == userDao.getUsers().size();
    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));
        userDao.deleteUser(user);
        assertEquals(0, userDao.getUsers().size());
        userDao.deleteUser(user);
    }

    @Test
    public void findUser() {
        String email = "fake@email.com";
        String name = "Fake name";
        String role1 = "admin";
        String role2 = "master";
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(Arrays.asList(role1, role2));
        userDao.saveUser(user);
        assertEquals(user, userDao.findUser(email));
        assertNull("expecting null", userDao.findUser("nonexisting"));
    }

    @Test
    public void findUserByName() {
        String email = "fake@email.com";
        String name = "Fake name";
        String role1 = "admin";
        String role2 = "master";
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(Arrays.asList(role1, role2));
        userDao.saveUser(user);
        assertEquals(user, userDao.findUserByName(name));
        assertNull("expecting null", userDao.findUserByName("nonexisting"));
    }

    @Test
    public void updateUser() {
        String email = "fake@email.com";
        String name = "Fake name";
        String role1 = "admin";
        String role2 = "master";
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(Arrays.asList(role1, role2));
        userDao.saveUser(user);


        String newName = "new name";
        String newRole1 = "newrole1";
        String newRole2 = "newrole2";
        String newRole3 = "newrole3";
        User newUser = new User(email, newName, Arrays.asList(newRole1, newRole2, newRole3));
        userDao.updateUser(newUser);
        assertEquals(1, userDao.getUsers().size());
        List<User> users = new ArrayList(userDao.getUsers());
        assert users.size() == 1;
        User foundUser = users.get(0);
        assertEquals(newUser.getEmail(), foundUser.getEmail());
        assertEquals(newUser.getName(), foundUser.getName());
        assertEquals(newUser.getRoles(), foundUser.getRoles());

        User nonExistingNewUser = new User("nonexistingemail", newName, Arrays.asList(newRole1, newRole2, newRole3));
        userDao.updateUser(nonExistingNewUser);

        users = new ArrayList(userDao.getUsers());
        assertEquals(1, users.size());
        foundUser = users.get(0);
        assertEquals(newUser.getEmail(), foundUser.getEmail());
        assertEquals(newUser.getName(), foundUser.getName());
        assertEquals(newUser.getRoles(), foundUser.getRoles());
    }
}
