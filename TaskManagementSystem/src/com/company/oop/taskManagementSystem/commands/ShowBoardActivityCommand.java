package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowBoardActivityCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowBoardActivityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String teamName = parameters.get(0);
        return showBoardActivity(teamName);
    }

    private String showBoardActivity(String teamName) {
        Team team = getTmsRepository().findTeamByName(teamName);
        List<Board> boardItems = team.getBoards();

        StringBuilder sb = new StringBuilder();

        for (Board boardItem : boardItems) {
            sb.append(boardItem.getName()).append(System.lineSeparator()); //add member username for readability
            sb.append(boardItem.getActivityHistory()).append(System.lineSeparator());
            // TODO: 8.08.23 add PrintActivityHistory method to improve the printing in ShowTeam/Member/ActivityCommand
            // TODO: 8.08.23 Do a check if there is no activity and return that there is no activity.
        }
        return sb.toString();
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

}
