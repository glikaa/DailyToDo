package com.example.tasks.controller;

import com.example.tasks.classes.Task;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tasks.service.TaskService;
import com.example.tasks.dto.TaskDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private final TaskService taskService;
    private final Tracer tracer;
    @Autowired
    public TaskController(TaskService taskService, Tracer tracer)
    {
        this.taskService = taskService;
        this.tracer = tracer;
    }
    @GetMapping("/byDate")
    public ResponseEntity<List<TaskDto>> getTasksByDate(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Span span = tracer.spanBuilder("TaskController.getTasksByDate").startSpan();
        try (Scope scope = span.makeCurrent()) {
            if (date == null) {
                date = LocalDate.now();
            }
            List<TaskDto> tasks = taskService.getTasksByDate(date);
            return ResponseEntity.ok(tasks);
        } finally {
            span.end();
        }
    }
    @PostMapping
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto){
        Span span = tracer.spanBuilder("TaskController.addTask").startSpan();
        try (Scope scope = span.makeCurrent()) {
            TaskDto savedTaskDto = taskService.createTask(
                    taskDto.getDescription(),
                    taskDto.getCategoryId(),
                    taskDto.getDate(),
                    taskDto.getStartTime(),
                    taskDto.getEndTime(),
                    taskDto.getStatus());
            return ResponseEntity.ok(savedTaskDto);
        } finally {
            span.end();
        }
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        Span span = tracer.spanBuilder("TaskController.deleteTask").startSpan();
        try (Scope scope = span.makeCurrent()) {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok("Task deleted successfully");
        } catch (Exception e) {
            span.recordException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task");
        } finally {
            span.end();
        }
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
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable int taskId) {
        Span span = tracer.spanBuilder("TaskController.getTaskById").startSpan();
        try (Scope scope = span.makeCurrent()) {
            TaskDto task = taskService.getTaskById(taskId);
            if (task != null) {
                return ResponseEntity.ok(task);
            } else {
                return ResponseEntity.notFound().build();
            }
        } finally {
            span.end();
        }
    }

}
