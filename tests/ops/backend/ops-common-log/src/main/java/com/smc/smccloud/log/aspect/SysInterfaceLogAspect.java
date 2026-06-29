package com.smc.smccloud.log.aspect;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.smc.smccloud.log.annotation.SysLog;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.log.model.Constants;
import com.smc.smccloud.log.model.OpsSysLogDO;
import com.smc.smccloud.log.rabbitMq.SendCommonLogMessage;
import com.smc.smccloud.log.util.SysLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author lyc
 * @Date 2023/6/1 15:49
 * @Descripton TODO
 */
@Aspect
@Slf4j
@Component
public class SysInterfaceLogAspect {

    public static final String SUCCESS = "成功";

    public static final String FAILURE = "失败";

    public static final String LOGIN = "登录";

    @Resource
	private SendCommonLogMessage sendMessage;

    @Pointcut("@annotation(com.smc.smccloud.log.annotation.SysLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.info("[类名]:{},[方法]:{}", strClassName, strMethodName);
        // 3. 获取方法上的注解
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        String apiLog = "";
        if (method != null)
        {
            apiLog=  method.getAnnotation(SysLog.class).value();
        }
        Long startTime = System.currentTimeMillis();
        Long endTime = null, excuteTime = null;
        Object obj = null, args[] = point.getArgs();
        OpsSysLogDO sysLogBean = new OpsSysLogDO();
        try {
            obj = point.proceed();
            sysLogBean = convertJson(args, apiLog,strClassName,strMethodName);
            endTime = System.currentTimeMillis();
            excuteTime = endTime - startTime;
        } catch (Throwable e) {
            sysLogBean = convertJson(args, apiLog,strClassName,strMethodName);
            endTime = System.currentTimeMillis();
            excuteTime = endTime - startTime;
            sysLogBean.setStatus(FAILURE);
            sysLogBean.setTime(excuteTime);
            sendLogToRabbitMq(sysLogBean);
            // SpringContextUtil.publishEvent(new SysLogEvent(sysLogBean));
            throw e;
        }
        sysLogBean.setStatus(SUCCESS);
        sysLogBean.setTime(excuteTime);
        sendLogToRabbitMq(sysLogBean);
        // SpringContextUtil.publishEvent(new SysLogEvent(sysLogBean));
        return obj;
    }

    private OpsSysLogDO convertJson(Object args[], String val,String className,String methodName){
        OpsSysLogDO sysLogBean = new OpsSysLogDO();
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String url = "";
        if (StringUtils.isBlank(request.getRequestURI())) {
            for (int i = 0; i<new Throwable().getStackTrace().length; i++) {
                log.info("类名 " + new Throwable().getStackTrace()[i].getClassName() + ", 方法名: " +new Throwable().getStackTrace()[i].getMethodName() );
            }
            for (int i = 0; i<new Throwable().getStackTrace().length; i++) {

                if(new Throwable().getStackTrace()[i].getMethodName().contains(methodName) && new Throwable().getStackTrace()[i].getClassName().contains(className)) {
                    url = new Throwable().getStackTrace()[i+1].getClassName()+"."+new Throwable().getStackTrace()[i+1].getMethodName();
                    break;
                }
            }

            sysLogBean.setRequesturi(url);
        } else {
            sysLogBean.setRequesturi(URLUtil.getPath(request.getRequestURI()));
        }

        DateTime dateTime = new DateTime();
        sysLogBean.setCreateid(SysLogUtils.getUsername());
        sysLogBean.setObj(args);
        if (args != null && args.length > 0) {
            sysLogBean.setParams(JSONArray.toJSONString(Arrays.asList(args)));
        }
        sysLogBean.setCreatetime(dateTime);
        sysLogBean.setRemoteaddr(ServletUtil.getClientIP(request));
        sysLogBean.setMethod(request.getMethod());
        sysLogBean.setUseragent(request.getHeader("user-agent"));
        sysLogBean.setServiceid(SysLogUtils.getClientId());
        sysLogBean.setTitle(val);
//        if(StringUtils.equals(LOGIN, val)) {
//            String loginName = ((Authentication)args[0]).getPrincipal().toString();
//            sysLogBean.setCreateid(loginName);
//            sysLogBean.setObj(new Object[] {loginName});
//        }
        return sysLogBean;
    }

    private void sendLogToRabbitMq(OpsSysLogDO sysLogBean) {
	    if (sysLogBean == null) {
	        return;
        }
		if(sysLogBean.getObj() != null) {
			sysLogBean.setParams(Arrays.toString(sysLogBean.getObj()));
		}
        log.info("推送数据到mq = " + JSON.toJSONString(sysLogBean));
		// 推送数据到mq
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setContent(JSON.toJSONString(sysLogBean));
        rabbitMqMessage.setFlag(Constants.OPS_SYSLOG_CREATE);
        rabbitMqMessage.setDataType(Constants.SYS_COMMON_LOG);
        rabbitMqMessage.setSystem(Constants.OPS);
        sendMessage.sendCommonLogMsg(rabbitMqMessage);
	}

}
