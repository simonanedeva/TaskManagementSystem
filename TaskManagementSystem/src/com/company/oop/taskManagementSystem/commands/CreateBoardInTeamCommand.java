package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class CreateBoardInTeamCommand extends BaseCommand {

    private static final String BOARD_ADDED_TO_TEAM = "Board %s added to team %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String BOARD_IS_PART_OF_TEAM_ERR_MESSAGE = "Board %s is already a part of team %s!";

    public CreateBoardInTeamCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String boardToAdd = parameters.get(0);
        String teamToAdd = parameters.get(1);
        return addToTeam(boardToAdd, teamToAdd);
    }

    private String addToTeam(String boardToAdd, String teamToAdd) {
        Team team = getTmsRepository().findTeamByName(teamToAdd);
        throwIfBoardExists(boardToAdd, teamToAdd, team);

        Board board = getTmsRepository().createBoard(boardToAdd);

        team.addBoard(board);

        return String.format(BOARD_ADDED_TO_TEAM, boardToAdd, teamToAdd);
    }

    private static void throwIfBoardExists(String boardToAdd, String teamToAdd, Team team) {
        List<Board> boardsInTeam = team.getBoards();
        for (Board board : boardsInTeam) {
            if (board.getName().equals(boardToAdd)) {
                throw new IllegalArgumentException(String.format(BOARD_IS_PART_OF_TEAM_ERR_MESSAGE, boardToAdd, teamToAdd));
            }
        }
    }

}
