package com.rao.study.tools.mybatisplus.controller;

import com.rao.study.tools.mybatisplus.service.MyBatisPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyBatisPlusController {

    @Autowired
    private MyBatisPlusService myBatisPlusService;

    @GetMapping("/mybatisplus/testInsert")
    public void test(){
        myBatisPlusService.testInsert();
    }

    @GetMapping("/mybatisplus/testUpdate")
    public void testUpdate(){
        myBatisPlusService.testUpdate();
    }

    @GetMapping("/mybatisplus/testSelect")
    public void testSelect(){
        myBatisPlusService.testSelect();
    }

    @GetMapping("/mybatisplus/testQueryWrapAnd")
    public void testQueryWrapAnd(){
        myBatisPlusService.testQueryWrapAnd();
    }


    @GetMapping("/mybatisplus/testQeuryWrapOr")
    public void testQeuryWrapOr(){
        myBatisPlusService.testQeuryWrapOr();
    }

    @GetMapping("/mybatisplus/testUpdateWrap")
    public void testUpdateWrap(){
        myBatisPlusService.testUpdateWrap();
    }

    @GetMapping("/mybatisplus/testPage")
    public void testPage(){
        myBatisPlusService.testPage();
    }

    @GetMapping("/mybatisplus/testCustom")
    public void testCustom(){
        myBatisPlusService.testCustom();
    }

    @GetMapping("/mybatisplus/testTransaction")
    public void testTransaction(){
        myBatisPlusService.testTransaction();
    }

    @GetMapping("/mybatisplus/testTransactionAndAspect")
    public void testTransactionAndAspect(){
        myBatisPlusService.testTransactionAndAspect();
    }
}
