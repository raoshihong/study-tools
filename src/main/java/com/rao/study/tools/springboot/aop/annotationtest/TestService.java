package com.rao.study.tools.springboot.aop.annotationtest;

import org.springframework.stereotype.Component;

@Component
public class TestService {

    @TestAnnoation
    public void test(){
        System.out.println("sdfsdf");
    }

}
