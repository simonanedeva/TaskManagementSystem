package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackCommand extends BaseCommand {

    private static final String FEEDBACK_CREATED = "Feedback %s created successfully in board %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    public static final String RATING_WHOLE_NUMBER_ERR_MESSAGE = "Rating must be a whole number1";

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

        return createFeedback(title, boardToAdd, description, rating);
    }

    private String createFeedback(String title, String boardToAdd, String description, int rating) {
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        // TODO: 9.08.23 We should consider optimizing here - the error message from FinTeamOfMember is too generic.
        List<Board> boards = teamOfLoggedInMember.getBoards();
        Board board = findBoardInTeam(boards, boardToAdd);
        List<Task> taskList = board.getTasks();
        throwIfTaskExist(title, taskList);
        Feedback feedbackToAdd = getTmsRepository().createFeedback(title, description, rating);
        board.addFeedback(feedbackToAdd);
        member.logEvent(String.format("Feedback %s created by member %s", title, member.getUsername()));
        feedbackToAdd.logEvent(String.format("Feedback %s created by member %s", title, member.getUsername()));

        return String.format(FEEDBACK_CREATED, title, boardToAdd);
    }

    private static void throwIfTaskExist(String nameOfTask, List<Task> taskList) {
        for (Task task : taskList) {
            if (task.getTitle().equals(nameOfTask)) {
                throw new IllegalArgumentException("Task with such a title already exists");
            }
        }
    }

    private Board findBoardInTeam(List<Board> boardList, String board) {
        for (Board board1 : boardList) {
            if (board1.getName().equals(board)) {
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

