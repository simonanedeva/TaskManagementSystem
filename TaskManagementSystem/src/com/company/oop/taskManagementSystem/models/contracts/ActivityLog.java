package com.company.oop.taskManagementSystem.models.contracts;

import java.time.LocalDateTime;

public interface ActivityLog {
    String getDescription();

    LocalDateTime getTimestamp();
    String viewInfo();


}
