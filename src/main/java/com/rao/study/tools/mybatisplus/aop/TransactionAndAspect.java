package com.rao.study.tools.mybatisplus.aop;

import com.rao.study.tools.mybatisplus.SpringContextHolder;
import com.rao.study.tools.mybatisplus.mapper.UserMapper;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Order(10)
@Aspect
@Component
public class TransactionAndAspect{

    @Before("@annotation(cost))")
    public void testBefore(Cost cost){
        UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
        userMapper.updateUser();
        System.out.println("TransactionAndAspect before");
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        int i=10/0;
    }

    @After("@annotation(cost))")
    public void testAfter(Cost cost){
//        System.out.println("TransactionAndAspect AfterReturning");
//        UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
//        userMapper.updateUser();
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        int i=10/0;
    }

}
