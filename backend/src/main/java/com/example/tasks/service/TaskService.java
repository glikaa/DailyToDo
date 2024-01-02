package com.example.tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tasks.repository.TaskRepository;

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
    public void deleteTask(int taskId){
        taskRepository.deleteTaskById(taskId);
    }
    public void updateTaskTime(int taskId, Date startTime, Date endTime){
        taskRepository.updateTaskTime(taskId, startTime, endTime);
    }
    public void assignCategoryToTask(int taskId, int categoryId){
        taskRepository.assignCategoryToTask(taskId, categoryId);
    }
    public void updateTaskStatus(int taskId, String status){
        taskRepository.updateTaskStatus(taskId, status);
    }
}
