package com.company.oop.taskManagementSystemTests.commands.assignTests;

import com.company.oop.taskManagementSystem.commands.assignCommands.AssignBugCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.models.BoardImpl;
import com.company.oop.taskManagementSystem.models.BugImpl;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Bug;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AssignBugCommandTests {

    private TMSRepositoryImpl tmsRepository;

    private AssignBugCommand assignBugCommand;
    private Bug bug;
    private Team team;
    private Board board;


    @BeforeEach
    public void beforeEach(){
        tmsRepository= new TMSRepositoryImpl();
        assignBugCommand = new AssignBugCommand(tmsRepository);
        Member member = new MemberImpl(TaskConstants.VALID_MEMBER_NAME);
        this.tmsRepository.addMember(member);
        this.tmsRepository.login(member);
        team = new TeamImpl("Javaholics");
        this.tmsRepository.addTeam(team);
        team.addMember(tmsRepository.getLoggedInMember());
        board = new BoardImpl("BoardOne");
        this.team.addBoard(board);
        this.bug = new BugImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_STEPS_TO_REPRODUCE,
                TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SEVERITY,
                TaskConstants.VALID_TASK_ASSIGNEE);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> params = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> assignBugCommand.execute(params));
    }

    @Test
    public void should_AssignBug_When_ValidArguments() {
        List<String> parameters = new ArrayList<>();
        board.addBug(bug);
        Member member2 = new MemberImpl("Simona");
        team.addMember(member2);
        parameters.add(bug.getTitle());
        parameters.add(member2.getUsername());
        assignBugCommand.execute(parameters);
        Assertions.assertEquals(member2.getUsername(),
                tmsRepository.findTeamOfMember(member2.getUsername()).getBoards().get(0).getBugs().get(0).getAssignee());
    }

    @Test
    public void should_ThrowException_When_BugNotPresentInBoard(){
        List<String> parameters = new ArrayList<>();
        board.addBug(bug);
        Member member2 = new MemberImpl("Simona");
        team.addMember(member2);
        parameters.add("InvalidTitle");
        parameters.add(member2.getUsername());
        Assertions.assertThrows(IllegalArgumentException.class, () -> assignBugCommand.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_InvalidAssignee(){
        List<String> parameters = new ArrayList<>();
        board.addBug(bug);
        Member member2 = new MemberImpl("Simona");
        parameters.add(bug.getTitle());
        parameters.add(member2.getUsername());
        Assertions.assertThrows(IllegalArgumentException.class, () -> assignBugCommand.execute(parameters));
    }
}
