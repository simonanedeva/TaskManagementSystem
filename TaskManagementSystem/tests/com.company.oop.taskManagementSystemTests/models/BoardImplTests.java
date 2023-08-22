package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.models.BoardImpl;
import com.company.oop.taskManagementSystem.models.BugImpl;
import com.company.oop.taskManagementSystem.models.FeedbackImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystemTests.models.taskModels.BugImplTests;
import com.company.oop.taskManagementSystemTests.models.taskModels.FeedbackImplTests;
import com.company.oop.taskManagementSystemTests.models.taskModels.StoryImplTests;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardImplTests {
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 10;
    private static final String VALID_BOARD_NAME = TestHelpers.getString(NAME_MIN_LENGTH + 1);

    @Test
    public void constructor_Should_InitializeName_When_ArgumentsAreValid() {
        Board board = initilizeValidBoard();
        Assertions.assertEquals(VALID_BOARD_NAME, board.getName());
    }

    @Test
    public void constructor_Should_InitializeListOfBugs_When_ArgumentsAreValid() {
        Board board = initilizeValidBoard();
        Assertions.assertNotNull(board.getBugs());
    }

    @Test
    public void constructor_Should_InitializeListOfFeedbacks_When_ArgumentsAreValid() {
        Board board = initilizeValidBoard();
        Assertions.assertNotNull(board.getFeedbacks());
    }

    @Test
    public void constructor_Should_InitializeListOfStories_When_ArgumentsAreValid() {
        Board board = initilizeValidBoard();
        Assertions.assertNotNull(board.getStories());
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidNameLength() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new BoardImpl(TestHelpers.getString(NAME_MIN_LENGTH - 1))),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new BoardImpl(TestHelpers.getString(NAME_MAX_LENGTH + 1)))

        );
    }

    @Test
    public void addBug_Should_AddBugToBugList() {
        Board board = initilizeValidBoard();
        Bug bug = BugImplTests.initializeBug();

        board.addBug(bug);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, board.getBugs().size()),
                () -> Assertions.assertTrue(board.getBugs().contains(bug))
        );
    }

    @Test
    public void addFeedback_Should_AddFeedbackToFeedbackList() {
        Board board = initilizeValidBoard();
        Feedback feedback = FeedbackImplTests.initializeFeedback();

        board.addFeedback(feedback);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, board.getFeedbacks().size()),
                () -> Assertions.assertTrue(board.getFeedbacks().contains(feedback))
        );
    }

    @Test
    public void addStory_Should_AddStoryToStoryList() {
        Board board = initilizeValidBoard();
        Story story = StoryImplTests.initializeStory();

        board.addStory(story);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, board.getStories().size()),
                () -> Assertions.assertTrue(board.getStories().contains(story))
        );
    }

    @Test
    public void getBugs_Should_ReturnCopyOfCollection() {
        Board board = initilizeValidBoard();
        Bug bug = BugImplTests.initializeBug();
        board.addBug(bug);

        board.getBugs().clear();

        Assertions.assertEquals(1, board.getBugs().size());

    }

    @Test
    public void getFeedbacks_Should_ReturnCopyOfCollection() {
        Board board = initilizeValidBoard();
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        board.addFeedback(feedback);

        board.getFeedbacks().clear();

        Assertions.assertEquals(1, board.getFeedbacks().size());

    }

    @Test
    public void getStories_Should_ReturnCopyOfCollection() {
        Board board = initilizeValidBoard();
        Story story = StoryImplTests.initializeStory();
        board.addStory(story);

        board.getStories().clear();

        Assertions.assertEquals(1, board.getStories().size());
    }

    @Test
    public void getTasks_Should_ReturnCopyOfCollection() {
        Board board = initilizeValidBoard();
        Bug bug = BugImplTests.initializeBug();
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        Story story = StoryImplTests.initializeStory();
        board.addBug(bug);
        board.addFeedback(feedback);
        board.addStory(story);

        board.getTasks().clear();

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, board.getTasks().size()),
                () -> Assertions.assertTrue(board.getTasks().contains(bug)),
                () -> Assertions.assertTrue(board.getTasks().contains(feedback)),
                () -> Assertions.assertTrue(board.getTasks().contains(story))
        );
    }

    @Test
    public void displayBoardActivityHistory_ShouldReturnActivity_When_Prompted() {
        Board board = initilizeValidBoard();
        Bug bug = BugImplTests.initializeBug();
        Feedback feedback = FeedbackImplTests.initializeFeedback();
        Story story = StoryImplTests.initializeStory();
        board.addBug(bug);
        board.addFeedback(feedback);
        board.addStory(story);

        Assertions.assertNotNull(board.displayBoardActivityHistory());
    }

    public static Board initilizeValidBoard() {
        return new BoardImpl(VALID_BOARD_NAME);
    }

}
