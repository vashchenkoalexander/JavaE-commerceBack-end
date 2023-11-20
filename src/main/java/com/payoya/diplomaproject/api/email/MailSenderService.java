package com.payoya.diplomaproject.api.email;

import com.payoya.diplomaproject.api.entity.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Component
@Profile("prod")
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
        System.err.println("Time start : " + LocalDateTime.now());
        message.setFrom("noreplypayoyaEA@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.err.println("email sent" + LocalDateTime.now());
        System.err.println("Time end : " + LocalDateTime.now());
    }

    @Async
    public void sendActivationEmail(String toEmail, String token) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setFrom("noreplypayoyaEA@gmail.com", "1234");
            helper.setSubject("Account Activation");
            message.setText("Hello,\n\nPlease click on the following link to activate your account:\n"
                    + "https://localhost:8443/api/v1/user/activate?token=" + token);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(message);
    }

    @Async
    public void sendOrdertoEmail(String toEmail, Order order){
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Order Activity");
            message.setText("Hello, your order is" + order.getOrderItems());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(message);
    }

}
