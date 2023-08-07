package com.company.oop.taskManagementSystem.models.contracts;

import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.enums.BugSeverity;
import com.company.oop.taskManagementSystem.models.enums.BugStatus;
import com.company.oop.taskManagementSystem.models.enums.Priority;

import java.util.List;

public interface Bug extends Task{

    List<String> getStepsToReproduce();

    Priority getPriority();

    BugSeverity getSeverity();

    String getAssignee();


}
