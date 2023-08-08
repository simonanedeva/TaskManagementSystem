package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;


public class ShowMemberActivity extends BaseCommand{
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowMemberActivity(TMSRepository tmsRepository) {
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
        return sb.toString();
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }
}
