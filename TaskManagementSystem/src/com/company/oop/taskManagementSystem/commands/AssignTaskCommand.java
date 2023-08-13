package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class AssignTaskCommand extends BaseCommand {

    private static final String TASK_REASSIGNED_SUCCESSFULLY = "Assignee of task %s successfully reassigned from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public AssignTaskCommand(TMSRepository tmsRepository) {
        super(tmsRepository);

    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String taskToReassign = parameters.get(0);
        String newAssignee = parameters.get(1);
        return reassignTask(taskToReassign, newAssignee);
    }

    private String reassignTask(String taskToReassign, String newAssignee) {
        Member member = getTmsRepository().getLoggedInMember();
        Member newAssigneeMember = getTmsRepository().findMemberByUsername(newAssignee);
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();

        for (Board board : boardsList) {
            List<Task> tasks = board.getTasks();
        //направи го през Board и си направи един метод за changeAssignee в Бъг и Стори, който да променя. Пак ще трябва да се каства :(.
            for (Task task : tasks) {
                if (task.getTitle().equals(taskToReassign)) {
                    if (task.getType().equals("FeedbackImpl")) {
                        throw new IllegalArgumentException("You cannot assign Feedback!");
                    }
                    AssigneeChangeable taskToChange = (AssigneeChangeable) task;
                    Member oldAssignee = getTmsRepository().findMemberByUsername(taskToChange.getAssignee());
                    taskToChange.changeAssignee(newAssignee);
                    newAssigneeMember.addTask(task);
                    oldAssignee.removeTask(task);
//                    tasks.remove(taskToChange); //shouldn't be removed from board
                    board.logEvent(String.format("%s changed the assignee of task %s from %s to %s.", member.getUsername(), task.getTitle(), oldAssignee.getUsername(), newAssignee));
                    member.logEvent(String.format("%s changed the priority of task %s from %s to %s.", member.getUsername(), task.getTitle(), oldAssignee.getUsername(), newAssignee));
                    return String.format(TASK_REASSIGNED_SUCCESSFULLY, taskToReassign, oldAssignee.getUsername(), newAssignee);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no story/bug %s in team %s!", taskToReassign, memberTeam.getName()));
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

}
