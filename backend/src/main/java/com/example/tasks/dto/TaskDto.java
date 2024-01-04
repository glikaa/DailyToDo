package com.example.tasks.dto;
import java.util.Date;
public class TaskDto {
    private String description;
    private Integer categoryId;
    private Date date;
    private Date startTime; //can be null
    private Date endTime; //can be null
    private String status;
    // Getters
    public String getDescription() {
        return description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Date getDate() {
        return date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
