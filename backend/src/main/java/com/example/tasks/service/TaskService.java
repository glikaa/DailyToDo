package com.example.tasks.service;

import com.example.tasks.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tasks.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;

import com.example.tasks.classes.Task;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final Tracer tracer;

    @Autowired
    public TaskService(TaskRepository taskRepository, Tracer tracer)
    {
        this.taskRepository = taskRepository;
        this.tracer = tracer;
    }
    public TaskDto createTask(String description, Integer categoryId, Date date, Date startTime, Date endTime, String status) {
        Span span = tracer.spanBuilder("createTask").startSpan();
        try (Scope scope = span.makeCurrent()) {
            Task newTask = new Task();
            newTask.setDescription(description);
            newTask.setCategoryId(categoryId);
            newTask.setDate(date);
            newTask.setStartTime(startTime);
            newTask.setEndTime(endTime);
            newTask.setStatus(status);

            Task savedTask = taskRepository.save(newTask);

            return convertToDto(savedTask);
        } finally {
            span.end();
        }
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
        Span span = tracer.spanBuilder("TaskService.deleteTask").startSpan();
        try (Scope scope = span.makeCurrent()) {
            taskRepository.deleteTaskById(taskId);
        } finally {
            span.end();
        }
    }
    public void updateTaskTime(int taskId, Date startTime, Date endTime){
        Span span = tracer.spanBuilder("TaskService.updateTaskTime").startSpan();
        try (Scope scope = span.makeCurrent()) {
            taskRepository.updateTaskTime(taskId, startTime, endTime);
        } finally {
            span.end();
        }
    }
    public void assignCategoryToTask(int taskId, int categoryId){
        Span span = tracer.spanBuilder("TaskService.assignCategoryToTask").startSpan();
        try (Scope scope = span.makeCurrent()) {
            taskRepository.assignCategoryToTask(taskId, categoryId);
        } finally {
            span.end();
        }
    }
    public void updateTaskStatus(int taskId, String status){
        Span span = tracer.spanBuilder("TaskService.updateTaskStatus").startSpan();
        try (Scope scope = span.makeCurrent()) {
            taskRepository.updateTaskStatus(taskId, status);
        } finally {
            span.end();
        }
    }
    public List<TaskDto> getTasksByDate(LocalDate date) {
        Span span = tracer.spanBuilder("TaskService.getTasksByDate").startSpan();
        try (Scope scope = span.makeCurrent()) {
            Date utilDate = java.sql.Date.valueOf(date);
            List<Task> tasks = taskRepository.findByDate(utilDate);
            return tasks.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } finally {
            span.end();
        }
    }

}
