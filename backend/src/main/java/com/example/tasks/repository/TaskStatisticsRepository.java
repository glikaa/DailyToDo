package com.example.tasks.repository;

import com.example.tasks.classes.TaskStatistics;
import com.example.tasks.classes.TaskStatisticsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskStatisticsRepository extends JpaRepository<TaskStatistics, TaskStatisticsId> {
    @Query(value = "SELECT * FROM GetTaskSummaryByCategory()", nativeQuery = true)
    List<Object[]> getTaskSummaryByCategory();

    @Query(value = "SELECT * FROM GetTotalTaskSummary()", nativeQuery = true)
    Object[] getTotalTaskSummary();
}
