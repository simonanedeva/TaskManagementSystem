package com.company.oop.taskManagementSystem.models.contracts;


public interface Feedback extends Task{

     int getRating();

     void changeRating(int newRating);

}
