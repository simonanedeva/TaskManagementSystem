package com.company.oop.taskManagementSystemTests.commands.changeTests;

import com.company.oop.taskManagementSystem.commands.changeCommands.ChangeBugSeverityCommand;
import com.company.oop.taskManagementSystem.commands.changeCommands.ChangeStatusCommand;
import com.company.oop.taskManagementSystem.commands.contracts.Command;
import com.company.oop.taskManagementSystem.commands.createCommands.CreateFeedbackCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystemTests.commands.createTests.CreateBugCommandTests;
import com.company.oop.taskManagementSystemTests.commands.createTests.CreateFeedbackCommandTests;
import com.company.oop.taskManagementSystemTests.commands.createTests.CreateStoryCommandTests;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeStatusCommandTests {


    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = ChangeStatusCommand.EXPECTED_NUMBER_OF_ARGUMENTS;
    private Command command;
    private TMSRepository repository;
    private Member member;
    private Team team;
    private String boardName = "BoardOne";
    private String storyTitle = "ThisIsTheTitleOfTheStory";
    private String bugTitle = "ThisIsTheTitleOfTheBug";
    private String feedbackTitle = "ThisIsTheTitleOfTheFeedback";


    private Story story;
    private Bug bug;
    private Feedback feedback;

    @BeforeEach
    public void before() {
        this.repository = new TMSRepositoryImpl();
        this.command = new ChangeStatusCommand(repository);

        member = repository.createMember("Victor");
        repository.addMember(member);
        repository.login(member);

        team = repository.createTeam("JavaHolics");
        repository.addTeam(team);
        team.addMember(member);

        team.addBoard(repository.createBoard(boardName));

        //story
        story = repository.createStory(storyTitle,
                CreateStoryCommandTests.VALID_TITLE, Priority.LOW, StorySize.SMALL,
                CreateBugCommandTests.VALID_ASSIGNEE);
        team.getBoards().get(0).addStory(story);

        //bug
        bug = repository.createBug(bugTitle, boardName, CreateBugCommandTests.VALID_DESCRIPTION, List.of(CreateBugCommandTests.VALID_STEPS_TO_REPRODUCE.split(",")), Priority.LOW,
                BugSeverity.MINOR, CreateBugCommandTests.VALID_ASSIGNEE);
        team.getBoards().get(0).addBug(bug);

        //feedback
        feedback = repository.createFeedback(feedbackTitle, CreateFeedbackCommandTests.VALID_DESCRIPTION, 1);
        team.getBoards().get(0).addFeedback(feedback);
    }

    @Test
    public void should_ChangeStoryStatus_When_PassedValidInput() {

        String oldStatusValue = story.getStatus().toString();

        List<String> parameters = List.of(storyTitle, StatusValues.DONE.toString());

        String successMessage = command.execute(parameters);

        Assertions.assertEquals(StatusValues.DONE, repository.getTeams().get(0).getBoards().get(0).getStories().get(0).getStatus());
        Assertions.assertEquals(successMessage, String.format(ChangeStatusCommand.STATUS_SET_SUCCESSFULLY,story.getType(), storyTitle,  oldStatusValue, StatusValues.DONE));
    }

    @Test
    public void should_ThrowException_When_InvalidStoryStatusValue() {
        List<String> parameters = List.of(storyTitle, StatusValues.NEW.toString());

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ChangeBugStatus_When_PassedValidInput() {

        String oldStatusValue = bug.getStatus().toString();

        List<String> parameters = List.of(bugTitle, StatusValues.FIXED.toString());

        String successMessage = command.execute(parameters);

        Assertions.assertEquals(StatusValues.FIXED, repository.getTeams().get(0).getBoards().get(0).getBugs().get(0).getStatus());
        Assertions.assertEquals(successMessage, String.format(ChangeStatusCommand.STATUS_SET_SUCCESSFULLY,bug.getType(), bugTitle,  oldStatusValue, StatusValues.FIXED));
    }

    @Test
    public void should_ThrowException_When_InvalidBugStatusValue() {
        List<String> parameters = List.of(bugTitle, StatusValues.NOTDONE.toString());

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ChangeFeedbackStatus_When_PassedValidInput() {

        String oldStatusValue = feedback.getStatus().toString();

        List<String> parameters = List.of(feedbackTitle, StatusValues.UNSCHEDULED.toString());

        String successMessage = command.execute(parameters);

        Assertions.assertEquals(StatusValues.UNSCHEDULED, repository.getTeams().get(0).getBoards().get(0).getFeedbacks().get(0).getStatus());
        Assertions.assertEquals(successMessage, String.format(ChangeStatusCommand.STATUS_SET_SUCCESSFULLY,feedback.getType(), feedbackTitle,  oldStatusValue, StatusValues.UNSCHEDULED));
    }

    @Test
    public void should_ThrowException_When_InvalidFeedbackStatusValue() {
        List<String> parameters = List.of(feedbackTitle, StatusValues.NOTDONE.toString());

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> parameters = TestHelpers.getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_StoryNotExist() {
        List<String> parameters = List.of("SomeOtherStoryName", StatusValues.DONE.toString());

        String commentErrorMessage = null;

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            commentErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = format(ChangeStatusCommand.NO_TASK_IN_TEAM_ERR_MESSAGE, "SomeOtherStoryName", team.getName());

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_BugNotExist() {
        List<String> parameters = List.of("SomeOtherBugName", StatusValues.FIXED.toString());

        String commentErrorMessage = null;

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            commentErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = format(ChangeStatusCommand.NO_TASK_IN_TEAM_ERR_MESSAGE, "SomeOtherBugName", team.getName());

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_FeedbackNotExist() {
        List<String> parameters = List.of("SomeOtherFeedbackName", StatusValues.SCHEDULED.toString());

        String commentErrorMessage = null;

        try {
            command.execute(parameters);
        } catch (IllegalArgumentException e) {
            commentErrorMessage = e.getMessage();
        }

        String expectedErrorMessage = format(ChangeStatusCommand.NO_TASK_IN_TEAM_ERR_MESSAGE, "SomeOtherFeedbackName", team.getName());

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

}
