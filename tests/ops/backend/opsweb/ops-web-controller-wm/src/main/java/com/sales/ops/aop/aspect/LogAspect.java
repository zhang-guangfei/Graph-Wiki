package com.sales.ops.aop.aspect;

import cn.hutool.json.JSONUtil;
import com.sales.ops.aop.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @description：打印日志
 */
@Slf4j
@Aspect
// @Component
public class LogAspect {

    @Before(value = "@annotation(log)")
    public void logBefore(JoinPoint joinPoint, Log log) {
        String info = log.value();
        this.log.info("开始执行：{}",info);
        this.log.info("参数：{}", JSONUtil.toJsonPrettyStr(joinPoint.getArgs()));
    }


    @After(value = "@annotation(log)")
    public void logAfter(JoinPoint joinPoint, Log log) {
        String info = log.value();
        this.log.info("执行完成；{}",info);
    }


}
