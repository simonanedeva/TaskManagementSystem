package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.StoryImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoryImplTests {


    public static final Priority VALID_PRIORITY = Priority.HIGH;
    public static final StorySize VALID_SIZE = StorySize.SMALL;
    public static final String VALID_ASSIGNEE = "Victor";

    @Test
    public void should_CreateStory_When_ValidInput() {
        StoryImpl story = initialize_Story();

        Assertions.assertAll(
                () -> assertEquals(TaskImplTests.VALID_TASK_ID, story.getId()),
                () -> assertEquals(TaskImplTests.VALID_TASK_TITLE, story.getTitle()),
                () -> assertEquals(TaskImplTests.VALID_TASK_DESCRIPTION, story.getDescription()),
                () -> assertEquals(VALID_PRIORITY, story.getPriority()),
                () -> assertEquals(VALID_SIZE, story.getSize()),
                () -> assertEquals(VALID_ASSIGNEE, story.getAssignee())
        );
    }

    // TODO: 14.08.23 think of a way to test invalidID 
    @Test
    public void constructor_Should_ThrowException_When_TitleTooShort() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(TaskImplTests.VALID_TASK_ID, "Short", TaskImplTests.VALID_TASK_DESCRIPTION, VALID_PRIORITY, VALID_SIZE, VALID_ASSIGNEE));
    }

    @Test
    public void constructor_Should_ThrowException_When_DescriptionTooShort() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(TaskImplTests.VALID_TASK_ID, TaskImplTests.VALID_TASK_TITLE, "Short", VALID_PRIORITY, VALID_SIZE, VALID_ASSIGNEE));
    }

    @Test
    public void getter_Should_Return_ValidPriority() {
        StoryImpl story = initialize_Story();

        assertEquals(VALID_PRIORITY, story.getPriority());
    }

    //TODO TESTS:
    //get size
    //get status
    //get assignee
    //get type
    //change priority method
    //change size method


    //Helpers method to initialize a valid StoryImpl
    public StoryImpl initialize_Story() {
        return new StoryImpl(TaskImplTests.VALID_TASK_ID, TaskImplTests.VALID_TASK_TITLE, TaskImplTests.VALID_TASK_DESCRIPTION, VALID_PRIORITY, VALID_SIZE, VALID_ASSIGNEE);
    }


}


//    @Test
//    public void should_CreateFeedback_When_ValidInput() {
//        int rating = 1;
//        Feedback feedback = new FeedbackImpl(id, title, description, rating);
//        Assertions.assertEquals(id, feedback.getId());
//        Assertions.assertEquals(title, feedback.getTitle());
//        Assertions.assertEquals(description, feedback.getDescription());
//        Assertions.assertEquals(rating, feedback.getRating());
//        Assertions.assertEquals(StatusValues.NEW.toString().toUpperCase(), feedback.getStatus());
//    }
//
//    @Test
//    public void should_ThrowException_When_NegativeRating() {
//        int rating = -1;
//        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(id, title, description, rating));
//    }
//
//    @Test
//    public void should_ThrowException_When_NewRatingSameAsOld() {
//        int oldRating = 3;
//        int newRating = 3;
//        Feedback feedback = new FeedbackImpl(id, title, description, oldRating);
//        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.changeRating(newRating));
//    }
//
//    @Test
//    public void should_ChangeRating_When_ValidNewRating(){
//        int oldRating = 3;
//        int newRating = 4;
//        Feedback feedback = new FeedbackImpl(id, title, description, oldRating);
//        feedback.changeRating(newRating);
//        Assertions.assertEquals(newRating, feedback.getRating());
//    }
//
//    @Test
//    public void should_ReturnType_When_Prompted(){
//        int rating = 3;
//        Feedback feedback = new FeedbackImpl(id, title, description, rating);
//        // TODO: 14-Aug-23 change to "Feedback" when fixed in Commands
//        Assertions.assertEquals("FeedbackImpl", feedback.getType());
//    }
//}