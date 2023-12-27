package com.example.tasks.classes;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskID")
    private int id;

    @Column(name = "Description", nullable = false, length = 100)
    private String description;

    @Column(name = "CategoryID")
    private Integer categoryId;

    @Column(name = "Date", nullable = false)
    private Date date;

    @Column(name = "StartTime")
    private Date startTime;

    @Column(name = "EndTime")
    private Date endTime;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    @Column(name = "CreateTime")
    private Date createTime;

    // Getters and setters
}
