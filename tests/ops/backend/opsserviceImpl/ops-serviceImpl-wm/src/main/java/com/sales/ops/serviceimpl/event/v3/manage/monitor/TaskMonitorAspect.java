package com.sales.ops.serviceimpl.event.v3.manage.monitor;

import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class TaskMonitorAspect {

    @Pointcut("@annotation(com.sales.ops.serviceimpl.event.v3.manage.monitor.TaskMonitor)")
    public void logPointCut() {
    }


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object args[] = point.getArgs();
        LocalDateTime start = LocalDateTime.now();
        if (args.length == 2 && args[0] instanceof OrderNoInfo) {
            OrderNoInfo orderNoInfo = (OrderNoInfo) args[0];
            XxlJobHelper.log("{}-{}:{}开始任务", Thread.currentThread().getName(), start, orderNoInfo.getFullNo());
        }

        // 处理目标方法
        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        // 打印现在时间和用时
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(start, end);
        if (args.length == 2 && args[0] instanceof OrderNoInfo) {
            OrderNoInfo orderNoInfo = (OrderNoInfo) args[0];
            XxlJobHelper.log("{}-{}:{}完成任务,用时{}s", Thread.currentThread().getName(), end, orderNoInfo.getFullNo(), between.toMillis() / 1000);
        }
        return result;
    }

}
