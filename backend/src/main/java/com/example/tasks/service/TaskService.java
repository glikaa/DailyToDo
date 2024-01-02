package com.example.tasks.service;

import com.example.tasks.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tasks.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.tasks.classes.Task;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    public TaskDto createTask(String description, Integer categoryId, Date date, Date startTime, Date endTime, String status) {
        Task newTask = new Task();
        newTask.setDescription(description);
        newTask.setCategoryId(categoryId);
        newTask.setDate(date);
        newTask.setStartTime(startTime);
        newTask.setEndTime(endTime);
        newTask.setStatus(status);

        Task savedTask = taskRepository.save(newTask);

        return convertToDto(savedTask);
    }
    private TaskDto convertToDto(Task task) {
        // Convert Task entity to TaskDto
        TaskDto dto = new TaskDto();
        dto.setDescription(task.getDescription());
        dto.setCategoryId(task.getCategoryId());
        dto.setDate(task.getDate());
        dto.setStartTime(task.getStartTime());
        dto.setEndTime(task.getEndTime());
        dto.setStatus(task.getStatus());
        return dto;
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
    public List<TaskDto> getTasksByDate(LocalDate date) {
        Date utilDate = java.sql.Date.valueOf(date);
        List<Task> tasks = taskRepository.findByDate(utilDate);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
