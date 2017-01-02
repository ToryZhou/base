/**
 * ______
 * /__	__/__  ____
 * / / / _ \/ __) _ `/
 * /_/ ()___/_/  \_, /
 * /___/
 * 2016年12月3日 上午10:23:25
 */
package com.example.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Audience {

    @Before("execution (** com.example.service.UserService.getUser(..))")
    public void beforeAop() {
        System.out.println("before aop");
        System.out.println("before aop");
        System.out.println("before aop");
        System.out.println("before aop");
        System.out.println("before aop");
    }
}
