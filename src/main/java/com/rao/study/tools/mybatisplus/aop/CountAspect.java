package com.rao.study.tools.mybatisplus.aop;

import com.rao.study.tools.mybatisplus.utils.SpringContextHolder;
import com.rao.study.tools.mybatisplus.mapper.UserMapper;
import org.aspectj.lang.annotation.*;

//@Aspect
//@Order(10)
//@Component
public class CountAspect {

    @Before("@annotation(cost))")
    public void testBefore(Cost cost){
        System.out.println("CountAspect before");
    }

    @After("@annotation(cost))")
    public void testAfter(Cost cost){
        System.out.println("CountAspect after");
//        int i= 1/0;
    }

    @AfterReturning("@annotation(cost))")
    public void testAfterReturning(Cost cost){
        System.out.println("CountAspect AfterReturning");
        UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
        userMapper.updateUser();
//        int i = 10/0;
    }

}
