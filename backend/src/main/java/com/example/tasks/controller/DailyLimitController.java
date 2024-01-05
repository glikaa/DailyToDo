package com.example.tasks.controller;

import com.example.tasks.dto.DailyLimitDto;
import com.example.tasks.service.DailyLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/daily-limits")
public class DailyLimitController {

    @Autowired
    private DailyLimitService dailyLimitService;

    @PostMapping
    public ResponseEntity<DailyLimitDto> setDailyLimit(@RequestBody DailyLimitDto dailyLimitDto) {
        DailyLimitDto createdDailyLimit = dailyLimitService.setDailyLimit(dailyLimitDto.getTaskLimit());
        return ResponseEntity.ok(createdDailyLimit);
    }


    @GetMapping
    public ResponseEntity<DailyLimitDto> getTodayDailyLimit() {
        return dailyLimitService.getDailyLimit()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
