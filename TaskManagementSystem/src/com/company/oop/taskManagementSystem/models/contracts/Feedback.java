package com.company.oop.taskManagementSystem.models.contracts;

import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.FeedbackStatus;

public interface Feedback extends Task{

     int getRating();

     void changeRating(int newRating);

}
