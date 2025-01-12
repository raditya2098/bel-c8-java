package com.capstone.jobscheduler.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.quartz.JobKey.jobKey;

@Service
public class DeleteJobService {

    @Autowired
    private Scheduler scheduler;

    public ResponseEntity<String> deleteJobByJobIdAndGroup(String jobId, String jobGroup) {
        try {
            scheduler.deleteJob(jobKey(jobId,jobGroup));
            return ResponseEntity.ok("Job "+jobId+" has been removed");
        } catch (SchedulerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to remove the job "+jobId);
        }
    }
}
