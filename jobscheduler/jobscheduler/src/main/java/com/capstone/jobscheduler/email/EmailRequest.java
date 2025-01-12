package com.capstone.jobscheduler.email;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class EmailRequest {

    //@Email(message = "Please enter a valid email Id")
    @NonNull
    private String emailID;
    private String subject;
    @NonNull
    private String body;
    @NonNull
    private LocalDateTime dateTime;
    @NonNull
    private ZoneId timeZone;

}
