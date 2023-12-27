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
}
