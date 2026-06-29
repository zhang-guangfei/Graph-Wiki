/*
package com.smc.smccloud.config.Interceptor;

import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ErrorCodeEnum;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.adapter.LoginUserDTO;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.ThreadLocalMap;
import com.smc.smccloud.service.CheckTokenServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private CheckTokenServiceApi checkTokenServiceApi;

    private static final String OPTIONS = "OPTIONS";
    private static final String NO_NEED_AUTH_PATH1 = "/error";

    @Resource
    private RedisManager redisManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        */
/**
         * 如果处理对象不是一个处理方法直接放过拦截的请求
         *//*

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String uri = request.getRequestURI();
//        log.info("权限拦截器 uri = " + uri);
        if (uri.contains(NO_NEED_AUTH_PATH1)) {
//            log.info(" 权限拦截器 - /error 不走认证 uri = " + uri);
            return true;
        }
        // 验证是否客户授权模式
        String grantType = request.getHeader(GlobalConstant.GRANT_TYPE);
        if (GlobalConstant.GrantType.CLIANT_CREDENTIALS.equals(grantType)) {
            String clientId = request.getHeader(GlobalConstant.CLIENT_ID);
            String clientSecret = request.getHeader(GlobalConstant.CLIENT_SECRET);
            // 暂只验证是否为空
            if (PublicUtil.isNotEmpty(clientId) && PublicUtil.isNotEmpty(clientSecret)) {
                log.info("<== preHandle - 客户端授权模式 client_Id:{}, uri:{}", clientId, uri);
                LoginUserDTO loginUser = new LoginUserDTO(clientId, clientId, clientId);
                ThreadLocalMap.put(GlobalConstant.Sys.LOGIN_USER_DTO, loginUser);
                return true;
            }
        }
        */
/**
         * 从请求头获取token
         *//*

        String token = request.getHeader(GlobalConstant.TOKEN);
        if (StringUtils.isEmpty(token)) {
            log.info("token is empty. uri: " + uri);
            log.error("token is empty. uri: {}", uri);
            throw new BusinessException(ErrorCodeEnum.GL_NO_LOGIN);
        }
        // log.info("<== preHandle - 权限拦截器. token={}", token);

        if (StringUtils.startsWith(token, "bearer ")) {
            token = StringUtils.substringAfter(request.getHeader(GlobalConstant.TOKEN), "bearer ");
        }
        log.info("token = " + token);
        */
/*String authorization = request.getHeader("authorization");

        String token = StringUtils.substringAfter(authorization, "Bearer ");
        log.info("token = " + token);

        if (PublicUtil.isEmpty(token)) {
            throw new BusinessException(ErrorCodeEnum.GL_NO_LOGIN);
        }*//*


        return true;
//        Map<String, Object> result = new HashMap<String, Object>();
//        try {
//            result = checkTokenServiceApi.checkToken(token);
//            log.info("result = " + result);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new RuntimeException(WebSocketExceptionCode.UN_AUTH);
//        }
//
//        if ((boolean) result.get("active")) {
//            return true;
//        }

//        throw new RuntimeException(WebSocketExceptionCode.UN_AUTH);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex)
            throws Exception {
        ThreadLocalMap.remove();
        if (ex != null) {
            log.error("<== afterCompletion - 解析token失败. ex={}", ex.getMessage(), ex);
            this.handleException(response);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv) {
    }

    private void handleException(HttpServletResponse res) throws IOException {
        res.resetBuffer();
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write("{\"status\":100009 ,\"message\" :\"解析token失败\"}");
        res.flushBuffer();
    }

}*/
