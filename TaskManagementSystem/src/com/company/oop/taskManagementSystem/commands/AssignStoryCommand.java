package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class AssignStoryCommand extends BaseCommand {

    private static final String TASK_REASSIGNED_SUCCESSFULLY = "Assignee of story %s successfully reassigned from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public AssignStoryCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String storyToReassign = parameters.get(0);
        String newAssignee = parameters.get(1);
        return reassignStory(storyToReassign, newAssignee);
    }

    private String reassignStory(String storyToReassign, String newAssignee) {
        Member member = getTmsRepository().getLoggedInMember();
        Team memberTeam = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Board> boardsList = memberTeam.getBoards();

        for (Board board : boardsList) {
            List<Story> stories = board.getStories();
            for (Story story : stories) {
                if (story.getTitle().equals(storyToReassign)) {
                    Member oldAssignee = getTmsRepository().findMemberByUsername(story.getAssignee());
                    story.changeAssignee(newAssignee);
                    story.logEvent(String.format("%s changed the assignee of story %s from %s to %s.", member.getUsername(), story.getTitle(), oldAssignee.getUsername(), newAssignee));
                    member.logEvent(String.format("%s changed the assignee of story %s from %s to %s.", member.getUsername(), story.getTitle(), oldAssignee.getUsername(), newAssignee));
                    return String.format(TASK_REASSIGNED_SUCCESSFULLY, storyToReassign, oldAssignee.getUsername(), newAssignee);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no story %s in team %s!", storyToReassign, memberTeam.getName()));
    }

    @Override
    protected boolean requiresLogin() {
        return true;
    }

}

