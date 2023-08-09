package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Feedback;
import com.company.oop.taskManagementSystem.models.enums.FeedbackStatus;

public class FeedbackImpl extends TaskImpl implements Feedback {

    private int rating;
    private FeedbackStatus status;

    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, title, description);
        setRating(rating);
        this.status = FeedbackStatus.NEW;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }

    @Override
    public int getRating() {
        return this.rating;
    }

    @Override
    public String getStatus() {
        return status.toString();
    }
}
