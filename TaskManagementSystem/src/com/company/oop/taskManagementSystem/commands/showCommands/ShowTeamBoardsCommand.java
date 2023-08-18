package com.company.oop.taskManagementSystem.commands.showCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamBoardsCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_BOARDS_MESSAGE = "Currently there are no boards in %s.";

    public ShowTeamBoardsCommand(TMSRepository tmsRepository) {
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
        return showTeamBoards(teamName);
    }

    private String showTeamBoards(String teamName) {
        Team team = getTmsRepository().findTeamByName(teamName);
        List<Board> teamBoards = team.getBoards();

        StringBuilder sb = new StringBuilder();
        if (teamBoards.isEmpty()) {
            sb.append(String.format(NO_BOARDS_MESSAGE, teamName));
            return sb.toString();
        }
        for (Board board : teamBoards) {
            sb.append(board.getName()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString().trim();
    }

}
