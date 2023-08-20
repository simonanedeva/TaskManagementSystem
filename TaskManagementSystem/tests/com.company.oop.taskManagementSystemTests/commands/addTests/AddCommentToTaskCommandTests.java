package com.company.oop.taskManagementSystemTests.commands.addTests;

import com.company.oop.taskManagementSystem.commands.addCommands.AddCommentToTaskCommand;
import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateStoryCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.TaskImpl;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Story;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddCommentToTaskCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = AddCommentToTaskCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_TITLE = TestHelpers.getString(TaskImpl.TITLE_MIN_LENGTH + 1);
    public static final String VALID_DESCRIPTION = TestHelpers.getString(TaskImpl.DESCRIPTION_MIN_LENGTH + 1);
    public static final String VALID_PRIORITY = "HIGH";
    public static final String VALID_SIZE = "LARGE";
    public static final String VALID_ASSIGNEE = "Victor";
    private static final String VALID_COMMENT = "This is a valid comment.";

    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";
    private Story story;

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new AddCommentToTaskCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);

        AddMemberToTeamCommand memberToTeamCommand = new AddMemberToTeamCommand(repository);
        memberToTeamCommand.execute(List.of(member.getUsername(), team.getName()));

        CreateBoardInTeamCommand boardInTeamCommand = new CreateBoardInTeamCommand(repository);
        boardInTeamCommand.execute(List.of(boardName, team.getName()));

        CreateStoryCommand createStoryCommand = new CreateStoryCommand(repository);
        createStoryCommand.execute(List.of(VALID_DESCRIPTION, VALID_TITLE, boardName, VALID_PRIORITY,
                VALID_SIZE, VALID_ASSIGNEE));

    }

    @Test
    public void should_AddCommentToTask_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_COMMENT, VALID_TITLE);

        String commentAddedMessage = command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().get(0).getBoards().get(0).getStories().get(0).getComments().size());

        Assertions.assertEquals(String.format(AddCommentToTaskCommand.COMMENT_ADDED_TO_TASK, VALID_TITLE), commentAddedMessage);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        // Arrange
        List<String> params = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void should_ThrowException_When_NoArguments() {
        List<String> parameters = List.of();

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_TaskNotExist() {
        List<String> parameters = List.of(VALID_COMMENT, "NewTask");

        String commentErrorMessage = null;

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            commentErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = String.format(AddCommentToTaskCommand.ТASK_NOT_EXIST_ERR_MESSAGE, team.getName());

        Assertions.assertEquals(commentErrorMessage, expectedErrorMessage);
    }

}


