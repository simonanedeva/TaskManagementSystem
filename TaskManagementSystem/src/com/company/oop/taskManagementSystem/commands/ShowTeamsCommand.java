package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamsCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    public ShowTeamsCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        return showTeams();
    }

    private String showTeams() {
        StringBuilder sb = new StringBuilder();
        List<Team> teamList = getTmsRepository().getTeams();
        for (Team team : teamList) {
            sb.append(team.getName()).append(", ");
        }
        sb.deleteCharAt(sb.length()-2);
        return sb.toString().trim();
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

}
