package com.capstone.jobscheduler.controller;

import com.capstone.jobscheduler.email.JobScheduleRequest;
import com.capstone.jobscheduler.email.JobSchedulerResponse;
import com.capstone.jobscheduler.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/jobscheduler")
public class JobSchedulerController {

    @Autowired
    JobSchedulerService jobSchedulerService;

    @Autowired
    TriggerJobNowService triggerJobNowService;

    @Autowired
    FetchJobDetailsService fetchJobDetailsService;

    @Autowired
    DeleteScheduledTriggerService deleteScheduledTriggerService;

    @Autowired
    DeleteJobService deleteJobService;

    @GetMapping("/testapi")
    public ResponseEntity<String> getApiTest(){
        return ResponseEntity.ok("Test passed");
    }

    @PostMapping("/schedule/job")                                   //This will schedule a job
    public ResponseEntity<JobSchedulerResponse> scheduleJob(@RequestBody JobScheduleRequest jobScheduleRequest){
        return jobSchedulerService.scheduleJobService(jobScheduleRequest);

    }

    @GetMapping("/job/trigger/{jobId}/{jobGroup}")                  //this will trigger the job now
    public ResponseEntity<String> triggerJob(@PathVariable String jobId, @PathVariable String jobGroup){
        return triggerJobNowService.triggerJobService(jobId,jobGroup,LocalDateTime.now());

    }

    @GetMapping("/job/trigger/{jobId}/{jobGroup}/{dateTime}")          //this will trigger the job at a specified time
    public ResponseEntity<String> rescheduleJob(@PathVariable String jobId, @PathVariable String jobGroup, @PathVariable LocalDateTime dateTime){
        return triggerJobNowService.triggerJobService(jobId,jobGroup,dateTime);

    }

    @GetMapping("/scheduledjobs/{jobId}/{jobGroup}")                //this will fetch details and scheduled triggers of the job
    public ResponseEntity<List<FetchJobDetailsService.TriggerDetails>> schuledJobDetails(@PathVariable String jobId, @PathVariable String jobGroup){
        return ResponseEntity.ok(fetchJobDetailsService.findJobTriggers(jobId,jobGroup));

    }

    @DeleteMapping("/triggers/{triggerId}/{triggerGroup}")          //This can be used to remove an upcoming trigger
    public ResponseEntity<String> deleteTrigger(@PathVariable String triggerId, @PathVariable  String triggerGroup){
        return deleteScheduledTriggerService.deleteTriggerByTriggerIdAndGroup(triggerId,triggerGroup);
    }

    @DeleteMapping("/jobs/{jobId}/{jobGroup}")                      //This can be used to remove a job
    public ResponseEntity<String> deleteJob(@PathVariable String jobId, @PathVariable  String jobGroup) {
        return deleteJobService.deleteJobByJobIdAndGroup(jobId,jobGroup);
    }

}
