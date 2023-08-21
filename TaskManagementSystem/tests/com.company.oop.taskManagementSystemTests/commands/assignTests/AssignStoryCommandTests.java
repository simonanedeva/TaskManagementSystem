package com.company.oop.taskManagementSystemTests.commands.assignTests;

import com.company.oop.taskManagementSystem.commands.assignCommands.AssignStoryCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.models.*;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Story;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AssignStoryCommandTests {
    private TMSRepositoryImpl tmsRepository;

    private AssignStoryCommand assignStoryCommand;
    private Story story;
    private Team team;
    private final List<String> parameters = new ArrayList<>();
    private Member member2;

    @BeforeEach
    public void beforeEach(){
        tmsRepository= new TMSRepositoryImpl();
        assignStoryCommand = new AssignStoryCommand(tmsRepository);
        Member member = new MemberImpl(TaskConstants.VALID_MEMBER_NAME);
        this.tmsRepository.addMember(member);
        this.tmsRepository.login(member);
        team = new TeamImpl("Javaholics");
        this.tmsRepository.addTeam(team);
        team.addMember(tmsRepository.getLoggedInMember());
        Board board = new BoardImpl("BoardOne");
        this.team.addBoard(board);
        this.story = new StoryImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_PRIORITY,
                TaskConstants.VALID_SIZE, TaskConstants.VALID_TASK_ASSIGNEE);
        board.addStory(story);
        member2 = new MemberImpl("Simona");
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> params = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> assignStoryCommand.execute(params));
    }

    @Test
    public void should_AssignBug_When_ValidArguments() {
        team.addMember(member2);
        parameters.add(story.getTitle());
        parameters.add(member2.getUsername());
        assignStoryCommand.execute(parameters);
        Assertions.assertEquals(member2.getUsername(),
                tmsRepository.findTeamOfMember(member2.getUsername()).getBoards().get(0).getStories().get(0).getAssignee());
    }

    @Test
    public void should_ThrowException_When_BugNotPresentInBoard(){
        team.addMember(member2);
        parameters.add("InvalidTitle");
        parameters.add(member2.getUsername());
        Assertions.assertThrows(IllegalArgumentException.class, () -> assignStoryCommand.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_InvalidAssignee(){
        parameters.add(story.getTitle());
        parameters.add(member2.getUsername());
        Assertions.assertThrows(IllegalArgumentException.class, () -> assignStoryCommand.execute(parameters));
    }
}
