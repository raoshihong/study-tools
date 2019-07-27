package com.rao.study.tools.springboot.springmvc.argument;


import com.rao.study.tools.springboot.springmvc.annotation.ChannelAuthenticate;
import com.rao.study.tools.springboot.springmvc.model.ChannelInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class ChannelAuthenticateInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截请求{}",request.getRequestURI());
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //在拦截器中只对方法带有ChannelAuthenticate注解的方法进行拦截处理
        if(method.isAnnotationPresent(ChannelAuthenticate.class)){
            ChannelAuthenticate channelAuthenticate = method.getAnnotation(ChannelAuthenticate.class);
            log.info("ChannelAuthenticate 拦截请求{}",request.getRequestURI());
            //获取登录用户


            ChannelInfo channelInfo = new ChannelInfo();
            request.setAttribute("channelInfo",channelInfo);
        }

        return true;
    }

}
