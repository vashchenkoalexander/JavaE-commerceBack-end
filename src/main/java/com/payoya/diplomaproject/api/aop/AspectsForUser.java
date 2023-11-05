package com.payoya.diplomaproject.api.aop;

import com.payoya.diplomaproject.api.email.MailSenderService;
import com.payoya.diplomaproject.api.entity.Order;
import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.jms_activemq_artemis.ArtemisProducer;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Profile("prod")
public class AspectsForUser {

    private MailSenderService mailSender;

    private ArtemisProducer producer;

    public AspectsForUser(MailSenderService mailSender, ArtemisProducer producer) {
        this.mailSender = mailSender;
        this.producer = producer;
    }

    /**
//    @After(value = "execution(* com.payoya.diplomaproject.api.service.UserService.createNewUser(..))")
//    public void afterUserIsCreate(){
//        System.err.println("user is created");
//    }


    //Proceeding user to aop advice for take an email address and catch this address when method save
    //in IUserRepository is called for creating new user
     //
//    @Around(value = "execution(* com.payoya.diplomaproject.api.repository.IUserRepository.save(..)) && args(user)")
//    public Object getEmail(ProceedingJoinPoint jp, User user) throws Throwable {
//        String mailAddress = user.getEmailAddress();
//        System.err.println("user email is: " + mailAddress);
//
//        return jp.proceed();
//    }
    **/

    /*
    Catching in this advice a user email address after creation a user
     */
    @AfterReturning(pointcut = "execution(* com.payoya.diplomaproject.api.repository.IUserRepository.save(..))", returning = "user")
    public void getEmailAddress(User user){
        producer.sendMessage(user); // work with ActiveMQ Artemis
        //mailSender.sendEmail(user.getEmailAddress(), "Welcome to party",  "Dear user: " + user.getUsername() + ". WelcomeMaDearFriend");
        mailSender.sendActivationEmail(user.getEmailAddress(), user.getActivationToken());
        //mailSender.sendEmail(user.getEmailAddress(), "activation", "12345");
        System.err.println("user's username is: " + user.getUsername() + " " + LocalDateTime.now());
    }

    @AfterReturning(pointcut = "execution(* com.payoya.diplomaproject.api.repository.IOrderRepository.save(..))", returning = "order")
    public void setOrderedItems(Order order){
        //producer.sendOrderedItems(order);
        mailSender.sendOrdertoEmail(order.getUser().getEmailAddress(), order);
    }

}
