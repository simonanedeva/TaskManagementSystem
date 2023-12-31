package com.company.oop.taskManagementSystem.commands.createCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class CreateMemberCommand extends BaseCommand {
    public final static String MEMBER_REGISTERED = "Member %s registered successfully!";
    public final static String MEMBER_ALREADY_EXIST = "Member %s already exist. Choose a different username!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public CreateMemberCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String username = parameters.get(0);
        throwIfMemberExists(username);
        return registerMember(username);
    }

    private String registerMember(String username) {
        Member member = getTmsRepository().createMember(username);
        getTmsRepository().addMember(member);

        return String.format(MEMBER_REGISTERED, username);
    }

    private void throwIfMemberExists(String username) {
        if (getTmsRepository().memberExists(username)) {
            throw new IllegalArgumentException(
                    String.format(MEMBER_ALREADY_EXIST, username));
        }
    }

}
