package com.example.tasks.dto;

public class TotalTaskSummaryDto {
    public TotalTaskSummaryDto(int totalCompleteTasks, int totalUncompleteTasks) {
        this.totalCompleteTasks = totalCompleteTasks;
        this.totalUncompleteTasks = totalUncompleteTasks;
    }

    public int getTotalCompleteTasks() {
        return totalCompleteTasks;
    }

    public void setTotalCompleteTasks(int totalCompleteTasks) {
        this.totalCompleteTasks = totalCompleteTasks;
    }

    public int getTotalUncompleteTasks() {
        return totalUncompleteTasks;
    }

    public void setTotalUncompleteTasks(int totalUncompleteTasks) {
        this.totalUncompleteTasks = totalUncompleteTasks;
    }

    private int totalCompleteTasks;
    private int totalUncompleteTasks;
}
