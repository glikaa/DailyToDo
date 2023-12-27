package com.example.tasks.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//composite key for TaskStatistics
public class TaskStatisticsId implements Serializable {
    private Date date;
    private int categoryId;

    //Constructors
    public TaskStatisticsId(){
    }
    public TaskStatisticsId(Date date, int categoryId){
        this.date = date;
        this.categoryId = categoryId;
    }
    //Getter and Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskStatisticsId that = (TaskStatisticsId) o;
        return categoryId == that.categoryId && Objects.equals(date, that.date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(date, categoryId);
    }
}
