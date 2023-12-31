package com.company.oop.taskManagementSystem.commands.addCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.CommentImpl;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class AddCommentToTaskCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String COMMENT_ADDED_TO_TASK = "Comment added to task %s!";
    public static final String ТASK_NOT_EXIST_ERR_MESSAGE = "Such a task does not exit in team %s";

    public AddCommentToTaskCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String commentToAdd = parameters.get(0);
        String taskToAdd = parameters.get(1);
        return addToTask(commentToAdd, taskToAdd);
    }

    private String addToTask(String stringCommentToAdd, String taskToAdd) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        Comment commentToAdd = new CommentImpl(stringCommentToAdd, member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();
        boolean taskExist = false;
        for (Board board : boardsList) {
            List<Task> tasks = board.getTasks();
            taskExist = isTaskExist(taskToAdd, member, commentToAdd, taskExist, tasks);
        }
        if (taskExist) {
            return String.format(COMMENT_ADDED_TO_TASK, taskToAdd);
        }
        throw new IllegalArgumentException(String.format(ТASK_NOT_EXIST_ERR_MESSAGE, memberTeam.getName()));
    }

    private static boolean isTaskExist(String taskToAdd, Member member, Comment commentToAdd, boolean taskExist, List<Task> tasks) {
        for (Task task : tasks) {
            if (task.getTitle().equals(taskToAdd)) {
                task.addComment(commentToAdd);
                task.logEvent(String.format("%s added a comment to task %s", member.getUsername(), task.getTitle()));
                member.logEvent(String.format("%s added a comment to task %s", member.getUsername(), task.getTitle()));
                taskExist = true;
                break;
            }
        }
        return taskExist;
    }

}
