package com.company.oop.taskManagementSystem.models.contracts;

import java.util.List;

public interface Task {
    int getId();
    // TODO: 7.08.23 we can make a new interface eventually if necessary.
    
    String getTitle();

    String getDescription();

    List<Comment> getComments();
    // TODO: 7.08.23 maybe should be string - look at it later.

    List<ActivityLog> getHistoryOfChanges();
    // TODO: 7.08.23 same as above.

    String getStatus();
}
