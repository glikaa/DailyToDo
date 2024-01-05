package com.example.tasks.repository;

import com.example.tasks.classes.DailyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DailyLimitRepository extends JpaRepository<DailyLimit, Date> {
    @Procedure(name = "SetDailyLimit")
    void setDailyLimit(@Param("Date") Date date, @Param("TaskLimit") int taskLimit);

    @Procedure(name = "GetDailyLimit")
    DailyLimit getDailyLimit(@Param("Date") Date date);
}
