package com.company.oop.taskManagementSystem.commands.assignCommands;

import com.company.oop.taskManagementSystem.commands.BaseCommand;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class AssignStoryCommand extends BaseCommand {

    private static final String TASK_REASSIGNED_SUCCESSFULLY = "Assignee of story %s successfully reassigned from %s to %s!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String ASSIGNEE_ERR_MESSAGE = "Assignee %s is not part of team %s!";

    public AssignStoryCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected boolean requiresLogin() {
        return true;
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
        List<Member> membersInTeam = memberTeam.getMembers();
        List<Board> boardsList = memberTeam.getBoards();

        for (Board board : boardsList) {
            List<Story> stories = board.getStories();
            for (Story story : stories) {
                if (story.getTitle().equals(storyToReassign)) {
                    Member oldAssignee = getTmsRepository().findMemberByUsername(story.getAssignee());
                    throwIfInvalidAssignee(newAssignee, memberTeam, membersInTeam);
                    story.changeAssignee(newAssignee);
                    story.logEvent(String.format("%s changed the assignee of story %s from %s to %s.", member.getUsername(), story.getTitle(), oldAssignee.getUsername(), newAssignee));
                    member.logEvent(String.format("%s changed the assignee of story %s from %s to %s.", member.getUsername(), story.getTitle(), oldAssignee.getUsername(), newAssignee));
                    return String.format(TASK_REASSIGNED_SUCCESSFULLY, storyToReassign, oldAssignee.getUsername(), newAssignee);
                }
            }
        }
        throw new IllegalArgumentException(String.format("There is no story %s in team %s!", storyToReassign, memberTeam.getName()));
    }

    private static void throwIfInvalidAssignee(String assignee, Team teamOfLoggedInMember, List<Member> membersInTeam) {
        boolean isMember = false;
        for (Member member1 : membersInTeam) {
            if (member1.getUsername().equals(assignee)) {
                isMember = true;
            }
        }
        if (!isMember) {
            throw new IllegalArgumentException(String.format(ASSIGNEE_ERR_MESSAGE, assignee, teamOfLoggedInMember.getName()));
        }
    }

}

