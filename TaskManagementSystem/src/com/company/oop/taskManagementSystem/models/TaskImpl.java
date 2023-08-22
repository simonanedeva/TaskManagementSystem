package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Comment;
import com.company.oop.taskManagementSystem.models.contracts.Task;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusBug;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusFeedback;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusStory;
import com.company.oop.taskManagementSystem.models.enums.StatusValues;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskImpl implements Task, StatusBug, StatusStory, StatusFeedback {
    public static final int TITLE_MAX_LENGTH = 50;
    public static final int TITLE_MIN_LENGTH = 10;
    public static final int DESCRIPTION_MIN_LENGTH = 10;
    public static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final String INVALID_TITLE_LENGTH_MESSAGE = String.format("Title length should be between %d and %d characters long"
            , TITLE_MIN_LENGTH, TITLE_MAX_LENGTH);
    public static final String INVALID_DESCRIPTION_LENGTH_MESSAGE = String.format("Description length should be between %d and %d characters long"
            , DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);


    private int id;
    private String title;
    private String description;
    private final List<Comment> comments;
    private final List<ActivityLog> activityHistory;
    protected StatusValues status;

    public TaskImpl(int id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
        comments = new ArrayList<>();
        activityHistory = new ArrayList<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public List<ActivityLog> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    public String displayActivityHistory() {
        StringBuilder sb = new StringBuilder();
        for (ActivityLog log : activityHistory) {
            if (activityHistory.isEmpty()) {
                sb.append("No activity to show.");
                return sb.toString();
            }
            sb.append(log.viewInfo());
        }
        return sb.toString().trim();
    }

    public void setDescription(String description) {
        ValidationHelpers.validateStringLength(description, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH, INVALID_DESCRIPTION_LENGTH_MESSAGE);
        this.description = description;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }


    public abstract boolean isValidStatus(StatusValues value);

    public void changeStatus(StatusValues status) {

        this.status = status;
    }

    public void logEvent(String event) {
        this.activityHistory.add(new ActivityLogImpl(event));
    }

    public String returnTaskSimpleInfo() {
        return String.format("""
                Task Title: %s
                Task Description: %s""", getTitle(), getDescription());
    }

    public String toString() {
        return String.format("""
                %s Title: %s
                %s Description: %s
                %s Status: %s""", getType(), getTitle(), getType(), getDescription(), getType(), getStatus().toString());
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setTitle(String title) {
        ValidationHelpers.validateStringLength(title, TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, INVALID_TITLE_LENGTH_MESSAGE);
        this.title = title;
    }

}
