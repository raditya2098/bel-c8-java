package com.capstone.jobscheduler.SampleJobs;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EmailJob extends QuartzJobBean {

    @Autowired
    private  JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        String recipientEmail = jobDataMap.getString("com/capstone/jobscheduler/email");
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");

        sendEmail(mailProperties.getUsername(),recipientEmail,subject,body);

    }

    private void sendEmail(String fromEmail, String recipientEmail, String subject, String body) {

        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body,true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(recipientEmail);
            mailSender.send(message);

        } catch (MessagingException ex) {
            System.out.println("Tried to trigger the job but failed"+ex);
        }

    }
}
