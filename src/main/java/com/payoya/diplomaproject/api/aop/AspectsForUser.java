package com.payoya.diplomaproject.api.aop;

import com.payoya.diplomaproject.api.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectsForUser {

    //TODO : create an AOP classes for sending emails by email addresses after successful registration

//    @After(value = "execution(* com.payoya.diplomaproject.api.service.UserService.createNewUser(..))")
//    public void afterUserIsCreate(){
//        System.err.println("user is created");
//    }

    /*
    Proceeding user to aop advice for take an email address and catch this address when method in UserService is called
     even thou when bad credentials for creating new user
     */
    @Around(value = "execution(* com.payoya.diplomaproject.api.repository.IUserRepository.save(..)) && args(user)")
    public Object getEmail(ProceedingJoinPoint jp, User user) throws Throwable {
        String mailAddress = user.getEmailAddress();
        System.err.println("user email is: " + mailAddress);

        return jp.proceed();
    }

}
