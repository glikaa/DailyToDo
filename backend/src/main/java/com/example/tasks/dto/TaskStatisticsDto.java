package com.example.tasks.dto;

import java.util.Date;

public class TaskStatisticsDto {

    private Date date;
    private int categoryId;
    private int completedTasks;
    private int uncompletedTasks;
    private int totalTimeSpent;

    public TaskStatisticsDto() {}
    public TaskStatisticsDto(Date date, int categoryId, int completedTasks, int uncompletedTasks, int totalTimeSpent) {
        this.date = date;
        this.categoryId = categoryId;
        this.completedTasks = completedTasks;
        this.uncompletedTasks = uncompletedTasks;
        this.totalTimeSpent = totalTimeSpent;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getUncompletedTasks() {
        return uncompletedTasks;
    }

    public void setUncompletedTasks(int uncompletedTasks) {
        this.uncompletedTasks = uncompletedTasks;
    }

    public int getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(int totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

}
