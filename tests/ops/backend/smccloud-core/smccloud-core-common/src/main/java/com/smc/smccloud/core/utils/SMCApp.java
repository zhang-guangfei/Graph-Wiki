package com.smc.smccloud.core.utils;

import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ErrorCodeEnum;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class SMCApp {

    public static LoginUserDTO getLoginAuthDto() {
        LoginUserDTO loginAuthDto = (LoginUserDTO) ThreadLocalMapUtil.get(GlobalConstant.Sys.LOGIN_USER_DTO);
        if (PublicUtil.isEmpty(loginAuthDto)) {
            throw new BusinessException(ErrorCodeEnum.GL_AUTH_OVER_TIME);
        }
        return loginAuthDto;
    }
    public static LoginUserDTO getLoginAuthDtoForSysUser() {
        try {

        LoginUserDTO loginAuthDto = (LoginUserDTO) ThreadLocalMapUtil.get(GlobalConstant.Sys.LOGIN_USER_DTO);
        if (PublicUtil.isEmpty(loginAuthDto)) {
            loginAuthDto.setDeptNo("");
            loginAuthDto.setUserNo("SYS");
            loginAuthDto.setRealName("SYS");
            loginAuthDto.setToken("SYS");
        }
            return loginAuthDto;
        } catch (Exception ex)
        {
            return new LoginUserDTO("SYS","SYS","SYS");
        }

    }

    public static void setGrantType(String grantType) {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.setAttribute(GlobalConstant.GRANT_TYPE, grantType);
        }
        ThreadLocalMapUtil.put(GlobalConstant.GRANT_TYPE, grantType);
    }

    public static String getGrantType() {
        Object obj = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            obj = request.getAttribute(GlobalConstant.GRANT_TYPE);
        }
        if (obj != null) {
            return obj.toString();
        }
        obj = ThreadLocalMapUtil.get(GlobalConstant.GRANT_TYPE);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static void setForSysUser() {
        LoginUserDTO dto = new LoginUserDTO();
        dto.setDeptNo("");
        dto.setUserNo("SYS");
        dto.setRealName("SYS");
        dto.setToken("SYS");
        ThreadLocalMapUtil.put(GlobalConstant.Sys.LOGIN_USER_DTO, dto);
    }
}
