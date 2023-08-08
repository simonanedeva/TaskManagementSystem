package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamActivityCommand extends BaseCommand{

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public static final String NO_ACTIVITY_FOR_MEMBER = "Currently there is no activity to display for member %s.";

    public ShowTeamActivityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String teamName = parameters.get(0); //extract the team name
        return showTeamActivity(teamName);
    }

    private String showTeamActivity(String teamName) {
        Team team = getTmsRepository().findTeamByName(teamName);
        List<Member> teamMembers = team.getMembers();

        StringBuilder sb = new StringBuilder();

        for (Member teamMember : teamMembers) {
            sb.append(teamMember.getUsername()).append(System.lineSeparator()); //add member username for readability
            if (teamMember.getActivityHistory().isEmpty()){
                sb.append(String.format(NO_ACTIVITY_FOR_MEMBER,teamMember.getUsername()));
            } else {
                sb.append(teamMember.displayActivityHistory());
            }
            //This command above replaces the code below and updates current functionality so that it returns a formatted string of activity and not a list
            //as well as a message in case there is no activity to show. Should test when we have same activity to add.
//            sb.append(teamMember.getActivityHistory()).append(System.lineSeparator()); //add member history
        }
        return sb.toString();
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }
}