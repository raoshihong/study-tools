package com.rao.study.tools.springboot.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class TestAspect {

    @Before("execution(* com.rao.study.tools.springboot.aop.TestService.*(..))")
    public void testBefore(){
        System.out.println("before");
    }

    @After("execution(* com.rao.study.tools.springboot.aop.TestService.*(..))")
    public void testAfter(){
        System.out.println("after");
    }

}
