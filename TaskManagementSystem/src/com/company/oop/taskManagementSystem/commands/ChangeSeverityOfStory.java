package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.CommentImpl;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeSeverityOfStory extends BaseCommand{

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public ChangeSeverityOfStory(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    // TODO: 11.08.23 Not finished!
    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String storyToChange = parameters.get(0);
        Priority newPriorityStatus = ParsingHelpers.tryParseEnum(parameters.get(1),Priority.class);
        return changeStatus(storyToChange, newPriorityStatus);

    }
    private String changeStatus(String storyToChange, Priority newPriorityStatus) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMеmber(member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();
        boolean taskExist = false;
        for (Board board : boardsList) {
            List<Task> tasks = board.getTasks();
            for (Task task : tasks) {
                if (task.getTitle().equals(storyToChange)) {
                    //we need to cast just here. we can also have a try-catch for ClassCastException, which trows a more informative message.
                    Story story = (Story) task;
                    String oldPriority = story.getPriority().toString();
                    //this one has to be implemented in StoryImpl;
                    story.changeStatus(newPriorityStatus);
                    board.logEvent(String.format("%s changed the status of task %s from %s to %s", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
                    member.logEvent(String.format("%s changed the status of task %s from %s to %s", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
                    taskExist = true;
                    break;
                }
            }
        }
        if (!taskExist) {
            throw new IllegalArgumentException(String.format("Such a task does not exit in team %s", memberTeam.getName()));
        }
        return String.format();
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }

}
