package com.h2rd.refactoring.usermanagement.dao;

import com.h2rd.refactoring.usermanagement.User;

import java.util.Set;

public interface UserDao {

    void saveUser(User user);

    Set<User> getUsers();

    void deleteUser(User user);

    void updateUser(User user);

    User findUser(String email);

    User findUserByName(String name);

    Set<User> findUsers(String name);
}
