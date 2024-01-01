package com.example.tasks.service;

import com.example.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void createTask(String description, Integer categoryId, Date date, Date startTime, Date endTime, String status) {
        taskRepository.addNewTask(description, categoryId, date, startTime, endTime, status);
    }
}
