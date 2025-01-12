package com.capstone.jobscheduler.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class EmailResponse {
    private Boolean success;
    private String jobId;
    private String jobGroup;
    private String message;

    public EmailResponse(Boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    public EmailResponse(Boolean success, String jobId, String jobGroup, String message)
    {
        this.success = success;
        this.jobId = jobId;
        this.jobGroup = jobGroup;
        this.message = message;
    }
}
