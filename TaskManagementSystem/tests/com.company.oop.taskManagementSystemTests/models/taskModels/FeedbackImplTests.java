package com.company.oop.taskManagementSystemTests.models.taskModels;

import com.company.oop.taskManagementSystem.models.FeedbackImpl;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FeedbackImplTests {

    @Test
    public void constructor_Should_CreateFeedback_When_ValidInput() {
        FeedbackImpl feedback = initializeFeedback();

        assertEquals(TaskConstants.VALID_TASK_ID, feedback.getId());
        assertEquals(TaskConstants.VALID_TASK_TITLE, feedback.getTitle());
        assertEquals(TaskConstants.VALID_TASK_DESCRIPTION, feedback.getDescription());
        assertEquals(TaskConstants.VALID_FEEDBACK_RATING, feedback.getRating());
    }

    @Test
    public void constructor_Should_ThrowException_When_TitleTooShort() {
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.INVALID_TASK_TITLE,
                        TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_FEEDBACK_RATING));
    }

    @Test
    public void constructor_Should_ThrowException_When_DescriptionTooShort() {
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE,
                        TaskConstants.INVALID_TASK_DESCRIPTION, TaskConstants.VALID_FEEDBACK_RATING));
    }

    @Test
    public void constructor_Should_ThrowException_When_RatingIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, -1));
    }

    @Test
    public void getter_Should_Return_ValidInitialStatus() {
        FeedbackImpl feedback = initializeFeedback();

        assertEquals(StatusValues.NEW, feedback.getStatus());
    }

    @Test
    public void getter_Should_Return_ValidType() {
        FeedbackImpl feedback = initializeFeedback();

        assertEquals("FeedbackImpl", feedback.getType());
    }

    @Test
    public void method_Should_Change_Rating() {
        FeedbackImpl feedback = initializeFeedback();

        feedback.changeRating(4);

        assertEquals(4, feedback.getRating());
    }

    @Test
    public void getter_Should_Change_Status() {
        FeedbackImpl feedback = initializeFeedback();

        feedback.changeStatus(StatusValues.UNSCHEDULED);

        assertEquals(StatusValues.UNSCHEDULED, feedback.getStatus());
    }

    @Test
    public void method_Should_ThrowException_When_NewRatingSameAsOld() {
        FeedbackImpl feedback = initializeFeedback();

        assertThrows(IllegalArgumentException.class, () -> feedback.changeRating(TaskConstants.VALID_FEEDBACK_RATING));
    }


    public static FeedbackImpl initializeFeedback() {
        return new FeedbackImpl(TaskConstants.VALID_TASK_ID, TaskConstants.VALID_TASK_TITLE, TaskConstants.VALID_TASK_DESCRIPTION, TaskConstants.VALID_FEEDBACK_RATING);
    }
}
