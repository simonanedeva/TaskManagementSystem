package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ShowBoardActivityCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public static final String NO_ACTIVITY_FOR_BOARD = "Currently there is no activity to display for board %s.";


    public ShowBoardActivityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String boardName = parameters.get(0);
        return showBoardActivity(boardName);
    }

    private String showBoardActivity(String boardName) {
        List<Team> teams = getTmsRepository().getTeams();
        StringBuilder stringBuilder = new StringBuilder();
        // TODO: 9.08.23 maybe we can optimize this one bellow with a separate method (a variety of it is also
        //  repetitive among other classes).
        for (Team team : teams) {
            List<Board> boards = team.getBoards();
            for (Board board : boards) {
                if (board.getName().equals(boardName)){
                    stringBuilder.append(board.getName()).append(System.lineSeparator());
                    if (board.displayBoardActivityHistory().isEmpty()){
                        stringBuilder.append(String.format(NO_ACTIVITY_FOR_BOARD,board.getName()));
                        stringBuilder.append(System.lineSeparator());
                    } else {
                        stringBuilder.append(board.displayBoardActivityHistory());
                    }
                }
            }
        }

        return stringBuilder.toString();
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

}
