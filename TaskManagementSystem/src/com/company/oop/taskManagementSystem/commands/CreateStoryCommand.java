package com.company.oop.taskManagementSystem.commands;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystem.utils.ParsingHelpers;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.List;

public class CreateStoryCommand extends BaseCommand{

    private static final String STORY_CREATED = "Story %s created successfully in board %s!";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 6;
    public static final String ASSIGNEE_ERR_MESSAGE = "Assignee %s is not part of team %s!";

    public CreateStoryCommand(TMSRepository tmsRepository) {
        super(tmsRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String description = parameters.get(0);
        String title = parameters.get(1);
        String boardToAdd = parameters.get(2);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(3), Priority.class);
        StorySize size = ParsingHelpers.tryParseEnum(parameters.get(4), StorySize.class);
        String assignee = parameters.get(5);
        return createStory(title,boardToAdd,description,priority, size, assignee);
    }

    private String createStory(String title, String boardToAdd, String description, Priority priority, StorySize size, String assignee) {
        Member member = getTmsRepository().getLoggedInMember();
        Team teamOfLoggedInMember = getTmsRepository().findTeamOfMember(member.getUsername());
        List<Member> membersInTeam = teamOfLoggedInMember.getMembers();
        throwIfInvalidAssignee(assignee, teamOfLoggedInMember, membersInTeam);

        List<Board> boards= teamOfLoggedInMember.getBoards();
        Board board = findBoardInTeam(boards,boardToAdd);
        Story storyToAdd = getTmsRepository().createStory(title,description,priority,size,assignee);
        List<Task> taskList = board.getTasks();
        throwIfTaskExist(title, taskList);

        board.addStory(storyToAdd);

        member.logEvent(String.format("Story %s created by member %s",title, member.getUsername()));
        storyToAdd.logEvent(String.format("Story %s created by member %s",title, member.getUsername()));

        return String.format(STORY_CREATED, title,boardToAdd);
    }

    private static void throwIfTaskExist(String nameOfTask, List<Task> taskList) {
        for (Task task : taskList) {
            if (task.getTitle().equals(nameOfTask)) {
                throw new IllegalArgumentException("Task with such a title already exists");
            }
        }
    }

    private static void throwIfInvalidAssignee(String assignee, Team teamOfLoggedInMember, List<Member> membersInTeam) {
        boolean isMember = false;
        for (Member member1 : membersInTeam) {
            if(member1.getUsername().equals(assignee)) {
                isMember=true;
            }
        }
        if (!isMember) {
            throw new IllegalArgumentException(String.format(ASSIGNEE_ERR_MESSAGE, assignee, teamOfLoggedInMember.getName()));
        }
    }

    private Board findBoardInTeam (List<Board> boardList, String board){
        for (Board board1 : boardList) {
            if (board1.getName().equals(board)){
                return board1;
            }
        }
        throw new IllegalArgumentException("Board does not exist in this team");
    }

    @Override
    protected boolean requiresLogin() {
        return false;
    }
}
