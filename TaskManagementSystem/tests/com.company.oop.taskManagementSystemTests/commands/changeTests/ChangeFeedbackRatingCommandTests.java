package com.company.oop.taskManagementSystemTests.commands.changeTests;

import com.company.oop.taskManagementSystem.commands.changeCommands.ChangeFeedbackRatingCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystemTests.commands.createTests.CreateFeedbackCommandTests;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeFeedbackRatingCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = ChangeFeedbackRatingCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new ChangeFeedbackRatingCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);
        team.addMember(member);

        team.addBoard(repository.createBoard(boardName));

        Feedback feedback = repository.createFeedback(CreateFeedbackCommandTests.VALID_TITLE,
                CreateFeedbackCommandTests.VALID_DESCRIPTION, Integer.parseInt(CreateFeedbackCommandTests.VALID_RATING));
        team.getBoards().get(0).addFeedback(feedback);
    }

    @Test
    public void should_ChangeFeedbackRating_When_PassedValidInput() {
        List<String> parameters = List.of(CreateFeedbackCommandTests.VALID_TITLE, "5");

        command.execute(parameters);

        Assertions.assertEquals(5, repository.getTeams().get(0).getBoards().get(0).getFeedbacks().get(0).getRating());
    }
    @Test
    public void should_LogEventToFeedbackHistoryOfChanges_When_PassedValidInput() {
        List<String> parameters = List.of(CreateFeedbackCommandTests.VALID_TITLE, "5");

        command.execute(parameters);

        Assertions.assertEquals(1, repository.getTeams().get(0).getBoards().get(0).getFeedbacks().get(0).getActivityHistory().size());
    }

    @Test
    public void should_LogEventToLoggedInMemberHistoryOfChanges_When_PassedValidInput() {
        List<String> parameters = List.of(CreateFeedbackCommandTests.VALID_TITLE, "5");

        command.execute(parameters);

        Assertions.assertEquals(1, repository.getLoggedInMember().getActivityHistory().size());
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> parameters = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_FeedbackDoesNotExist() {
        List<String> parameters = List.of("SomeOtherFeedbackName", "5");

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_FeedbackRatingIsAlreadySetToPassedValue() {
        List<String> parameters = List.of(CreateFeedbackCommandTests.VALID_TITLE,
                String.valueOf(repository.getTeams().get(0).getBoards().get(0).getFeedbacks().get(0).getRating()));

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_FeedbackIsNegative() {
        List<String> parameters = List.of(CreateFeedbackCommandTests.VALID_TITLE, "-1");

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_FeedbackIsNotAnInt() {
        List<String> parameters = List.of(CreateFeedbackCommandTests.VALID_TITLE, "Gosho");

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_LoggedInMemberIsInOtherTeam() {
        repository.addTeam(repository.createTeam("AlcoHolics"));
        Team team = repository.findTeamByName("AlcoHolics");
        team.addMember(repository.createMember("Viktor"));
        team.addBoard(repository.createBoard("Shame"));
        Feedback testFeedback = repository.createFeedback("SomeOtherFeedbackName",
                CreateFeedbackCommandTests.VALID_DESCRIPTION, Integer.parseInt(CreateFeedbackCommandTests.VALID_RATING));
        team.getBoards().get(0).addFeedback(testFeedback);

        List<String> parameters = List.of("SomeOtherFeedbackName", "5");

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
}
