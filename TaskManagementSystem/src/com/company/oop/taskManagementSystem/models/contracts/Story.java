package com.company.oop.taskManagementSystem.models.contracts;

import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;

public interface Story extends Task, AssigneeChangeable, AssigneeGettable {

    Priority getPriority();

    StorySize getSize();

    void changePriority(Priority newPriorityStatus);

    void changeSize(StorySize newSize);

}
