package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.commands.ChangeFeedbackRatingCommand;
import com.company.oop.taskManagementSystem.models.contracts.Feedback;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusFeedback;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;

public class FeedbackImpl extends TaskImpl implements Feedback {

    private int rating;

    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, title, description);
        setRating(rating);
        status = StatusValues.NEW;
    }

    private void setRating(int rating) {
        if (rating >= 0) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException(ChangeFeedbackRatingCommand.RATING_SET_SUCCESSFULLY);
        }
    }

    @Override
    public int getRating() {
        return this.rating;
    }

    @Override
    public void changeRating(int newRating) {
        setRating(newRating);
    }

    @Override
    public String getStatus() {
        return status.toString();
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
}
