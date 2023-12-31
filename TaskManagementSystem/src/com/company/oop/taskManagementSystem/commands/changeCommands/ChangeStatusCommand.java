package com.company.oop.taskManagementSystem.commands.changeCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeStatusCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String STATUS_SET_SUCCESSFULLY = "Status of %s %s successfully changed from %s to %s!";
    public static final String NO_TASK_IN_TEAM_ERR_MESSAGE = "There is no task %s in team %s!";

    public ChangeStatusCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String taskStatusToChange = parameters.get(0);
        StatusValues newStatus = ParsingHelpers.tryParseEnum(parameters.get(1), StatusValues.class);
        return changeStatus(taskStatusToChange, newStatus);
    }

    private String changeStatus(String taskToChange, StatusValues newStatus) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();
        for (Board board : boardsList) {
            List<Task> tasks = board.getTasks();
            for (Task task : tasks) {
                if (task.getTitle().equals(taskToChange)) {
                    String oldStatus = task.getStatus().toString();
                    if (task.isValidStatus(newStatus)) {
                        task.changeStatus(newStatus);
                        task.logEvent(String.format("%s changed the status of %s %s from %s to %s.",
                                member.getUsername(), task.getType(), taskToChange, oldStatus, newStatus));
                        member.logEvent(String.format("%s changed the status of %s %s from %s to %s.", member.getUsername()
                                , task.getType(), taskToChange, oldStatus, newStatus));
                        return String.format(STATUS_SET_SUCCESSFULLY, task.getType(), taskToChange, oldStatus, newStatus);
                    }
                }
            }
        }
        throw new IllegalArgumentException(String.format(NO_TASK_IN_TEAM_ERR_MESSAGE, taskToChange, memberTeam.getName()));
    }
}
