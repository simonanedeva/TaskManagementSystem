package com.company.oop.taskManagementSystem.commands.showCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamMembersCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_MEMBERS_MESSAGE = "Currently there are no members in %s.";

    public ShowTeamMembersCommand(TMSRepository tmsRepository) {
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
        return showTeamMembers(teamName);
    }

    private String showTeamMembers(String teamName) {
        Team team = getTmsRepository().findTeamByName(teamName);
        List<Member> teamMembers = team.getMembers();

        StringBuilder sb = new StringBuilder();
        if (teamMembers.isEmpty()) {
            sb.append(String.format(NO_MEMBERS_MESSAGE, teamName));
            return sb.toString();
        }
        for (Member member : teamMembers) {
            sb.append(member.getUsername()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString().trim();
        // TODO: 8.08.23 think about implementing a print method here as well, we have a repetition (ShowMembersCommand)
    }

}
