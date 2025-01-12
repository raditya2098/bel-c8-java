package com.capstone.jobscheduler.functions;

import com.capstone.jobscheduler.email.JobScheduleRequest;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BuildJob {
    public static JobDetail buildJobDetail(JobScheduleRequest jobScheduleRequest) throws ClassNotFoundException {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", jobScheduleRequest.getName());
        jobDataMap.put("description", jobScheduleRequest.getDescription());
        jobDataMap.put("triggerNow", jobScheduleRequest.getTriggerNow());
        jobDataMap.put("isRepeating", jobScheduleRequest.getIsRepeating());
        jobDataMap.put("maxRetryAllowed", jobScheduleRequest.getMaxRetryAllowed());
        jobDataMap.put("dateTime", jobScheduleRequest.getDateTime());
        jobDataMap.put("timeZone", jobScheduleRequest.getTimeZone());
        jobDataMap.put("cronExpression", jobScheduleRequest.getCronExpression());
        jobDataMap.put("retryCount", 0);


        String unq_id = "JOB"+String.valueOf(snowflakeIdGenerator.generateUniqueId());
        String className = "com.capstone.jobscheduler.SampleJobs."+jobScheduleRequest.getName();
        Class <? extends QuartzJobBean> jobClass = (Class<? extends QuartzJobBean>) Class.forName(className);
        return JobBuilder.newJob(jobClass)
                .withIdentity(unq_id,jobScheduleRequest.getName()+"-schedule")
                .withDescription(jobScheduleRequest.getDescription())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();


        /*return JobBuilder.newJob(TimeDelayJob.class)//here the name should be changed to the jobScheduleRequest.getName()
                .withIdentity(unq_id,jobScheduleRequest.getName()+"-schedule")
                .withDescription(jobScheduleRequest.getDescription())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();*/
    }

}
