package com.capstone.jobscheduler.functions;

import org.quartz.*;
import java.time.ZonedDateTime;
import java.util.Date;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class TriggerJob {
    public static Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startTime, String jobGroup){
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator();
        String unq_id = "TRGR"+String.valueOf(snowflakeIdGenerator.generateUniqueId());
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(unq_id,jobGroup+"-trigger")
                .withDescription("Triggering the job")
                .startAt(Date.from(startTime.toInstant()))
                .withSchedule(simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
    public static Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startTime, String jobGroup, String cronExpression){
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator();
        String unq_id = "TRGR"+String.valueOf(snowflakeIdGenerator.generateUniqueId());
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(unq_id,jobGroup+"-trigger")
                .withDescription("Triggering the job")
                .startAt(Date.from(startTime.toInstant()))
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }
    public static Trigger triggerExistingJob(String jobId, String jobGroup, ZonedDateTime startTime){
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator();
        String unq_id = "TRGR"+String.valueOf(snowflakeIdGenerator.generateUniqueId());
        return TriggerBuilder.newTrigger()
                .withIdentity(unq_id, jobGroup+"-trigger")
                .startAt(Date.from(startTime.toInstant()))
                .forJob(jobKey(jobId, jobGroup))
                .build();
    }
}
