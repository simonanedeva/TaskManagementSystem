package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Feedback;
import com.company.oop.taskManagementSystem.models.enums.FeedbackStatus;
import com.company.oop.taskManagementSystem.models.enums.StatusBug;
import com.company.oop.taskManagementSystem.models.enums.StatusFeedback;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;

public class FeedbackImpl extends TaskImpl implements Feedback {

    private int rating;

    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, title, description);
        setRating(rating);
        status = StatusValues.NEW;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int getRating() {
        return this.rating;
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
    protected StatusValues isValidStatus(StatusValues value) {
        StatusValues[] allowedValues = StatusFeedback.allowedValues;
        for (StatusValues allowedValue : allowedValues) {
            if (allowedValue == value) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid enum value for this class");
    }
}
