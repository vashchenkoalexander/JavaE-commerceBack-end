package com.payoya.diplomaproject.api.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MailSenderConfig {

    @AfterReturning("execution(* com.payoya.diplomaproject.api.service.UserService.createNewUser())")
    public void afterUserIsCreate(){
        System.err.println("user is created");
    }

    //TODO : create an AOP classes for sending emails by email addresses after successful registration

}
