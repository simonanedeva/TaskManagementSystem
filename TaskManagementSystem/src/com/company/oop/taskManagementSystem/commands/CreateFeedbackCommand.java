package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackCommand extends BaseCommand{

    private static final String FEEDBACK_CREATED = "Feedback %s created successfully in board %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    public static final String BOARD_IS_PART_OF_TEAM_ERR_MESSAGE = "Board %s is already a part of team %s!";
    public static final String RATING_WHOLE_NUMBER_ERR_MESSAGE = "Rating must be a whole number";

    public CreateFeedbackCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String description = parameters.get(0);
        String title = parameters.get(1);
        String boardToAdd = parameters.get(2);
        int rating = ParsingHelpers.tryParseInt(parameters.get(3), RATING_WHOLE_NUMBER_ERR_MESSAGE);

        return createFeedback(title,boardToAdd,description,rating);
    }

    private String createFeedback(String title, String boardToAdd, String description, int rating){
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().FindTeamOfMEmeber(member.getUsername());
        // TODO: 9.08.23 We should consider optimizing here - the error message from FinTeamOfMember is too generic. 
        List<Board> boards= teamOfLoggedInMember.getBoards();
        Board board = findBoardInTeam(boards,boardToAdd);
        board.addTask(getTmsRepository().createFeedback(title,description,rating));
        member.logEvent(String.format("Feedback %s created by member %s",title, member.getUsername()));
        board.logEvent(String.format("Feedback %s created by member %s",title, member.getUsername()));
        // TODO: 9.08.23 creating a board through a task may turn out to be an issue.
        return String.format(FEEDBACK_CREATED, title,boardToAdd);
    }

    private Board findBoardInTeam (List<Board> boardList, String board){
        for (Board board1 : boardList) {
            if (board1.getName().equals(board)){
                return board1;
            }
        }
        throw new IllegalArgumentException("Board does not exist in this team");
    }


    @Override
    protected boolean requiresLogin() {
        return false;
    }
}

