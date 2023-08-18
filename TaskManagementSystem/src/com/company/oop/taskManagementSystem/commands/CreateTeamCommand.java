package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class CreateTeamCommand extends BaseCommand {
    private final static String TEAM_REGISTERED = "Team %s registered successfully!";
    private final static String TEAM_ALREADY_EXIST = "Team %s already exist. Choose a different name for a team!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public CreateTeamCommand(TMSRepository tmsRepository) {
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
        throwIfTeamExists(teamName);
        return registerTeam(teamName);
    }

    private String registerTeam(String teamName) {
        Team team = getTmsRepository().createTeam(teamName);
        getTmsRepository().addTeam(team);

        return String.format(TEAM_REGISTERED, teamName);
    }

    private void throwIfTeamExists(String teamName) {
        if (getTmsRepository().teamExists(teamName)) {
            throw new IllegalArgumentException(
                    String.format(TEAM_ALREADY_EXIST, teamName));
        }
    }

}
