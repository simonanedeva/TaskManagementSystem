package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Bug;
import com.company.oop.taskManagementSystem.models.contracts.Comment;
import com.company.oop.taskManagementSystem.models.enums.BugStatus;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.BugSeverity;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends TaskImpl implements Bug {

    private List<String> stepsToReproduce;
    private Priority priority;
    private BugSeverity severity;
    private BugStatus status;
    private String assignee;


    public BugImpl(int id, String title, String description, List<String> stepsToReproduce, Priority priority,
                   BugSeverity severity, BugStatus status, String assignee, List<Comment> comments, List<ActivityLog> historyOfChanges) {
        super(id, title, description);
        this.stepsToReproduce = new ArrayList<>();
        setPriority(priority);
        setSeverity(severity);
        setStatus(status);
        setAssignee(assignee);
        // TODO: 7.08.23 confirm with Victor if this is the correct constructor, do we need to add comments & historyOfChanges somewhere here?
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setSeverity(BugSeverity severity) {
        this.severity = severity;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    private void setAssignee(String assignee) {
        this.assignee = assignee;
        // TODO: 7.08.23 make a validation, assignee muse be a member from the team
    }

    // TODO: 7.08.23  TBC if in BugImpl or UserImpl
    public void addStepToReproduce(String step) {
        stepsToReproduce.add(step);
    }

    @Override
    public List<String> getStepsToReproduce() {
        return new ArrayList<>(stepsToReproduce);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public BugSeverity getSeverity() {
        return severity;
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

}