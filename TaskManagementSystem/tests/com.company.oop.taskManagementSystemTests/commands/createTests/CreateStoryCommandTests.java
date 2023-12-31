package com.company.oop.taskManagementSystemTests.commands.createTests;

import com.company.oop.taskManagementSystem.commands.addCommands.AddMemberToTeamCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateBoardInTeamCommand;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateStoryCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.BoardImpl;
import com.company.oop.taskManagementSystem.models.StoryImpl;
import com.company.oop.taskManagementSystem.models.TaskImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Story;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateStoryCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = CreateStoryCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String VALID_TITLE = TestHelpers.getString(TaskImpl.TITLE_MIN_LENGTH + 1);
    public static final String VALID_DESCRIPTION = TestHelpers.getString(TaskImpl.DESCRIPTION_MIN_LENGTH + 1);
    public static final String VALID_PRIORITY = "HIGH";
    public static final String VALID_SIZE = "LARGE";
    public static final String VALID_ASSIGNEE = "Victor";
    public static final String VALID_ID = "1";

    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";

    //boardToAdd

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new CreateStoryCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);

        AddMemberToTeamCommand memberToTeamCommand = new AddMemberToTeamCommand(repository);
        memberToTeamCommand.execute(List.of(member.getUsername(), team.getName()));

        CreateBoardInTeamCommand boardInTeamCommand = new CreateBoardInTeamCommand(repository);
        //board = repository.createBoard("BoardOne");
        boardInTeamCommand.execute(List.of(boardName, team.getName()));
    }

    @Test
    public void should_CreateNewStory_When_PassedValidInput() {
        List<String> parameters = List.of(VALID_DESCRIPTION, VALID_TITLE, boardName, VALID_PRIORITY,
                VALID_SIZE, VALID_ASSIGNEE);

        String storyCreatedMessage = command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().get(0).getBoards().get(0).getStories().size());

        Assertions.assertEquals(String.format(CreateStoryCommand.STORY_CREATED, VALID_TITLE, repository.getTeams().get(0).getBoards().get(0).getName(),
                parameters.get(0)), storyCreatedMessage);
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
    public void should_ThrowException_When_AssigneeNotPartOfLoggedInMemberTeam() {
        List<String> parameters = List.of(VALID_DESCRIPTION, VALID_TITLE, boardName, VALID_PRIORITY,
                VALID_SIZE, "Nikolay");

        String storyErrorMessage = null;

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            storyErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = String.format(CreateStoryCommand.ASSIGNEE_ERR_MESSAGE, "Nikolay", team.getName(), repository.getTeams().get(0).getBoards().get(0).getName(),
                parameters.get(0));

        Assertions.assertEquals(expectedErrorMessage, storyErrorMessage);
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));

    }


}


