package com.rao.study.tools.springboot.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public void test(){
        testService.test();
    }

}
