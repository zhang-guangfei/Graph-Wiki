package com.sales.ops.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MonitorExecutionTimeHandler {

    @Around("@annotation(MonitorExecutionTime)")
    public Object monitorExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 执行目标方法
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("方法 " + joinPoint.getSignature().getName() + " 执行时间: " + executionTime + " ms");
        return result;
    }
}