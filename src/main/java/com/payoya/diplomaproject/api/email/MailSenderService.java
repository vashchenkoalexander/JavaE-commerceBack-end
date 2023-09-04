package com.payoya.diplomaproject.api.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MailSenderService {

    private JavaMailSender mailSender;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /*
    Sending emails for users in Async. Created new thread for that purposes when this method is called
     */
    @Async
    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
//        System.err.println("Time start : " + LocalDateTime.now());
        message.setFrom("noreplypayoyaEA@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.err.println("email sent" + LocalDateTime.now());
//        System.err.println("Time end : " + LocalDateTime.now());
    }

}
