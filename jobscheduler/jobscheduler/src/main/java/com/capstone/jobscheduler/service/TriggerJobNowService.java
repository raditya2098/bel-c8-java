package com.capstone.jobscheduler.service;

import com.capstone.jobscheduler.functions.TriggerJob;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TriggerJobNowService {

    @Autowired
    private Scheduler scheduler;

    public ResponseEntity<String> triggerJobService(String jobId, String jobGroup, LocalDateTime dateTime) {

        try {
            ZonedDateTime zonedDateTimeDateTime = ZonedDateTime.of(dateTime, ZoneId.of(ZoneId.systemDefault().toString()));
            Trigger trigger = TriggerJob.triggerExistingJob(jobId,jobGroup, zonedDateTimeDateTime);
            scheduler.scheduleJob(trigger);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("The trigger for this job has been added");

    }
}
