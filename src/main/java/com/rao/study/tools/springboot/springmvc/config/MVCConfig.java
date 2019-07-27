package com.rao.study.tools.springboot.springmvc.config;

import com.rao.study.tools.springboot.springmvc.argument.ChannelAuthenticateInterceptor;
import com.rao.study.tools.springboot.springmvc.argument.ChannelInfoArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class MVCConfig extends WebMvcConfigurationSupport {

    /**
     * 添加自定义的拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        //添加自定义的拦截器,对所有的请求都进行拦截
        registry.addInterceptor(new ChannelAuthenticateInterceptor()).addPathPatterns("/**");
    }

    /**
     * 添加自定义参数注解拦截器 (它的优先级调用在请求拦截器之后)
     * @param argumentResolvers
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new ChannelInfoArgumentResolver());
    }
}
