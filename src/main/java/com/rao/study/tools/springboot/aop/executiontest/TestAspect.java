package com.rao.study.tools.springboot.aop.executiontest;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class TestAspect {

    @Before("execution(* com.rao.study.tools.springboot.aop.executiontest.TestService.*(..))")
    public void testBefore(){
        System.out.println("before");
    }

    @After("execution(* com.rao.study.tools.springboot.aop.executiontest.TestService.*(..))")
    public void testAfter(){
        System.out.println("after");
    }

}
