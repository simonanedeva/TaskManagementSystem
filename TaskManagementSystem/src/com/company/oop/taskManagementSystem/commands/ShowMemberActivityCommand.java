package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;


public class ShowMemberActivityCommand extends BaseCommand{
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowMemberActivityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String username = parameters.get(0);
        Member member = getTmsRepository().findMemberByUsername(username);
        List<ActivityLog> list = member.getActivityHistory();
        StringBuilder sb = new StringBuilder();
        for (ActivityLog activityLog : list) {
            sb.append(activityLog.viewInfo()).append(System.lineSeparator());
        }
        // TODO: 8.08.23 Implement some printing method in Member, which prints getActivityHistory! 
        // TODO: 8.08.23 Do a check if there is no activity and return that there is no activity. 
        return sb.toString();
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }
}
