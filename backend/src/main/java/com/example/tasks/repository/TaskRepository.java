package com.example.tasks.repository;

import com.example.tasks.classes.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
//call functions from db here
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Procedure(name = "AddNewTask")
    void addNewTask(@Param("Description") String description,
                    @Param("CategoryID") Integer categoryId,
                    @Param("Date") Date date,
                    @Param("StartTime") Date startTime,
                    @Param("EndTime") Date endTime,
                    @Param("Status") String status);
}
