package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class ShowTeamActivityCommand extends BaseCommand{

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTeamActivityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String teamName = parameters.get(1); //extract the team name
        return showTeamActivity(teamName);
    }

    private String showTeamActivity(String teamName) {
        Team team = getTmsRepository().findTeamByName(teamName);
        List<Member> teamMembers = team.getMembers();

        StringBuilder sb = new StringBuilder();

        for (Member teamMember : teamMembers) {
            sb.append(teamMember.getUsername()).append(System.lineSeparator()); //add member username for readability
            sb.append(teamMember.getActivityHistory()).append(System.lineSeparator()); //add member history
            // TODO: 8.08.23 add PrintActivityHistory method to improve the printing in ShowTeam/Member/ActivityCommand
        }
        return sb.toString();
    }


    @Override
    protected boolean requiresLogin() {
        return false;
    }
}