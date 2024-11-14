package com.course.project.varabei.aspect;

import com.course.project.varabei.annotation.ExecutionTimeLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
public class LogExecutionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogExecutionAspect.class);

    @Around("@annotation(com.course.project.varabei.annotation.ExecutionTimeLog)")
    public Object aroundLogExecutionTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ExecutionTimeLog originalAnnotation = method.getAnnotation(ExecutionTimeLog.class);
        String methodName = Optional.ofNullable(originalAnnotation)
                .map(ExecutionTimeLog::methodName)
                .filter(StringUtils::hasText)
                .orElse(method.getName());

        try {
            return joinPoint.proceed();
        } finally {
            LOGGER.info("Method execution time {}: {} ms", methodName, System.currentTimeMillis() - startTime);
        }
    }
}
