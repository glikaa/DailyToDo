package com.example.tasks.repository;
import com.example.tasks.classes.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;

//call functions from db here
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Procedure(name = "AddNewTask")
    void addNewTask(@Param("TaskName") String taskName, @Param("DueDate") Date dueDate);

}
