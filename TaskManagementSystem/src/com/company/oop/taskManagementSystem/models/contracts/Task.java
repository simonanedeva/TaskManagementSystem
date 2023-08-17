package com.company.oop.taskManagementSystem.models.contracts;

import com.company.oop.taskManagementSystem.models.enums.StatusValues;

import java.util.List;

public interface Task {
    int getId();
    // TODO: 7.08.23 we can make a new interface eventually if necessary.
    
    String getTitle();

    String getDescription();

    List<Comment> getComments();
    // TODO: 7.08.23 maybe should be string - look at it later.
    //  We can shift this to a new interface Commentable.

    List<ActivityLog> getActivityHistory();

    String displayActivityHistory();

    String getStatus();

    void addComment(Comment comment);

    void removeComment(Comment comment);

    String getType();

    void changeStatus(StatusValues status);

    boolean isValidStatus(StatusValues status);

    void logEvent(String event);

    String returnTaskSimpleInfo();

}
