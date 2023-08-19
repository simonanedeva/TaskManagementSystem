package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BoardImpl implements Board {

    public static final int BOARD_NAME_MIN_LENGTH = 5;
    public static final int BOARD_NAME_MAX_LENGTH = 10;
    public static final String BOARD_NAME_LEN_ERR = String.format("Board name must be between %d and %d characters long!",
            BOARD_NAME_MIN_LENGTH, BOARD_NAME_MAX_LENGTH);

    private String name;
    private final List<Bug> bugs;
    private final List<Feedback> feedbacks;
    private final List<Story> stories;

    public BoardImpl(String name) {
        setName(name);
        bugs = new ArrayList<>();
        feedbacks = new ArrayList<>();
        stories = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Bug> getBugs() {
        return new ArrayList<>(bugs);
    }

    public List<Story> getStories() {
        return new ArrayList<>(stories);
    }

    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    public List<Task> getTasks() {
        ArrayList<Task> arrayList = new ArrayList<>();
        arrayList.addAll(getBugs());
        arrayList.addAll(getFeedbacks());
        arrayList.addAll(getStories());
        return arrayList;
    }

    public String displayBoardActivityHistory() {
        StringBuilder sb = new StringBuilder();
        for (Bug bug : getBugs()) {
            sb.append(bug.getTitle()).append(System.lineSeparator());
            sb.append(bug.displayActivityHistory()).append(System.lineSeparator());
        }
        for (Feedback feedback : getFeedbacks()) {
            sb.append(feedback.getTitle()).append(System.lineSeparator());
            sb.append(feedback.displayActivityHistory()).append(System.lineSeparator());
        }
        for (Story story : getStories()) {
            sb.append(story.getTitle()).append(System.lineSeparator());
            sb.append(story.displayActivityHistory()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void addBug(Bug bug) {
        bugs.add(bug);
    }

    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }

    public void addStory(Story story) {
        stories.add(story);
    }

    private void setName(String name) {
        ValidationHelpers.validateStringLength(name, BOARD_NAME_MIN_LENGTH, BOARD_NAME_MAX_LENGTH,
                BOARD_NAME_LEN_ERR);
        this.name = name;
    }

}
