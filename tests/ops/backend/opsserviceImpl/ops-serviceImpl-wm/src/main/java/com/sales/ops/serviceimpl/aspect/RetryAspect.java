package com.sales.ops.serviceimpl.aspect;

import com.sales.ops.serviceimpl.annotation.Retryable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Slf4j
@Aspect
@Component
public class RetryAspect {
    
    @Around("@annotation(retryable)") // 指定切点为带有 @Retryable 注解的方法
    public Object doRetry(ProceedingJoinPoint joinPoint, Retryable retryable) throws Throwable {
        int maxRetries = retryable.maxRetries();
        long retryInterval = retryable.retryInterval();
        int retryCount = 0;
        Throwable lastException;

        do {
            try {
                return joinPoint.proceed(); // 执行目标方法
            } catch (Throwable ex) {
                lastException = ex;
                retryCount++;
                if (retryCount < maxRetries) {
                    log.error("重试次数：{}",retryCount);
                    log.error("异常原因：", ex);
                    Thread.sleep(retryInterval); // 等待一段时间后重试
                }
            }
        } while (retryCount < maxRetries);
        throw lastException; // 若达到最大重试次数仍未成功，则抛出最后一次异常
    }
}

