package com.example.tasks.classes;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DailyLimit")
public class DayLimit {

    @Id
    @Column(name = "Date")
    private Date date;

    @Column(name = "TaskLimit", nullable = false)
    private int taskLimit;

    //Getter and setter

}
