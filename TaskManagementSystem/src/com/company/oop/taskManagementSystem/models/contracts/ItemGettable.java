package com.company.oop.taskManagementSystem.models.contracts;

import java.util.List;

public interface ItemGettable<T> {
    List<T> getItem();
}
