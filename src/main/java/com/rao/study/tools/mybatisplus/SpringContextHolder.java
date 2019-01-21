package com.rao.study.tools.mybatisplus;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext ctxt;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctxt = applicationContext;
    }

    public static  <T> T getBean(Class<T> tClass){
        return ctxt.getBean(tClass);
    }
}
