package com.company.oop.taskManagementSystemTests.commands.assignTests;

import com.company.oop.taskManagementSystem.commands.addCommands.AddCommentToTaskCommand;
import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.assignCommands.AssignBugCommand;
import com.company.oop.taskManagementSystem.commands.assignCommands.AssignStoryCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBugCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.TaskImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssignBugCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = AddCommentToTaskCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_TITLE = TestHelpers.getString(TaskImpl.TITLE_MIN_LENGTH + 1);
    public static final String VALID_DESCRIPTION = TestHelpers.getString(TaskImpl.DESCRIPTION_MIN_LENGTH + 1);
    public static final String VALID_PRIORITY = "HIGH";
    public static final String VALID_STEPS_TO_REPRODUCE = ("Open the application, Click Log In, The application freezes!");
    public static final String VALID_SEVERITY = "MINOR";
    public static final String VALID_ASSIGNEE = "Victor";
    public static final String NEW_ASSIGNEE = "Simona";


    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new AssignBugCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);

        AddMemberToTeamCommand memberToTeamCommand = new AddMemberToTeamCommand(repository);
        memberToTeamCommand.execute(List.of(member.getUsername(), team.getName()));

        CreateBoardInTeamCommand boardInTeamCommand = new CreateBoardInTeamCommand(repository);
        boardInTeamCommand.execute(List.of(boardName, team.getName()));

        CreateBugCommand createBugCommand = new CreateBugCommand(repository);
        createBugCommand.execute(List.of(VALID_DESCRIPTION, VALID_STEPS_TO_REPRODUCE, VALID_TITLE, boardName, VALID_PRIORITY,
                VALID_SEVERITY, VALID_ASSIGNEE));
    }

    @Test
    public void should_AssignBug_When_PassedValidInput() {
        Member member2 = repository.createMember(NEW_ASSIGNEE);
        repository.addMember(member2);
        AddMemberToTeamCommand memberToTeamCommand = new AddMemberToTeamCommand(repository);
        memberToTeamCommand.execute(List.of(member2.getUsername(), team.getName()));

        List<String> parameters = List.of(VALID_TITLE, NEW_ASSIGNEE);
        String commentAddedMessage = command.execute(parameters);

        Assertions.assertEquals(repository.getTeams().get(0).getBoards().get(0).getBugs().get(0).getAssignee(), NEW_ASSIGNEE);
        Assertions.assertEquals(String.format(AssignBugCommand.TASK_REASSIGNED_SUCCESSFULLY, VALID_TITLE, member.getUsername(), NEW_ASSIGNEE), commentAddedMessage);
    }

    @Test
    public void should_ThrowException_When_NoBugInTeam() {
        List<String> parameters = List.of("InvalidBugName", NEW_ASSIGNEE);

        String commentErrorMessage = null;

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            commentErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = String.format(AssignBugCommand.NO_BUG_ERR_MESSAGE, "InvalidBugName", team.getName());

        Assertions.assertEquals(commentErrorMessage, expectedErrorMessage);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        // Arrange
        List<String> params = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void should_ThrowException_When_InvalidAssignee() {
        List<String> parameters = List.of(VALID_TITLE, "Nikolay");

        String commentErrorMessage = null;

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            commentErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = String.format(AssignStoryCommand.ASSIGNEE_ERR_MESSAGE, "Nikolay", team.getName());

        Assertions.assertEquals(commentErrorMessage, expectedErrorMessage);
    }
}
