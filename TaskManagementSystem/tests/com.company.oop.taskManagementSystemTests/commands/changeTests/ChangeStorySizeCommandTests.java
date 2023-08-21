package com.company.oop.taskManagementSystemTests.commands.changeTests;

import com.company.oop.taskManagementSystem.commands.changeCommands.ChangeStorySizeCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Story;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystemTests.commands.createTests.CreateStoryCommandTests;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeStorySizeCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = ChangeStorySizeCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new ChangeStorySizeCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);
        team.addMember(member);

        team.addBoard(repository.createBoard(boardName));

        Story story = repository.createStory(CreateStoryCommandTests.VALID_TITLE,CreateStoryCommandTests.VALID_DESCRIPTION,
                Priority.LOW,StorySize.SMALL, CreateStoryCommandTests.VALID_ASSIGNEE);

        team.getBoards().get(0).addStory(story);
    }

    @Test
    public void should_ChangeStorySize_When_PassedValidInput() {
        List<String> parameters = List.of(CreateStoryCommandTests.VALID_TITLE, CreateStoryCommandTests.VALID_SIZE);

        command.execute(parameters);

        Assertions.assertEquals(StorySize.LARGE, repository.getTeams().get(0).getBoards().get(0).getStories().get(0).getSize());
    }

    @Test
    public void should_LogEventToStoryHistoryOfChanges_When_PassedValidInput() {
        List<String> parameters = List.of(CreateStoryCommandTests.VALID_TITLE, CreateStoryCommandTests.VALID_SIZE);

        command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().get(0).getBoards().get(0).getStories().get(0).getActivityHistory().size());
    }

    @Test
    public void should_LogEventToLoggedInMemberHistoryOfChanges_When_PassedValidInput() {
        List<String> parameters = List.of(CreateStoryCommandTests.VALID_TITLE, CreateStoryCommandTests.VALID_SIZE);

        command.execute(parameters);

        Assertions.assertEquals(1, repository.getLoggedInMember().getActivityHistory().size());
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> parameters = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_StoryDoesNotExist() {
        List<String> parameters = List.of("SomeOtherStoryName", CreateStoryCommandTests.VALID_SIZE);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_StorySizeIsAlreadySetToPassedValue() {
        List<String> parameters = List.of(CreateStoryCommandTests.VALID_TITLE,
                String.valueOf(repository.getTeams().get(0).getBoards().get(0).getStories().get(0).getSize()));

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_InvalidSize() {
        List<String> parameters = List.of(CreateStoryCommandTests.VALID_TITLE, "GoshoSMALL");

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_LoggedInMemberIsInOtherTeam() {
        repository.addTeam(repository.createTeam("AlcoHolics"));
        Team team = repository.findTeamByName("AlcoHolics");
        team.addMember(repository.createMember("Viktor"));
        team.addBoard(repository.createBoard("Shame"));
        Story testStory = repository.createStory("SomeOtherStoryName",
                CreateStoryCommandTests.VALID_DESCRIPTION,
                Priority.LOW,StorySize.SMALL, CreateStoryCommandTests.VALID_ASSIGNEE);
        team.getBoards().get(0).addStory(testStory);

        List<String> parameters = List.of("SomeOtherStoryName", CreateStoryCommandTests.VALID_SIZE);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
}
