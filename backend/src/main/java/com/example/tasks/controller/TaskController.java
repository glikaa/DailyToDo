package com.example.tasks.controller;

import com.example.tasks.classes.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tasks.service.TaskService;
import com.example.tasks.dto.TaskDto;

import java.util.Date;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    @GetMapping("/date")
    public ResponseEntity<List<TaskDto>> getTasksByDate(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now(); // Use current date if none is provided
        }
        List<TaskDto> tasks = taskService.getTasksByDate(date);
        return ResponseEntity.ok(tasks);
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
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }
    @PatchMapping("/{taskId}/time")
    public ResponseEntity<String> updateTaskTime(@PathVariable int taskId,
                                                 @RequestParam(required = false) Date startTime,
                                                 @RequestParam(required = false) Date endTime) {
        taskService.updateTaskTime(taskId, startTime, endTime);
        return ResponseEntity.ok("Task time updated successfully");
    }
    @PatchMapping("/{taskId}/category")
    public ResponseEntity<String> assignCategoryToTask(@PathVariable int taskId,
                                                       @RequestParam int categoryId) {
        taskService.assignCategoryToTask(taskId, categoryId);
        return ResponseEntity.ok("Category assigned to task successfully");
    }
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable int taskId,
                                                   @RequestParam String status) {
        taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok("Task status updated successfully");
    }
}
