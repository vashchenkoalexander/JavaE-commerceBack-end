package com.payoya.diplomaproject.api.aop;

import com.payoya.diplomaproject.api.email.MailSenderService;
import com.payoya.diplomaproject.api.entity.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AspectsForUser {

    private MailSenderService mailSender;

    public AspectsForUser(MailSenderService mailSender) {
        this.mailSender = mailSender;
    }

//    @After(value = "execution(* com.payoya.diplomaproject.api.service.UserService.createNewUser(..))")
//    public void afterUserIsCreate(){
//        System.err.println("user is created");
//    }

    /*
    Proceeding user to aop advice for take an email address and catch this address when method save
    in IUserRepository is called for creating new user
     */
//    @Around(value = "execution(* com.payoya.diplomaproject.api.repository.IUserRepository.save(..)) && args(user)")
//    public Object getEmail(ProceedingJoinPoint jp, User user) throws Throwable {
//        String mailAddress = user.getEmailAddress();
//        System.err.println("user email is: " + mailAddress);
//
//        return jp.proceed();
//    }

    /*
    Catching in this advice a user email address after creation a user
     */
    @AfterReturning(pointcut = "execution(* com.payoya.diplomaproject.api.repository.IUserRepository.save(..))", returning = "user")
    public void getEmailAddress(User user){
        //mailSender.sendEmail(user.getEmailAddress(), "Welcome to party",  "Dear user: " + user.getUsername() + ". WelcomeMaDearFriend");
        System.err.println("user username is: " + user.getUsername() + LocalDateTime.now());
    }

}
