package com.capstone.jobscheduler.email;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class JobScheduleRequest {

    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Boolean triggerNow;
    @NonNull
    private Boolean isRepeating;
    @NonNull
    private Integer maxRetryAllowed;

    private String cronExpression;

    // dateTime & timeZone will be mandatory if triggerNow is set as false
    private LocalDateTime dateTime;
    private ZoneId timeZone;




//    private Integer seconds;
//    private Integer minutes;
//    private Integer hours;
//    private Integer days;
//    private Integer weeks;
//    private Integer months;

}
