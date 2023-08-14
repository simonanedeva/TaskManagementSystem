package com.company.oop.taskManagementSystem.models.contracts;

import java.util.ArrayList;
import java.util.List;

public interface Board {

    String getName();

    String displayBoardActivityHistory();

    List<Bug> getBugs();

    List<Story> getStories();

    List<Feedback> getFeedbacks();

    void addBug(Bug bug);

    void addFeedback(Feedback feedback);

    void addStory(Story story);
}
