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
        String teamName = parameters.get(0);
        return showBoardActivity(teamName);
    }

    private String showBoardActivity(String teamName) {
        Team team = getTmsRepository().findTeamByName(teamName);
        List<Board> boardItems = team.getBoards();

        StringBuilder sb = new StringBuilder();

        for (Board boardItem : boardItems) {
            sb.append(boardItem.getName()).append(System.lineSeparator()); //add member username for readability
            if (boardItem.getActivityHistory().isEmpty()){
                sb.append(String.format(NO_ACTIVITY_FOR_BOARD,boardItem.getName()));
                sb.append(System.lineSeparator());
            } else {
                sb.append(boardItem.displayActivityHistory());
            }
            //This command above replaces the code below and updates current functionality so that it returns a formatted string of activity and not a list
            //as well as a message in case there is no activity to show. Should test when we have same activity to add.
//            sb.append(boardItem.getActivityHistory()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

}
