package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.CommentImpl;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

// TODO: 10.08.23 Test whether this allows the logged-in member to add comments to boards of other teams!
public class AddCommentToTaskCommand extends BaseCommand{
    private static final String COMMENT_ADDED_TO_TASK = "Comment added to task %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public AddCommentToTaskCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String commentToAdd = parameters.get(0);
        String taskToAdd = parameters.get(1);
        return addToTask(commentToAdd, taskToAdd);
    }

    // TODO: 10.08.23 the logic bellow is very bulky - we should consider rewriting it. 
    private String addToTask(String stringCommentToAdd, String taskToAdd) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMеmber(member.getUsername());
        Comment commentToAdd = new CommentImpl(stringCommentToAdd,member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();
        boolean taskExist = false;
        for (Board board : boardsList) {
            List<Task> tasks = board.getTasks();
            for (Task task : tasks) {
                if (task.getTitle().equals(taskToAdd)){
                    task.addComment(commentToAdd);
                    board.logEvent(String.format("%s added a comment to task %s",member.getUsername(), task.getTitle()));
                    member.logEvent(String.format("%s added a comment to task %s",member.getUsername(),task.getTitle()));
                    taskExist = true;
                    break;
                }
            }
        }
        if (!taskExist){
            throw new IllegalArgumentException(String.format("Such a task does not exit in team %s",memberTeam.getName()));
        }
        List<Member> memberList = memberTeam.getMembers();
        for (Member teamMember : memberList) {
            List<Task> tasks = teamMember.getTasks();
            for (Task task : tasks) {
                if (task.getTitle().equals(taskToAdd)) {
                    task.addComment(commentToAdd);
                }
            }
        }
        return String.format(COMMENT_ADDED_TO_TASK, taskToAdd);
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }
}
