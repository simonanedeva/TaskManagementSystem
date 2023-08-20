package com.company.oop.taskManagementSystem.commands.createCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackCommand extends BaseCommand {

    public static final String FEEDBACK_CREATED = "Feedback %s created successfully in board %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    public static final String RATING_WHOLE_NUMBER_ERR_MESSAGE = "Rating must be a whole number1";
    public static final String TITLE_EXIST_ERR_MESSAGE = "Task with such a title already exists!";
    public static final String BOARD_NOT_EXIST_ERR_MESSAGE = "Board %s does not exist in this team!";

    public CreateFeedbackCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
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
                throw new IllegalArgumentException(TITLE_EXIST_ERR_MESSAGE);
            }
        }
    }

    private Board findBoardInTeam(List<Board> boardList, String board) {
        for (Board board1 : boardList) {
            if (board1.getName().equals(board)) {
                return board1;
            }
        }
        throw new IllegalArgumentException(String.format(BOARD_NOT_EXIST_ERR_MESSAGE, board));
    }

}

