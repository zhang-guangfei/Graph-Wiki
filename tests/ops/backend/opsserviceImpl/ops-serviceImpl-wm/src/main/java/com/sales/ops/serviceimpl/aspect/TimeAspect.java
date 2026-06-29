package com.sales.ops.serviceimpl.aspect;

import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.serviceimpl.annotation.Time;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 打印方法执行时间，和参数
 * handleOrderStatusUpdated: orderNo: 11703048, orderItem: 4,耗时：4s
 */
@Slf4j
@Aspect
@Component
public class TimeAspect {
    @Autowired
    private ParameterNameDiscoverer parameterNameDiscoverer;

    @Around("@annotation(time)")
    public Object doTime(ProceedingJoinPoint joinPoint, Time time) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(((MethodSignature) joinPoint.getSignature()).getMethod());
        Date start = DateUtil.getNow();
        Object proceed = joinPoint.proceed();// 执行目标方法
        Date end = DateUtil.getNow();
        if (parameterNames != null && time.value().length > 0) {
            log.info("{}: {},耗时：{}ms", methodName, getString(joinPoint, time, parameterNames), DateUtil.diffMs(end, start));
        } else {
            log.info("{},耗时：{}ms", methodName, DateUtil.diffMs(end, start));
        }
        return proceed;
    }

    private String getString(ProceedingJoinPoint joinPoint, Time logParams, String[] parameterNames) {
        StringBuilder argsLog = new StringBuilder();
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("args", joinPoint.getArgs());
        for (String paramName : logParams.value()) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (parameterNames[i].equals(paramName)) {
                    Object argValue = parser.parseExpression("#args[" + i + "]").getValue(context);
                    argsLog.append(paramName).append(": ").append(argValue).append(", ");
                    break;
                }
            }
        }
        return argsLog.toString().isEmpty() ? "" : argsLog.substring(0, argsLog.length() - 2);
    }
}

