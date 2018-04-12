package com.vidor.Baby.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BabyInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(BabyInterceptor.class);

    @Override
    //拦截之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //URI - /XXX ; URL - http://XXXXXX
        logger.info("interceptor : {}", request.getRequestURI() + " --" + request.getRequestURL());
        return true;
    }

    @Override
    //响应之后，返回view之前
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("interceptor before rendering view");
    }

    @Override
    //结束之后
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("interceptor ending");
    }
}
