package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.models.FeedbackImpl;
import com.company.oop.taskManagementSystem.models.contracts.Feedback;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedbackImplTests {
    private int rating;
    int id;
    String title;
    String description;

    @BeforeEach
    public void setUp() {
        id = 1;
        title = "ValidTitle";
        description = "This should be a valid description";
    }

    @Test
    public void should_CreateFeedback_When_ValidInput() {
        int rating = 1;
        Feedback feedback = new FeedbackImpl(id, title, description, rating);
        Assertions.assertEquals(id, feedback.getId());
        Assertions.assertEquals(title, feedback.getTitle());
        Assertions.assertEquals(description, feedback.getDescription());
        Assertions.assertEquals(rating, feedback.getRating());
        Assertions.assertEquals(StatusValues.NEW.toString(), feedback.getStatus().toString());
    }

    @Test
    public void should_ThrowException_When_NegativeRating() {
        int rating = -1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FeedbackImpl(id, title, description, rating));
    }

    @Test
    public void should_ThrowException_When_NewRatingSameAsOld() {
        int oldRating = 3;
        int newRating = 3;
        Feedback feedback = new FeedbackImpl(id, title, description, oldRating);
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.changeRating(newRating));
    }

    @Test
    public void should_ChangeRating_When_ValidNewRating(){
        int oldRating = 3;
        int newRating = 4;
        Feedback feedback = new FeedbackImpl(id, title, description, oldRating);
        feedback.changeRating(newRating);
        Assertions.assertEquals(newRating, feedback.getRating());
    }

    @Test
    public void should_ReturnType_When_Prompted(){
        int rating = 3;
        Feedback feedback = new FeedbackImpl(id, title, description, rating);
        // TODO: 14-Aug-23 change to "Feedback" when fixed in Commands
        Assertions.assertEquals("FeedbackImpl", feedback.getType());
    }
}
