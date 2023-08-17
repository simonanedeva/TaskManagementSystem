package com.company.oop.taskManagementSystem.models.contracts;

import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.Priority;

import java.util.List;

public interface Bug extends Task, AssigneeChangeable, AssigneeGettable{

    List<String> getStepsToReproduce();

    Priority getPriority();

    BugSeverity getSeverity();



    void changePriority(Priority newPriorityStatus);

    void changeSeverity(BugSeverity newSeverityStatus);


}
