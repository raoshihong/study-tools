package com.rao.study.tools.springboot.aop.annotationtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testAnnotationService;

    @GetMapping(value = "/annotation/testAnnoation")
    public void test(){
        testAnnotationService.test();
    }

}
