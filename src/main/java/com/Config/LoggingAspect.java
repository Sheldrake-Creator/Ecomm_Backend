package com.Config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com..*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.debug("Entering method: {}.{}", className, methodName);
        }
    }

    @After("execution(* com..*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.debug("Exiting method: {}.{}", className, methodName);
        }
    }
}