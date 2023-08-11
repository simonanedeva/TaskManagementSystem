package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class ChangePriorityOfStory extends BaseCommand {

    private static final String PRIORITY_SET_SUCCESSFULLY = "Priority of story %s successfully changed from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public ChangePriorityOfStory(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    // TODO: 11.08.23 Not finished!
    // TODO: 11.08.23 discuss if logged in member can change story severity of other members
    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String storyToChange = parameters.get(0);
        Priority newPriorityStatus = ParsingHelpers.tryParseEnum(parameters.get(1), Priority.class);
        return changePriority(storyToChange, newPriorityStatus);

    }

    private String changePriority(String storyToChange, Priority newPriorityStatus) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMеmber(member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();
        for (Board board : boardsList) {
            List<Task> tasks = board.getTasks();
            for (Task task : tasks) {
                if (task.getTitle().equals(storyToChange)) {
                    //we need to cast just here. we can also have a try-catch for ClassCastException, which trows a more informative message.
                    try {
                        Story story = (Story) task;
                        String oldPriority = story.getPriority().toString();
                        story.changePriority(newPriorityStatus);
                        board.logEvent(String.format("%s changed the priority of story %s from %s to %s.", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
                        member.logEvent(String.format("%s changed the priority of story %s from %s to %s.", member.getUsername(), task.getTitle(), oldPriority, newPriorityStatus));
                        return String.format(PRIORITY_SET_SUCCESSFULLY, storyToChange, oldPriority, newPriorityStatus);
                    } catch (ClassCastException exception) {
                        throw new IllegalArgumentException(String.format("There is no story %s in team %s!", storyToChange, memberTeam.getName()));
                    }
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no story %s in team %s!", storyToChange, memberTeam.getName()));
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

}
