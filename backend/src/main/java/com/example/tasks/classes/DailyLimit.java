package com.example.tasks.classes;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DailyLimit")
public class DailyLimit {

    @Id
    @Column(name = "Date")
    private Date date;

    @Column(name = "TaskLimit", nullable = false)
    private int taskLimit;

    //Getter and setter
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public int getTaskLimit(){
        return taskLimit;
    }
    public void setTaskLimit(int taskLimit){
        this.taskLimit = taskLimit;
    }

}
