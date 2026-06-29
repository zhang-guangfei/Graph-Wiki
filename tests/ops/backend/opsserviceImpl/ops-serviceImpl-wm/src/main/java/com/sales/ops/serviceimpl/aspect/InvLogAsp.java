package com.sales.ops.serviceimpl.aspect;


import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.OpsSysLogMapper;
import com.sales.ops.db.entity.OpsSysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：打印日志存入数据库
 * @date ：Created in 2022/1/14 9:18
 */
@Aspect
@Component
public class InvLogAsp {

	@Autowired
	private OpsSysLogMapper logMapper;

	@Pointcut("@annotation(com.sales.ops.serviceimpl.annotation.InvLog)")
	public void logPointCut() {
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		// 执行方法
		Object result = point.proceed();
		saveLog(point);
		return result;
	}

	private void saveLog(ProceedingJoinPoint joinPoint) throws InterruptedException {
		OpsSysLog opsLog = new OpsSysLog();
		opsLog.setTitle("库存变动日志");
		//调用链路
		String strClassName = joinPoint.getTarget().getClass().getName();
		String strMethodName = joinPoint.getSignature().getName();
		StringBuilder buff = new StringBuilder();
		int count = 0;
		// 请求的参数
		String param = Arrays.toString(joinPoint.getArgs());

		buff.append(strClassName);
		buff.append(".");
		buff.append(strMethodName);
		buff.append("(");
		buff.append(param);
		buff.append(")");
		buff.append("-->");//
		Throwable throwable = new Throwable("initial");
		StackTraceElement[] traceElements = throwable.getStackTrace();
		for (StackTraceElement traceElement : traceElements) {
			if(traceElement.getLineNumber() != -1 && !Objects.equals(traceElement.getFileName(), "InvLogAsp.java")){
				if(traceElement.getClassName().contains("com.sales")||traceElement.getClassName().contains("com.smc")){
					//保留4层链路
					if(count > 3){
						break;
					}
					buff.append(Objects.requireNonNull(traceElement.getFileName()).split("\\.")[0]);
					buff.append(".");
					buff.append(traceElement.getMethodName());
					buff.append(".");
					buff.append(traceElement.getLineNumber());
					buff.append("-->");
					count ++;
				}
			}
		}
		opsLog.setParams(buff.toString());
		opsLog.setCreatetime(DateUtil.getNow());
		// 保存系统日志
		//logMapper.insertSelective(opsLog);
	}
}


