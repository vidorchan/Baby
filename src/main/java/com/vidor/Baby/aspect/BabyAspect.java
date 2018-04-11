package com.vidor.Baby.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class BabyAspect {

    private final static Logger logger = LoggerFactory.getLogger(BabyAspect.class);

    //execution(<修饰符模式>? <返回类型模式> <方法名模式>(<参数模式>) <异常模式>?)
    //execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
    @Pointcut("execution(public * com.vidor.Baby.controller.BabyApiController.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        logger.info("URL={}", httpServletRequest.getRequestURL());
        logger.info("IP={}", httpServletRequest.getRemoteAddr());
        logger.info("URI={}", httpServletRequest.getRequestURI());
        logger.info("Method={}", httpServletRequest.getMethod());
        logger.info("Class={}", joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getDeclaringTypeName());
    }

    @After("log()")
    public void doAfter() {
        logger.info("after=111111111");
    }

    @Pointcut("execution(public * com.vidor.Baby.service.BabyService.insertTwo(..))")
    public void insert() {
    }

    @Around("insert()")
    public void insertTwoPoint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        logger.info("around=insertTwo before");
        proceedingJoinPoint.proceed();
        logger.info("around=insertTwo after");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturn(Object object) {
//        logger.info("return object = {}", object.toString());
    }
}
