package com.rao.study.tools.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(order = 2)  //开启事务
@MapperScan("com.rao.study.tools.mybatisplus.mapper")
//@ImportResource(locations = {"classpath:applicationContext-datasource-mybatis.xml"})
@Configuration
//@EnableAspectJAutoProxy
public class MybatisConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
