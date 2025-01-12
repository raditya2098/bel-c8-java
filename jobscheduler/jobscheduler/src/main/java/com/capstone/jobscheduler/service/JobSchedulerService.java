package com.capstone.jobscheduler.service;

import com.capstone.jobscheduler.email.JobScheduleRequest;
import com.capstone.jobscheduler.email.JobSchedulerResponse;
import com.capstone.jobscheduler.functions.BuildJob;
import com.capstone.jobscheduler.functions.RequestValidator;
import com.capstone.jobscheduler.functions.TriggerJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.List;

import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerKey.triggerKey;

@Slf4j
@Service
public class JobSchedulerService {

    @Autowired
    private Scheduler scheduler;

    public ResponseEntity<JobSchedulerResponse> scheduleJobService(JobScheduleRequest jobScheduleRequest) {
        try{
            JobSchedulerResponse jobSchedulerResponse =  RequestValidator.ObjectValidator(jobScheduleRequest);
            if(jobSchedulerResponse.getSuccess().equals(false))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jobSchedulerResponse);

            if(jobScheduleRequest.getDateTime() == null){
                jobScheduleRequest.setDateTime(LocalDateTime.now());
            }
            if(RequestValidator.isEmpty(jobScheduleRequest.getTimeZone())){
                jobScheduleRequest.setTimeZone(ZoneId.of(ZoneId.systemDefault().toString()));
            }
            ZonedDateTime dateTime = ZonedDateTime.of(jobScheduleRequest.getDateTime(),jobScheduleRequest.getTimeZone());
            JobDetail jobDetail = BuildJob.buildJobDetail(jobScheduleRequest);
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            String jobGroup = jobDataMap.getString("name");
            if(RequestValidator.isEmpty(jobDataMap.getString("cronExpression"))){
                Trigger trigger = TriggerJob.buildTrigger(jobDetail, dateTime,jobGroup);
                scheduler.scheduleJob(jobDetail, trigger);
            }
            else {
                Trigger trigger = TriggerJob.buildTrigger(jobDetail, dateTime,jobGroup,jobDataMap.getString("cronExpression"));
                scheduler.scheduleJob(jobDetail, trigger);
            }


            JobSchedulerResponse jobSchedulerResponse1 = new JobSchedulerResponse(
                    true, jobDetail.getKey().getName(),
                    jobDetail.getKey().getGroup(),
                    "Task scheduled successfully");
            return ResponseEntity.ok(jobSchedulerResponse1);

        } catch (SchedulerException exception) {
            log.error("Error while scheduling the job: ",exception);
            JobSchedulerResponse jobSchedulerResponse = new JobSchedulerResponse(false,"Failed to schedule the job");
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jobSchedulerResponse);
        } catch (ClassNotFoundException e) {
            JobSchedulerResponse jobSchedulerResponse = new JobSchedulerResponse(false,"Class not found. Make sure that the class which has your job execution logic has the same name as given in the input as job name.");
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobSchedulerResponse);
        }

    }











}
