package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.*;
import com.company.oop.taskManagementSystem.models.enums.*;
import com.company.oop.taskManagementSystem.models.enums.contracts.StatusBug;

import javax.print.attribute.standard.Severity;
import java.util.ArrayList;
import java.util.List;

public class BugImpl extends TaskImpl implements Bug, AssigneeChangeable{

    private List<String> stepsToReproduce;
    private Priority priority;
    private BugSeverity severity;
    private String assignee;
    private List<Comment> comments;

    private List<ActivityLog> historyOfChanges;

    public BugImpl(int id, String title, String description, List<String> stepsToReproduce, Priority priority,
                   BugSeverity severity, String assignee) {
        super(id, title, description);
        this.stepsToReproduce = stepsToReproduce;
        setPriority(priority);
        setSeverity(severity);
        setAssignee(assignee);
        comments = new ArrayList<>();
        historyOfChanges = new ArrayList<>();
        status = StatusValues.ACTIVE;
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
    public StatusValues getStatus() {
        return status;
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
    public void changePriority(Priority newPriorityStatus) {
        if (newPriorityStatus.equals(priority)) {
            throw new IllegalArgumentException((String.format("Priority is already set at %s!", priority)));
        }
        setPriority(newPriorityStatus);
    }

    @Override
    public void changeSeverity(BugSeverity newSeverityStatus) {
        if (newSeverityStatus.equals(severity)) {
            throw new IllegalArgumentException((String.format("Severity is already set at %s!", severity)));
        }
        setSeverity(newSeverityStatus);
    }

    @Override
    public boolean isValidStatus(StatusValues value) {
        StatusValues[] allowedValues = StatusBug.allowedValues;
        for (StatusValues allowedValue : allowedValues) {
            if (allowedValue == value) {
                return true;
            }
        }
        throw new IllegalArgumentException("Invalid enum value");
    }

    @Override
    public void changeAssignee(String assignee) {
        if (this.assignee.equals(assignee)) {
            throw new IllegalArgumentException(String.format("Assignee already set to %s!", assignee));
        }
        setAssignee(assignee);
    }

    @Override
    public String toString() {
        return String.format("""
                %s
                Steps to Reproduce: %s
                Bug Priority: %s
                Bug Severity: %s
                Bug Assignee: %s
                """, super.toString(), getStepsToReproduce(), getPriority(), getSeverity(), getAssignee());
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    private void setSeverity(BugSeverity severity) {
        this.severity = severity;
    }

    private void setAssignee(String assignee) {
        this.assignee = assignee;
        // TODO: 7.08.23 make a validation, assignee muse be a member from the team (separate method validateAssignee)
    }
}
