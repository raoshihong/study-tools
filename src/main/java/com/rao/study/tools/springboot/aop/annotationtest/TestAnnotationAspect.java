package com.rao.study.tools.springboot.aop.annotationtest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAnnotationAspect {

    @Before(value = "@annotation(testAnnoation)")
    public void testBefore(JoinPoint joinPoint,TestAnnoation testAnnoation){
        System.out.println("sdfsdf");
    }

}
