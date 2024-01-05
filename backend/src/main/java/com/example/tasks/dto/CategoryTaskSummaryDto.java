package com.example.tasks.dto;

public class CategoryTaskSummaryDto {
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTotalCompletedTasks() {
        return totalCompletedTasks;
    }

    public void setTotalCompletedTasks(int totalCompletedTasks) {
        this.totalCompletedTasks = totalCompletedTasks;
    }

    public int getTotalUncompletedTasks() {
        return totalUncompletedTasks;
    }

    public void setTotalUncompletedTasks(int totalUncompletedTasks) {
        this.totalUncompletedTasks = totalUncompletedTasks;
    }

    public int getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(int totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    private int categoryId;
    private int totalCompletedTasks;
    private int totalUncompletedTasks;
    private int totalTimeSpent;

    public CategoryTaskSummaryDto(int categoryId, int totalCompletedTasks, int totalUncompletedTasks, int totalTimeSpent) {
        this.categoryId = categoryId;
        this.totalCompletedTasks = totalCompletedTasks;
        this.totalUncompletedTasks = totalUncompletedTasks;
        this.totalTimeSpent = totalTimeSpent;
    }


}
