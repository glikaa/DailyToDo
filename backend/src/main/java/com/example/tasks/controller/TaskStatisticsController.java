package com.example.tasks.controller;

import com.example.tasks.dto.CategoryTaskSummaryDto;
import com.example.tasks.dto.TaskStatisticsDto;
import com.example.tasks.dto.TotalTaskSummaryDto;
import com.example.tasks.service.TaskStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-statistics")
public class TaskStatisticsController {

    @Autowired
    private TaskStatisticsService taskStatisticsService;

    @GetMapping
    public ResponseEntity<List<TaskStatisticsDto>> getAllTaskStatistics() {
        List<TaskStatisticsDto> statistics = taskStatisticsService.getAllTaskStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/summary-by-category")
    public ResponseEntity<List<CategoryTaskSummaryDto>> getTaskSummaryByCategory() {
        List<CategoryTaskSummaryDto> summary = taskStatisticsService.getTaskSummaryByCategory();
        return ResponseEntity.ok(summary);
    }
    public ResponseEntity<TotalTaskSummaryDto> getTotalTaskSummary() {
        TotalTaskSummaryDto summary = taskStatisticsService.getTotalTaskSummary();
        if (summary != null) {
            return ResponseEntity.ok(summary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
