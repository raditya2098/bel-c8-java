package com.capstone.jobscheduler.SampleJobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TimeDelayJob extends QuartzJobBean {
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        String name = jobDataMap.getString("name");
        int maxRetryCount = jobDataMap.getIntValue("maxRetryAllowed");
        int retryCount = jobDataMap.getIntValue("retryCount");  // This will get the current retry attempt for the job

        try {                                                        //Inside this try block would be the actual implementation logic of the job
            System.out.println("Executing the job: " + name);
            System.out.println("This is a sample job. We'll wait for 45 seconds before finishing the execution of job");
            TimeUnit.SECONDS.sleep(45);
            System.out.println("The job has finished the execution");
        }
        catch (Exception e) {                                        //The code can be configured without try catch block if automatic retry is not required
            if(retryCount<maxRetryCount){
                System.out.println("The job failed to execute because of the following reason: \n\t"+
                        e.getMessage()+"\nThis is the "+retryCount+1+" out of "+maxRetryCount+" attempts to retry");
                jobDataMap.put("retryCount",retryCount+1);
                JobExecutionException jobExecutionException = new JobExecutionException(e);
                jobExecutionException.setRefireImmediately(true);    // Retry the job
                throw jobExecutionException;
            }
            else{
                System.out.println("The retry limit for the job has been reached");
            }
        }

    }
}
