package com.capstone.jobscheduler.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.quartz.JobKey.jobKey;

@Service
public class FetchJobDetailsService {

    @Autowired
    private Scheduler scheduler;

    public List<TriggerDetails> findJobTriggers(String jobId, String jobGroup) {
        try {
            List<? extends Trigger> jobTriggers = scheduler.getTriggersOfJob(jobKey(jobId, jobGroup));
            return jobTriggers.stream()
                    .map(trigger -> {
                        try {
                            return new TriggerDetails(
                                    trigger.getKey().toString(),
                                    trigger.getStartTime(),
                                    trigger.getNextFireTime(),
                                    trigger.getPreviousFireTime(),
                                    String.valueOf(scheduler.getTriggerState(trigger.getKey()))
                            );
                        } catch (SchedulerException e) {
                            throw new RuntimeException("Error retrieving triggers", e);
                        }
                    })
                    .toList();
            //return ResponseEntity.ok(jobTriggers);
        } catch (SchedulerException e) {
            throw new RuntimeException("Error retrieving triggers", e);
        }

    }
    public record TriggerDetails(String key, Date startTime, Date nextFireTime, Date previousFireTime, String triggerState) {}

}
