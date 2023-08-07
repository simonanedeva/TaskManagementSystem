package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Story;
import com.company.oop.taskManagementSystem.models.enums.Priority;
import com.company.oop.taskManagementSystem.models.enums.StorySize;
import com.company.oop.taskManagementSystem.models.enums.StoryStatus;

public class StoryImpl extends TaskImpl implements Story {
    
    private Priority priority;
    private StorySize size;
    private StoryStatus status;
    private String assignee;

    public StoryImpl(int id, String title, String description, Priority priority, StorySize size, StoryStatus status, String assignee) {
        super(id, title, description);
        setPriority(priority);
        setSize(size);
        setStatus(status);
        setAssignee(assignee);
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setSize(StorySize size) {
        this.size = size;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }
    
    public void setAssignee(String assignee) {
        this.assignee = assignee;
        // TODO: 7.08.23 make a validation here - assignee should be a member from the team
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
}
