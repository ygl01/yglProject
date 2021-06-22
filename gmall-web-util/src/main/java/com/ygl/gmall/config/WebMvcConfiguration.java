package com.ygl.gmall.config;

import com.ygl.gmall.interceptors.AuthInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * TODO
 * 拦截器 将拦截器交由Spring容器进行管理
 *
 * @author ygl
 * @return null
 * @date 2021/6/22 10:48
 **/
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    //自定义拦截器
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}