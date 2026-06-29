package com.smc.smccloud.core.config;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthExceptionEntryPoint  implements AuthenticationEntryPoint {

    /**
     * 401相应应该用来表示缺失或错误的认证，token失效，403表示用户认证后，但权限不足，无法对该资源进行操作
     *
     * token 失效
     */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Wrapper<String> resultVo = WrapMapper.error(request.getServletPath());
        ResultVo<String> resultVo = ResultVo.failure(request.getServletPath(), "401", "token失效！");
        JSONObject json = JSONObject.fromObject(resultVo);
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8")  ;
        response.getWriter().print(json.toString());
    }
}
