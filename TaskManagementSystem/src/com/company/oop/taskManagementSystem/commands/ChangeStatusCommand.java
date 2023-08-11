package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeStatusCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String PRIORITY_SET_SUCCESSFULLY = "Priority of story %s successfully changed from %s to %s!";
    public ChangeStatusCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String taskStatusToChange = parameters.get(0);
        StatusValues newStatus = ParsingHelpers.tryParseEnum(parameters.get(1), StatusValues.class);
        return changeStatus(taskStatusToChange, newStatus);
    }

    private String changeStatus(String storyToChange, StatusValues newStatus) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();
        for (Board board : boardsList) {
            List<Task> tasks = board.getTasks();
            for (Task task : tasks) {
                if (task.getTitle().equals(storyToChange)) {
                    //we need to cast just here; making a validation above to ensure casting success
                    //Story story = (Story) task;
                    String oldPriority = task.getStatus();
                    if (task.isValidStatus(newStatus)){
                        task.changeStatus(newStatus);
                    }
                    board.logEvent(String.format("%s changed the status of %s %s from %s to %s.",
                            member.getUsername(), task.getType(), task.getTitle(), oldPriority, newStatus));
                    member.logEvent(String.format("%s changed the %s of story %s from %s to %s.", member.getUsername()
                            , task.getType(), task.getTitle(), oldPriority, newStatus));
                    return String.format(PRIORITY_SET_SUCCESSFULLY, storyToChange, oldPriority, newStatus);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no story %s in team %s!", storyToChange, memberTeam.getName()));
    }
}
