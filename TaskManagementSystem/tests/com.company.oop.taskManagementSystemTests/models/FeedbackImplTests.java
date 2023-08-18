package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.models.FeedbackImpl;
import com.company.oop.taskManagementSystem.models.StoryImpl;
import com.company.oop.taskManagementSystem.models.contracts.Feedback;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedbackImplTests {

    @Test
    public void should_CreateFeedback_When_ValidInput() {
        FeedbackImpl feedback = initializeFeedback();

        Assertions.assertAll(
                () -> assertEquals(TaskConstants.VALID_TASK_ID, feedback.getId()),
                () -> assertEquals(TaskConstants.VALID_TASK_TITLE, feedback.getTitle()),
                () -> assertEquals(TaskConstants.VALID_TASK_DESCRIPTION, feedback.getDescription()),
                () -> assertEquals(TaskConstants.VALID_RATING, feedback.getRating())
        );
    }

    @Test
    public void should_ThrowException_When_NegativeRating() {
        int rating = -1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, rating));
    }

    @Test
    public void should_ThrowException_When_NewRatingSameAsOld() {
        int oldRating = 3;
        int newRating = 3;
        Feedback feedback = new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, oldRating);
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.changeRating(newRating));
    }

    @Test
    public void should_ChangeRating_When_ValidNewRating() {
        int oldRating = 3;
        int newRating = 4;
        Feedback feedback = new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, oldRating);
        feedback.changeRating(newRating);
        Assertions.assertEquals(newRating, feedback.getRating());
    }

    @Test
    public void getter_Should_Return_ValidRaring() {
        FeedbackImpl feedback = initializeFeedback();

        assertEquals(TaskConstants.VALID_RATING, feedback.getRating());
    }


    public FeedbackImpl initializeFeedback() {
        return new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_RATING);
    }

}
