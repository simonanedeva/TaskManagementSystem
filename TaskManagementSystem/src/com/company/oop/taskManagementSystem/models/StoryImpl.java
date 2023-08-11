package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Story;
import com.company.oop.taskManagementSystem.models.enums.*;

public class StoryImpl extends TaskImpl implements Story {
    
    private Priority priority;
    private StorySize size;
    private String assignee;

    public StoryImpl(int id, String title, String description, Priority priority, StorySize size, String assignee) {
        super(id, title, description);
        setPriority(priority);
        setSize(size);
        setAssignee(assignee);
        status = StatusValues.NOTDONE;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setSize(StorySize size) {
        this.size = size;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    protected StatusValues isValidStatus(StatusValues value) {
            StatusValues[] allowedValues = StatusStory.allowedValues;
            for (StatusValues allowedValue : allowedValues) {
                if (allowedValue == value) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Invalid enum value for this class");
        }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public StorySize getSize() {
        return size;
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
    public void changePriority(Priority newPriorityStatus) {
        if (newPriorityStatus.equals(priority)) {
            throw new IllegalArgumentException((String.format("Priority is already set at %s!", priority)));
        }
        setPriority(newPriorityStatus);
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }


}
