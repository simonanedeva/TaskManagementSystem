package com.company.oop.taskManagementSystem.commands.showCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamsCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    public static final String NO_TEAMS_MESSAGE = "No teams to show.";

    public ShowTeamsCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        return showTeams();
    }

    // TODO: 18.08.23 implement a method in Member for optimization (Viki knows)
    private String showTeams() {
        StringBuilder sb = new StringBuilder();
        List<Team> teamList = getTmsRepository().getTeams();
        if (teamList.isEmpty()) {
            sb.append(String.format(NO_TEAMS_MESSAGE));
            return sb.toString();
        }
        for (Team team : teamList) {
            sb.append(team.getName()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString().trim();
    }

}
