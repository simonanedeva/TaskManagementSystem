package com.company.oop.taskManagementSystem.models.contracts;

import com.company.oop.taskManagementSystem.models.enums.StatusValues;

import java.util.List;

public interface Task {
    int getId();

    String getTitle();

    String getDescription();

    List<Comment> getComments();

    List<ActivityLog> getActivityHistory();

    String displayActivityHistory();

    StatusValues getStatus();

    void addComment(Comment comment);

    void removeComment(Comment comment);

    String getType();

    void changeStatus(StatusValues status);

    boolean isValidStatus(StatusValues status);

    void logEvent(String event);

    String returnTaskSimpleInfo();

}
