package com.company.oop.taskManagementSystemTests.commands.listTests;

import com.company.oop.taskManagementSystem.commands.assignCommands.AssignBugCommand;
import com.company.oop.taskManagementSystem.commands.listCommands.FilterCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
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
import java.util.stream.Collectors;

public class FilterCommandTests {
    private TMSRepository tmsRepository;
    private FilterCommand filterCommand;
    private Team team;
    private Board board;
    private Bug bug;
    List<String> params;

    @BeforeEach
    private void beforeEach(){
        tmsRepository = new TMSRepositoryImpl();
        filterCommand = new FilterCommand(tmsRepository);
        params = new ArrayList<>();
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
        board.addBug(bug);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> params = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> filterCommand.execute(params));
    }

    @Test
    public void filterAllTask_ShouldReturn_When_ValidInput(){
        Bug bug1 = new BugImpl(TaskConstants.VALID_TASK_ID, "ZZZZZZZZZZZZZZ",
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_STEPS_TO_REPRODUCE,
                TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SEVERITY,
                TaskConstants.VALID_TASK_ASSIGNEE);
        this.board.addBug(bug1);

        Assertions.assertEquals(bug.getTitle(),
                tmsRepository.getTeams().get(0).getBoards().get(0).getBugs().get(0).getTitle());
        Assertions.assertEquals(bug1.getTitle(),
                tmsRepository.getTeams().get(0).getBoards().get(0).getBugs().get(1).getTitle());

        params.add("Bugs");
        params.add("Assignee");
        params.add("Simona");
        Assertions.assertNotNull(filterCommand.execute(params));
        // TODO: 20-Aug-23 WHY?!?!?!
    }
}
