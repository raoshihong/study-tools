package com.rao.study.tools.mybatisplus.aop;

import com.rao.study.tools.mybatisplus.SpringContextHolder;
import com.rao.study.tools.mybatisplus.mapper.UserMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAndAspect {

    @Before("@annotation(cost))")
    public void testBefore(Cost cost){
        System.out.println("before");
    }

    @After("@annotation(cost))")
    public void testAfter(Cost cost){
        System.out.println("after");
//        int i= 1/0;
    }

    @Around("@annotation(cost))")
    public void testAround(ProceedingJoinPoint joinPoint,Cost cost)throws Throwable{
        System.out.println();
        joinPoint.proceed();
    }

    @AfterReturning("@annotation(cost))")
    public void testAfterReturning(Cost cost){
        System.out.println("AfterReturning");
        UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
        userMapper.updateUser();
        int i = 10/0;
    }
//    @AfterThrowing(value = "execution(* com.rao.study.tools.mybatisplus.service.MyBatisPlusService.testTransactionAndAspect(..))",throwing = "ex")
//    public void testAfterThrowing(Throwable ex){
//        System.out.println("sdf");
//    }

}
