package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.commands.enums.CommandType;
import com.company.oop.taskManagementSystem.core.contracts.CommandFactory;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowMembersCommand extends BaseCommand {

    // TODO: 8.08.23 command works well with SHOWMEMBERS (+ it takes 1 parameter) see how to do it with 0 parameters

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    public ShowMembersCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        return showMembers();
    }

    private String showMembers() {
        StringBuilder sb = new StringBuilder();
        List<Member> memberList = getTmsRepository().getMembers();
        for (Member member : memberList) {
            sb.append(member.getUsername()).append(", ");
        }
        return sb.toString();
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

}
