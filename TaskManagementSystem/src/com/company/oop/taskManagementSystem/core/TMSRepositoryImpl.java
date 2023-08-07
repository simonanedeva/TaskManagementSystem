package com.company.oop.taskManagementSystem.core;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.User;

import java.util.ArrayList;
import java.util.List;

public class TMSRepositoryImpl implements TMSRepository {
    private static final String NO_LOGGED_IN_USER = "There is no logged in user.";
    private final static String NO_SUCH_USER = "There is no user with username %s!";
    private final static String USER_ALREADY_EXIST = "User %s already exist. Choose a different username!";
    private final List<User> users;
    private User loggedUser;

    public TMSRepositoryImpl() {
        this.users = new ArrayList<>();
        this.loggedUser = null;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void addUser(User userToAdd) {
        if (users.contains(userToAdd)) {
            throw new IllegalArgumentException(String.format(USER_ALREADY_EXIST, userToAdd.getUsername()));
        }
        this.users.add(userToAdd);
    }

    @Override
    public User findUserByUsername(String username) {
        User user = users
                .stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(NO_SUCH_USER, username)));
        return user;
    }

    @Override
    public User getLoggedInUser() {
        if (loggedUser == null) {
            throw new IllegalArgumentException(NO_LOGGED_IN_USER);
        }
        return loggedUser;
    }

    @Override
    public boolean hasLoggedInUser() {
        return loggedUser != null;
    }

    @Override
    public void login(User user) {
        loggedUser = user;
    }

    @Override
    public void logout() {
        loggedUser = null;
    }
}
