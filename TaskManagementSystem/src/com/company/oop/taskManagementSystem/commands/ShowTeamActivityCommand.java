package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamActivityCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public static final String NO_ACTIVITY_FOR_MEMBER = "Currently there is no activity to display for member %s.";

    public ShowTeamActivityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String teamName = parameters.get(0);
        return showTeamActivity(teamName);
    }

    private String showTeamActivity(String teamName) {
        Team team = getTmsRepository().findTeamByName(teamName);
        List<Member> teamMembers = team.getMembers();

        StringBuilder sb = new StringBuilder();

        teamMembers.forEach(teamMember -> {
            sb.append(teamMember.getUsername()).append(System.lineSeparator()); //add member username for readability
            if (teamMember.getActivityHistory().isEmpty()) {
                sb.append(String.format(NO_ACTIVITY_FOR_MEMBER, teamMember.getUsername()));
            } else {
                sb.append(teamMember.displayActivityHistory());
            }
        });
        return sb.toString();
    }

}