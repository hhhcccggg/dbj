package com.zwdbj.server.mobileapi.controller;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class WebRequestResLog {
    private Logger logger = LoggerFactory.getLogger(WebRequestResLog.class);

    @Pointcut("execution(public * com.zwdbj.server.mobileapi.controller..*.*(..))")
    public void webLog(){

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            logger.info("REQUEST URL : " + request.getRequestURL().toString());
            logger.info("REQUEST HTTP_METHOD : " + request.getMethod());
            logger.info("REQUEST IP : " + request.getRemoteAddr());
            logger.info("REQUEST CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            Object[] args = joinPoint.getArgs();
            logger.info("REQUEST ARGS : " + JSONObject.toJSON(args).toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.error(ex.getStackTrace().toString());
        }
    }
    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        try {
            logger.info("RESPONSE : " + JSONObject.toJSON(ret).toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.error(ex.getStackTrace().toString());
        }
    }

}
