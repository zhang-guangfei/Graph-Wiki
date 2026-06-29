package com.sales.ops.api.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sales.ops.api.annotation.Log;
import com.sales.ops.db.dao.OpsCoordinateMapper;
import com.sales.ops.db.entity.OpsCoordinate;
import com.sales.ops.dto.util.CommonResult;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：打印日志存入数据库
 * @date ：Created in 2022/1/14 9:18
 */
@Aspect
@Component
public class LogAspect {

	@Autowired
	private OpsCoordinateMapper opsCoordinateMapper;

	@Pointcut("@annotation(com.sales.ops.api.annotation.Log)")
	public void logPointCut() {
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//todo 先不记录日志 异步保存日志
		saveLog(point, time, result);
		return result;
	}

	private void saveLog(ProceedingJoinPoint joinPoint, long time, Object result) throws InterruptedException {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Class r = signature.getReturnType();
		if (r.getName().equals("com.sales.ops.dto.util.CommonResult")) {
			CommonResult res = (CommonResult) result;
			// if(!res.isSuccess()){//保存不成功数据
			OpsCoordinate opsCoordinate = new OpsCoordinate();
			Log syslog = method.getAnnotation(Log.class);
			if (syslog != null) {
				// 注解上的描述 接口名称
				opsCoordinate.setApiname(syslog.apiName());
				opsCoordinate.setType(syslog.type());
			}
			opsCoordinate.setCostTime(time);// 花费时间
			opsCoordinate.setReturnMessage(res.getCode() + ":" + res.getMessage());
			opsCoordinate.setIssuccess(res.isSuccess());

			// 请求的方法名
			/*
			 * String className = joinPoint.getTarget().getClass().getName(); String
			 * methodName = signature.getName();
			 */

			// 请求的参数
			String params = Arrays.toString(joinPoint.getArgs());
			opsCoordinate.setMessage(params);
			// 获取request
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			// 设置IP地址
			opsCoordinate.setRemark(getIpAddress(request));
			// 系统当前时间
			Date date = new Date();
			opsCoordinate.setUsetime(date);
			if (!StringUtils.isEmpty(opsCoordinate.getMessage()) && opsCoordinate.getMessage().length() > 400) {
				opsCoordinate.setMessage(opsCoordinate.getMessage().substring(0, 400));// 报错信息不能过长
			} else {
				opsCoordinate.setMessage(opsCoordinate.getMessage());// 报错信息不能过长
			}
			if (!StringUtils.isEmpty(opsCoordinate.getReturnMessage())
					&& opsCoordinate.getReturnMessage().length() > 400) {
				opsCoordinate.setReturnMessage(opsCoordinate.getReturnMessage().substring(0, 400));// 报错信息不能过长
			} else {
				opsCoordinate.setReturnMessage(opsCoordinate.getReturnMessage());// 报错信息不能过长
			}
			opsCoordinate.setCreateTime(new Date());
			// 保存系统日志
			opsCoordinateMapper.insertSelective(opsCoordinate);
			// }

		}

	}

	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.hasText(ip)) {
			return ip.indexOf(",") > 0 ? ip.substring(0, ip.indexOf(",")) : ip;
		} else {
			ip = request.getHeader("X-Real-IP");
			return StringUtils.hasText(ip) ? ip : request.getRemoteAddr();
		}
	}

}
