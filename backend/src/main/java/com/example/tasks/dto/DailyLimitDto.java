package com.example.tasks.dto;

public class DailyLimitDto {
    private int taskLimit;

    // Constructor, Getters, and Setters
    public DailyLimitDto() {}

    public DailyLimitDto(int taskLimit) {
        this.taskLimit = taskLimit;
    }

    public int getTaskLimit() {
        return taskLimit;
    }

    public void setTaskLimit(int taskLimit) {
        this.taskLimit = taskLimit;
    }
}
