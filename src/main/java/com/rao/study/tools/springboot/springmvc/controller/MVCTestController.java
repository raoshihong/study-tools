package com.rao.study.tools.springboot.springmvc.controller;

import com.rao.study.tools.springboot.springmvc.annotation.ChannelAuthenticate;
import com.rao.study.tools.springboot.springmvc.annotation.GetChannelInfo;
import com.rao.study.tools.springboot.springmvc.model.ChannelInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MVCTestController {

    @GetMapping(value = "/testMvcArgument")
    @ChannelAuthenticate
    public void testMvcArgument(@GetChannelInfo ChannelInfo channelInfo){

        System.out.println("sdfsdf");

    }

}
