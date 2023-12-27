package com.example.tasks.classes;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TaskStatistics")
@IdClass(TaskStatisticsId.class)
public class TaskStatistics {

    @Id
    @Column(name = "Date", nullable = false)
    private Date date;

    @Id
    @Column(name = "CategoryID", nullable = false)
    private int categoryId;

    @Column(name = "CompletedTasks", nullable = false)
    private int completedTasks;

    @Column(name = "UncompletedTasks", nullable = false)
    private int uncompletedTasks;

    @Column(name = "TotalTimeSpent", nullable = false)
    private int totalTimeSpent;

    // Getters and setters
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
