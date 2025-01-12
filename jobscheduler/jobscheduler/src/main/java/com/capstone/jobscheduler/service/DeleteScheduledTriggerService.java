package com.capstone.jobscheduler.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.quartz.TriggerKey.triggerKey;

@Service
public class DeleteScheduledTriggerService {

    @Autowired
    private Scheduler scheduler;

    public ResponseEntity<String> deleteTriggerByTriggerIdAndGroup(String triggerId, String triggerGroup) {
        try {
            scheduler.unscheduleJob(triggerKey(triggerId, triggerGroup));
            return ResponseEntity.ok("Trigger "+triggerId+" has been removed");
        } catch (SchedulerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to remove the trigger "+triggerId);
        }
    }
}
