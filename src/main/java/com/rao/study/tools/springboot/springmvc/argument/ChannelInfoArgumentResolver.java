package com.rao.study.tools.springboot.springmvc.argument;

import com.rao.study.tools.jdk8function.BusinessException;
import com.rao.study.tools.springboot.springmvc.annotation.ChannelAuthenticate;
import com.rao.study.tools.springboot.springmvc.annotation.GetChannelInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class ChannelInfoArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(GetChannelInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        Method method = methodParameter.getMethod();
        if(!method.isAnnotationPresent(ChannelAuthenticate.class)){
            throw new BusinessException("400","获取渠道信息失败");
        }

        return httpServletRequest.getAttribute("channelInfo");
    }
}
