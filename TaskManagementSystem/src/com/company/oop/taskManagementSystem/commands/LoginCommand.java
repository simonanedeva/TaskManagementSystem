package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class LoginCommand extends BaseCommand {
    public final static String MEMBER_LOGGED_IN = "Member %s successfully logged in!";
    public final static String MEMBER_LOGGED_IN_ALREADY = "Member %s is logged in! Please log out first!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public LoginCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        throwIfMemberLoggedIn();
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String username = parameters.get(0);

        return login(username);
    }

    private String login(String username) {
        Member memberFound = getTmsRepository().findMemberByUsername(username);
        getTmsRepository().login(memberFound);
        return String.format(MEMBER_LOGGED_IN, username);
    }

    private void throwIfMemberLoggedIn() {
        if (getTmsRepository().hasLoggedInMember()) {
            throw new IllegalArgumentException(
                    String.format(MEMBER_LOGGED_IN_ALREADY, getTmsRepository().getLoggedInMember().getUsername())
            );
        }
    }

}
