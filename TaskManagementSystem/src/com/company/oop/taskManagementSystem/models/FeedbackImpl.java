package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Feedback;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusFeedback;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;

public class FeedbackImpl extends TaskImpl implements Feedback {

    public static final String FEEDBACK_RATING_NEG_ERR_MESSAGE = "Feedback rating cannot be negative! Chose a positive whole number";
    private int rating;

    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, title, description);
        setRating(rating);
        status = StatusValues.NEW;
    }

    @Override
    public int getRating() {
        return this.rating;
    }

    @Override
    public void changeRating(int newRating) {
        if (newRating == this.rating) {
            throw new IllegalArgumentException(String.format("Rating already set to %s!", this.rating));
        }
        setRating(newRating);
    }

    @Override
    public StatusValues getStatus() {
        return status;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean isValidStatus(StatusValues value) {
        StatusValues[] allowedValues = StatusFeedback.allowedValues;
        for (StatusValues allowedValue : allowedValues) {
            if (allowedValue == value) {
                return true;
            }
        }
        throw new IllegalArgumentException("Invalid enum value for this class");
    }

    public String toString() {
        return String.format("""
                %s
                Feedback Rating: %s
                """, super.toString(), getRating());
    }

    private void setRating(int rating) {
        if (rating >= 0) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException(FEEDBACK_RATING_NEG_ERR_MESSAGE);
        }
    }

}
