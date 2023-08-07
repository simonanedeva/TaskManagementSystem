package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class LoginCommand extends BaseCommand {
    private final static String MEMBER_LOGGED_IN = "Member %s successfully logged in!";
    //private final static String WRONG_USERNAME_OR_PASSWORD = "Wrong username or password!";
    public final static String MEMBER_LOGGED_IN_ALREADY = "Member %s is logged in! Please log out first!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1; //switch to 2 if password implemented!!!
    public LoginCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        throwIfMemberLoggedIn();
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String username = parameters.get(0);
        //String password = parameters.get(1);

        return login(username); //don't forget to add password here if needed
    }

    private String login(String username) {
        Member memberFound = getTmsRepository().findMemberByUsername(username);

 /*       if (!userFound.getPassword().equals(password)) {
            throw new IllegalArgumentException(WRONG_USERNAME_OR_PASSWORD);
        }*/

        getTmsRepository().login(memberFound);
        return String.format(MEMBER_LOGGED_IN, username);
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

    private void throwIfMemberLoggedIn() {
        if (getTmsRepository().hasLoggedInMember()) {
            throw new IllegalArgumentException(
                    String.format(MEMBER_LOGGED_IN_ALREADY, getTmsRepository().getLoggedInMember().getUsername())
            );
        }
    }
}
