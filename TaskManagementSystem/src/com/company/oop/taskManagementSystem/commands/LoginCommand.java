package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.User;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class LoginCommand extends BaseCommand {
    private final static String USER_LOGGED_IN = "User %s successfully logged in!";
    //private final static String WRONG_USERNAME_OR_PASSWORD = "Wrong username or password!";
    public final static String USER_LOGGED_IN_ALREADY = "User %s is logged in! Please log out first!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1; //switch to 2 if password implemented!!!
    public LoginCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        throwIfUserLoggedIn();
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String username = parameters.get(0);
        //String password = parameters.get(1);

        return login(username); //don't forget to add password here if needed
    }

    private String login(String username) {
        User userFound = getTmsRepository().findUserByUsername(username);

 /*       if (!userFound.getPassword().equals(password)) {
            throw new IllegalArgumentException(WRONG_USERNAME_OR_PASSWORD);
        }*/

        getTmsRepository().login(userFound);
        return String.format(USER_LOGGED_IN, username);
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

    private void throwIfUserLoggedIn() {
        if (getTmsRepository().hasLoggedInUser()) {
            throw new IllegalArgumentException(
                    String.format(USER_LOGGED_IN_ALREADY, getTmsRepository().getLoggedInUser().getUsername())
            );
        }
    }
}
