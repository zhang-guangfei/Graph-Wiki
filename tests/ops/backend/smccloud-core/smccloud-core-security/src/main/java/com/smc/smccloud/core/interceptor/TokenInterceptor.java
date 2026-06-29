package com.smc.smccloud.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.core.annotation.NoNeedAccessAuthentication;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ErrorCodeEnum;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.JwtEntity;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;


@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private StringEncryptor encryptor;
    private static final String OPTIONS = "OPTIONS";
    private static final String NO_NEED_AUTH_PATH1 = "/error";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex)
            throws Exception {
        ThreadLocalMapUtil.clear();
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();

        if (uri.contains(NO_NEED_AUTH_PATH1)) {
            log.info("<== preHandle - 配置URL不走认证.  url={}", uri);
            return true;
        }

        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            return true;
        }

      /*  if (isHaveAccess(handler)) {
            // log.info("<== preHandle - 不需要认证注解不走认证. token={}");
            return true;
        }*/

        // 若客户端则放行
        String grantType = request.getHeader(GlobalConstant.GRANT_TYPE);
        if (GlobalConstant.GrantType.CLIANT_CREDENTIALS.equals(grantType)) {
            String clientId = request.getHeader(GlobalConstant.CLIENT_ID);
            String clientSecret = request.getHeader(GlobalConstant.CLIENT_SECRET);
            if (PublicUtil.isNotEmpty(clientId) && PublicUtil.isNotEmpty(clientSecret)) {
                String serviceName = request.getHeader(GlobalConstant.SERVICE_NAME);
                // log.info("<== preHandle - 客户端授权模式 service_name:{}, client_Id:{}, uri:{}", serviceName, clientId, uri);
                LoginUserDTO loginUser = new LoginUserDTO(serviceName, serviceName, serviceName);
                ThreadLocalMapUtil.put(GlobalConstant.Sys.LOGIN_USER_DTO, loginUser);
                return true;
            }
        }

        String token = request.getHeader("authorization");
        if (PublicUtil.isEmpty(token)) {
            String token2 = request.getHeader("Authorization");
            if (StringUtils.isBlank(token2)) {
                throw new BusinessException(ErrorCodeEnum.GL_NO_LOGIN);
            }
            token = token2;
        }
        ThreadLocalMapUtil.put(GlobalConstant.TOKEN, token);

         // 解析jwt token
        String jwtToken = StringUtils.substringAfter(token, "Bearer ");

        // jwt 是否正确 || 是否有效/过期
        try {
            if (!JwtUtil.verifyToken(jwtToken)) {
                throw new BusinessException("请检查Token格式/过期时间");
            }
        } catch (Exception e) {
            throw new BusinessException("请检查Token格式/过期时间");
        }

        Claims body = Jwts.parser().setSigningKey(Constants.JWT_TOKEN_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(jwtToken).getBody();
        String s = JSONObject.toJSONString(body);
        JwtEntity jwtEntity = JSONObject.parseObject(s, JwtEntity.class);
        if (jwtEntity != null) {
            LoginUserDTO loginUserDTO = new LoginUserDTO();
            String username =  jwtEntity.getUsername();
            // C14717 bugid：14930  2024/8/14
            String ssoValue = request.getHeader("sso");
            if(StringUtils.isNotBlank(ssoValue) && !"undefined".equals(ssoValue)){
                username = encryptor.decrypt(ssoValue);
            }
            loginUserDTO.setUserNo(username);
            loginUserDTO.setUserName(username);
            if (PublicUtil.isNotEmpty(jwtEntity.getPsnName())) {
                loginUserDTO.setRealName(jwtEntity.getPsnName());
            } else {
                loginUserDTO.setRealName(username);
            }
            loginUserDTO.setToken(jwtToken);
            if (PublicUtil.isNotEmpty(jwtEntity.getDeptNo())) {
                loginUserDTO.setDeptNo(jwtEntity.getDeptNo());
            }
            // loginUserDTO.setRoles(jwtEntity.getAuthorities());
            ThreadLocalMapUtil.put(GlobalConstant.Sys.LOGIN_USER_DTO, loginUserDTO);
        }

        // 解决跨域后系统为了安全去掉自定义头的问题
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        return true;
    }

    /**
     * 判断方法的注解是否不需要走验证权限
     */
    private boolean isHaveAccess(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();

        NoNeedAccessAuthentication responseBody = AnnotationUtils.findAnnotation(method,
                NoNeedAccessAuthentication.class);
        return responseBody != null;
    }

}