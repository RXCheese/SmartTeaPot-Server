package com.springboot.smartteapot.hardware.openapi;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


@Aspect
public class GizwitsOpenApiAop {

    private Logger logger = LoggerFactory.getLogger(GizwitsOpenApiAop.class);

    @Pointcut("execution(public * com.springboot.smartteapot.hardware.openapi.GizwitsOpenApi.*(..))")
    public void pointcut() {

    }

    @Before(value = "pointcut()")
    public void onBefore(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("OpenApi执行了【" + name + "】方法，参数为【" + Arrays.toString(args) + "】");
    }
}
