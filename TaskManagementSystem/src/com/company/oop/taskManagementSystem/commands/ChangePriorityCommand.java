package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ChangePriorityCommand extends BaseCommand {

    private static final String PRIORITY_SET_SUCCESSFULLY = "Priority of %s %s successfully changed from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public ChangePriorityCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String taskToChange = parameters.get(0);
        Priority newPriorityStatus = ParsingHelpers.tryParseEnum(parameters.get(1), Priority.class);
        return changePriority(taskToChange, newPriorityStatus);
    }

    private String changePriority(String taskToChange, Priority newPriorityStatus) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();

        for (Board board : boardsList) {
            List<Task> tasks = board.getTasks();

            for (Task task : tasks) {
                if (task.getTitle().equals(taskToChange)) {
                    String taskType;

                    if (task.getType().equals("StoryImpl")) {
                        //we need to cast just here; making a validation above to ensure casting success
                        taskType = "story";
                        Story story = (Story) task;
                        String oldPriority = story.getPriority().toString();
                        story.changePriority(newPriorityStatus);
                        board.logEvent(String.format("%s changed the priority of story %s from %s to %s.", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
                        member.logEvent(String.format("%s changed the priority of story %s from %s to %s.", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
                        return String.format(PRIORITY_SET_SUCCESSFULLY, taskType, taskToChange, oldPriority, newPriorityStatus);
                    }

                    if (task.getType().equals("BugImpl")) {
                        taskType = "bug";
                        Bug bug = (Bug) task;
                        String oldPriority = bug.getPriority().toString();
                        bug.changePriority(newPriorityStatus);
                        board.logEvent(String.format("%s changed the priority of bug %s from %s to %s.", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
                        member.logEvent(String.format("%s changed the priority of bug %s from %s to %s.", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
                        return String.format(PRIORITY_SET_SUCCESSFULLY, taskType, taskToChange, oldPriority, newPriorityStatus);
                    }
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no story/bug %s in team %s!", taskToChange, memberTeam.getName()));
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

}
