package com.company.oop.taskManagementSystem.core.contracts;

import com.company.oop.taskManagementSystem.models.contracts.User;

import java.util.List;

public interface TMSRepository {
    List<User> getUsers();

    User getLoggedInUser();

    void addUser(User userToAdd);

    User findUserByUsername(String username);

    boolean hasLoggedInUser();

    void login(User user);

    void logout();

    //TODO UserImpl ++ квото друго има покрай него
}
