package com.rao.study.tools.mybatisplus.aop;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
//@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRED)
public @interface Count {
}
