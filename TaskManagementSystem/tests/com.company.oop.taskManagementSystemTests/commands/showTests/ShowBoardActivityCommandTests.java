package com.company.oop.taskManagementSystemTests.commands.showTests;

import com.company.oop.taskManagementSystem.commands.showCommands.ShowBoardActivityCommand;
import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.*;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowBoardActivityCommandTests {
    private ShowBoardActivityCommand showBoardActivityCommand;
    private Board board;
    List<String> params;

    @BeforeEach
    public void beforeEach() {
        TMSRepository tmsRepository = new TMSRepositoryImpl();
        showBoardActivityCommand = new ShowBoardActivityCommand(tmsRepository);
        params = new ArrayList<>();
        Member member = new MemberImpl(TaskConstants.VALID_MEMBER_NAME);
        tmsRepository.addMember(member);
        tmsRepository.login(member);
        Team team = new TeamImpl("Javaholics");
        tmsRepository.addTeam(team);
        team.addMember(tmsRepository.getLoggedInMember());
        board = new BoardImpl("BoardOne");
        team.addBoard(board);
    }

    @Test
    public void should_ThrowException_When_InvalidArgumentsCount() {
        List<String> params = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> showBoardActivityCommand.execute(params));
    }

    @Test
    public void showBoardActivity_ShouldReturn_When_ActivityPresent() {
        BugImpl bug = initializeBug();
        FeedbackImpl feedback = initializeFeedback();
        StoryImpl story = initializeStory();
        board.addFeedback(feedback);
        board.addStory(story);
        board.addBug(bug);

        params.add(board.getName());

        Assertions.assertNotNull(showBoardActivityCommand.execute(params));
    }

    @Test
    public void showBoardActivity_ShouldReturnErrorMessage_When_NoActivity() {
        params.add(board.getName());

        String comparator = String.format("%s", board.getName()) + System.lineSeparator() +
                String.format("%s", String.format(ShowBoardActivityCommand.NO_ACTIVITY_FOR_BOARD, board.getName()))
                + System.lineSeparator();

        Assertions.assertEquals(comparator, showBoardActivityCommand.execute(params));
    }

    /** Helper methods for Bug, Story and Feedback initialization */
    public static BugImpl initializeBug() {
        return new BugImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_STEPS_TO_REPRODUCE,
                TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SEVERITY, TaskConstants.VALID_TASK_ASSIGNEE);
    }
    public static FeedbackImpl initializeFeedback() {
        return new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_FEEDBACK_RATING);
    }
    public static StoryImpl initializeStory() {
        return new StoryImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_PRIORITY, TaskConstants.VALID_SIZE,
                TaskConstants.VALID_TASK_ASSIGNEE);
    }
}
