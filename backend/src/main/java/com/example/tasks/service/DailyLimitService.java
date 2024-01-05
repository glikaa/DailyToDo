package com.example.tasks.service;

import com.example.tasks.classes.DailyLimit;
import com.example.tasks.dto.DailyLimitDto;
import com.example.tasks.repository.DailyLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class DailyLimitService {

    @Autowired
    private DailyLimitRepository dailyLimitRepository;

    public DailyLimitDto setDailyLimit(int taskLimit) {
        DailyLimit dailyLimit = new DailyLimit();
        dailyLimit.setDate(new Date());
        dailyLimit.setTaskLimit(taskLimit);
        dailyLimit = dailyLimitRepository.save(dailyLimit);
        return new DailyLimitDto(dailyLimit.getTaskLimit());
    }

    public Optional<DailyLimitDto> getDailyLimit() {
        Date currentDate = getCurrentDateWithoutTime();
        return dailyLimitRepository.findById(currentDate)
                .map(limit -> new DailyLimitDto(limit.getTaskLimit()));
    }

    private Date getCurrentDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Set to current date
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


}
