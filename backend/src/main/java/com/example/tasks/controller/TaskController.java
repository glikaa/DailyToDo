package com.example.tasks.controller;

import com.example.tasks.classes.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasks.service.TaskService;
import com.example.tasks.dto.TaskDto;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody TaskDto taskDto){
        taskService.createTask(
                taskDto.getDescription(),
                taskDto.getCategoryId(),
                taskDto.getDate(),
                taskDto.getStartTime(),
                taskDto.getEndTime(),
                taskDto.getStatus());
        return ResponseEntity.ok("Task created successfully");
    }
}
