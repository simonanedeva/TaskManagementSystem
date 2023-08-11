package com.company.oop.taskManagementSystem.models.contracts;

import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystem.models.enums.StoryStatus;

public interface Story extends Task{

    Priority getPriority();
    StorySize getSize();
    String getAssignee();
    void changePriority(Priority newPriorityStatus);
    void changeSize(StorySize newSize);

}
