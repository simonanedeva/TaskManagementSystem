package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.ActivityLog;
import com.company.oop.taskManagementSystem.models.contracts.Bug;
import com.company.oop.taskManagementSystem.models.contracts.Comment;
import com.company.oop.taskManagementSystem.models.enums.*;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends TaskImpl implements Bug {

    private List<String> stepsToReproduce;
    private Priority priority;
    private BugSeverity severity;
    private String assignee;
    private List<Comment> comments;

    private List<ActivityLog> historyOfChanges;

    public BugImpl(int id, String title, String description, List<String> stepsToReproduce, Priority priority,
                   BugSeverity severity, String assignee) {
        super(id, title, description);
        this.stepsToReproduce = new ArrayList<>();
        setPriority(priority);
        setSeverity(severity);
        setAssignee(assignee);
        comments = new ArrayList<>();
        historyOfChanges = new ArrayList<>();
        status = StatusValues.ACTIVE;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setSeverity(BugSeverity severity) {
        this.severity = severity;
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

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    // TODO: 11.08.23 improve; make boolean for the Change status validation
    protected StatusValues isValidStatus(StatusValues value) {
            StatusValues[] allowedValues = StatusBug.allowedValues;
            for (StatusValues allowedValue : allowedValues) {
                if (allowedValue == value) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Invalid enum value for this class");
        }
    }
