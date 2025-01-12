package com.capstone.jobscheduler.email;

import lombok.Data;

@Data
public class JobSchedulerResponse {
    private Boolean success;
    private String jobId;
    private String jobGroup;
    private String message;

    public JobSchedulerResponse(Boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    public JobSchedulerResponse(Boolean success, String jobId, String jobGroup, String message)
    {
        this.success = success;
        this.jobId = jobId;
        this.jobGroup = jobGroup;
        this.message = message;
    }
}
