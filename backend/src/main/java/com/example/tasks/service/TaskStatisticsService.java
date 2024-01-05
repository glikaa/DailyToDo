package com.example.tasks.service;

import com.example.tasks.classes.TaskStatistics;
import com.example.tasks.dto.CategoryTaskSummaryDto;
import com.example.tasks.dto.TaskStatisticsDto;
import com.example.tasks.dto.TotalTaskSummaryDto;
import com.example.tasks.repository.TaskStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskStatisticsService {

    @Autowired
    private TaskStatisticsRepository taskStatisticsRepository;

    public List<TaskStatisticsDto> getAllTaskStatistics() {
        return taskStatisticsRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskStatisticsDto convertToDto(TaskStatistics taskStatistics) {
        TaskStatisticsDto dto = new TaskStatisticsDto();
        dto.setDate(taskStatistics.getDate());
        dto.setCategoryId(taskStatistics.getCategoryId());
        dto.setCompletedTasks(taskStatistics.getCompletedTasks());
        dto.setUncompletedTasks(taskStatistics.getUncompletedTasks());
        dto.setTotalTimeSpent(taskStatistics.getTotalTimeSpent());

        return dto;
    }

    public List<CategoryTaskSummaryDto> getTaskSummaryByCategory() {
        List<Object[]> summary = taskStatisticsRepository.getTaskSummaryByCategory();
        return summary.stream()
                .map(this::convertToObjectArrayToDto)
                .collect(Collectors.toList());
    }
    private CategoryTaskSummaryDto convertToObjectArrayToDto(Object[] array) {
        int categoryId = (Integer) array[0];
        int totalCompletedTasks = (Integer) array[1];
        int totalUncompletedTasks = (Integer) array[2];
        int totalTimeSpent = (Integer) array[3];
        return new CategoryTaskSummaryDto(categoryId, totalCompletedTasks, totalUncompletedTasks, totalTimeSpent);
    }

    public TotalTaskSummaryDto getTotalTaskSummary() {
        Object[] summary = taskStatisticsRepository.getTotalTaskSummary();
        if (summary != null) {
            int totalCompletedTasks = (Integer) summary[0];
            int totalUncompletedTasks = (Integer) summary[1];
            return new TotalTaskSummaryDto(totalCompletedTasks, totalUncompletedTasks);
        }
        return null;
    }

}
