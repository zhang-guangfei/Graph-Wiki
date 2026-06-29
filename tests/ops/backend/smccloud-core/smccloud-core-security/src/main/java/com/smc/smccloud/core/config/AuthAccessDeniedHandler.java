package com.smc.smccloud.core.config;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 401相应应该用来表示缺失或错误的认证，token失效，403表示用户认证后，但权限不足，无法对该资源进行操作
     *
     * 暂无权限
     */

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Wrapper<String> resultVo = WrapMapper.error(request.getServletPath());
       log.info("[暂无权限]request = " + request.getServletPath());
        ResultVo<String> resultVo = ResultVo.failure(request.getServletPath(), "403", "暂无权限[403]");
        JSONObject json = JSONObject.fromObject(resultVo);
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8")  ;
        response.getWriter().print(json.toString());
    }
}
