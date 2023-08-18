package com.company.oop.taskManagementSystem.models.contracts;

import java.util.List;

public interface Board extends ItemGettable {

    String getName();

    String displayBoardActivityHistory();

    List<Bug> getBugs();

    List<Story> getStories();

    List<Feedback> getFeedbacks();

    void addBug(Bug bug);

    void addFeedback(Feedback feedback);

    void addStory(Story story);

    List<Task> getTasks();
}
