package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Comment;
import com.company.oop.taskManagementSystem.models.contracts.Task;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class TaskImpl implements Task {
    public static final int MAX_LENGTH_TITLE = 50;
    public static final int MIN_LENGTH_TITLE = 10;
    public static final int MIN_LENGTH_DESCRIPTION = 10;
    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final String INVALID_TITLE_LENGTH_MESSAGE = String.format("Title length should be between %d and %d characters long"
            , MIN_LENGTH_TITLE, MAX_LENGTH_TITLE);
    public static final String INVALID_DESCRIPTION_LENGTH_MESSAGE = String.format("Description length should be between %d and %d characters long"
            , MIN_LENGTH_DESCRIPTION, MAX_LENGTH_DESCRIPTION);


    private int id;
    private String title;
    private String description;
    private final List<Comment> comments;
    private final List<ActivityLog> historyOfChanges;

    public TaskImpl(int id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
        comments = new ArrayList<>();
        historyOfChanges = new ArrayList<>();
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
    public List<ActivityLog> getHistoryOfChanges() {
        return new ArrayList<>(historyOfChanges);
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setTitle(String title) {
        ValidationHelpers.validateStringLength(title, MIN_LENGTH_TITLE, MAX_LENGTH_TITLE, INVALID_TITLE_LENGTH_MESSAGE);
        this.title = title;
    }

    public void setDescription(String description) {
        ValidationHelpers.validateStringLength(description, MIN_LENGTH_DESCRIPTION, MAX_LENGTH_DESCRIPTION, INVALID_DESCRIPTION_LENGTH_MESSAGE);
        this.description = description;
    }
}
