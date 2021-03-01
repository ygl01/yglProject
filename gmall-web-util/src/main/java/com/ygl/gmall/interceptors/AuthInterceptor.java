package com.ygl.gmall.interceptors;

import com.ygl.gmall.annotations.LoginRequired;
import com.ygl.gmall.util.CookieUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截代码

        //判断被拦截的请求的访问的方法的注解（是否是需要拦截的）
        //通过反射获取到方法信息
        HandlerMethod hm = (HandlerMethod) handler;
        LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);

        //如果获取的方法信息为null，则放行，不进行拦截
        if (methodAnnotation == null){
            return true;
        }
        //获得该请求是否必须登录成功
        boolean loginSuccess = methodAnnotation.loginSuccess();

        System.out.println("进入拦截器的拦截方法！");

        return true;
    }
}