package com.h2rd.refactoring.usermanagement.dao;

import com.h2rd.refactoring.usermanagement.User;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Repository("userDao")
public class MemoryBackedUserDao implements UserDao {
    private Set<User> users = new ConcurrentSkipListSet<User>();

    public void saveUser(User user) {
        users.add(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void deleteUser(User userToDelete) {
        for (User user : users) {
            if (user.equals(userToDelete)) {
                users.remove(user);
                break;
            }
        }
    }

    public void updateUser(User userToUpdate) {
        for (User user : users) {
            if (user.equals(userToUpdate)) {
                user.setName(userToUpdate.getName());
                user.setRoles(userToUpdate.getRoles());
            }
        }
    }

    public User findUser(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }


    public User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public Set<User> findUsers(String name) {
        Set<User> foundUsers = new HashSet<User>();
        for (User user : users) {
            if (user.getName().equals(name)) {
                foundUsers.add(user);
            }
        }
        return foundUsers;
    }
}
