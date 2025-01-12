package com.capstone.jobscheduler.functions;

import com.capstone.jobscheduler.email.JobScheduleRequest;
import com.capstone.jobscheduler.email.JobSchedulerResponse;
import lombok.Data;
import org.quartz.CronExpression;

import java.time.ZonedDateTime;

@Data
public class RequestValidator {

    public static boolean isEmpty(Object field) {
        if (field == null) {
            return true; // Field is null
        }

        if (field instanceof String) {
            return ((String) field).trim().isEmpty(); // Empty or whitespace-only String
        }

        if (field instanceof Integer) {
            return ((Integer) field) == 0;
        }

        return false; // Non-empty field
    }

    public static JobSchedulerResponse ObjectValidator(JobScheduleRequest jobScheduleRequest){

        if(jobScheduleRequest.getTriggerNow().equals(false) &&
                (jobScheduleRequest.getDateTime() == null || isEmpty(jobScheduleRequest.getTimeZone()))){
            return new JobSchedulerResponse(
                    false,"Please provide the date time of trigger and the timezone if the job is not scheduled to be triggered right now.");
        }
        if(jobScheduleRequest.getIsRepeating().equals(true) &&
                (isEmpty(jobScheduleRequest.getCronExpression()))){
            return new JobSchedulerResponse(
                    false,"To schedule a repeating job, please provide a cron expression.");
        }

        if(!isEmpty(jobScheduleRequest.getCronExpression()) && !CronExpression.isValidExpression(jobScheduleRequest.getCronExpression())){
            return new JobSchedulerResponse(
                    false,"Please provide a valid cron expression");
        }

        if(!(jobScheduleRequest.getDateTime() == null)) {
            ZonedDateTime dateTime = ZonedDateTime.of(jobScheduleRequest.getDateTime(),jobScheduleRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now()))
                return new JobSchedulerResponse(false, "The scheduled time must be after the current time");
        }

        return new JobSchedulerResponse(
                true,"Request object validated");
    }

}
