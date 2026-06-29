package com.sales.ops.webutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.until.OpsGetTokenUtil;
import com.sales.ops.dto.util.UserDto;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ErrorCodeEnum;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.JwtEntity;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.JwtUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author C02483
 * @version 1.0
 * @description: 基础信息获取
 * @date 2021/9/10 11:40
 */
@Slf4j
public abstract class BaseController {
    @Autowired
    private HttpServletRequest request;

    public UserDto getUserDto() {
        UserDto userDto = null;
        LoginUserDTO targetUser = null;
        String token = OpsGetTokenUtil.getTokenByRequest();
        if (StringUtils.isEmpty(token)) {
            return new UserDto("auto", OpsGetTokenUtil.getClientIpByRequest());
        }
        try {
            LoginUserDTO loginAuthDto = (LoginUserDTO) ThreadLocalMapUtil.get(GlobalConstant.Sys.LOGIN_USER_DTO);
            if (Objects.isNull(loginAuthDto)) {
                if (PublicUtil.isEmpty(token)) {
                    String token2 = request.getHeader("Authorization");
                    if (org.apache.commons.lang3.StringUtils.isBlank(token2)) {
                        throw new BusinessException(ErrorCodeEnum.GL_NO_LOGIN);
                    }
                    token = token2;
                }
                ThreadLocalMapUtil.put(GlobalConstant.TOKEN, token);
                // 解析jwt token
                String jwtToken = org.apache.commons.lang3.StringUtils.substringAfter(token, "Bearer ");
                if (org.apache.commons.lang3.StringUtils.isBlank(jwtToken)) {
                    jwtToken = token;
                }
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
                JwtEntity jwtEntity = JSON.parseObject(s, JwtEntity.class);
                if (jwtEntity != null) {
                    LoginUserDTO loginUserDTO = new LoginUserDTO();
                    loginUserDTO.setUserNo(jwtEntity.getUsername());
                    loginUserDTO.setUserName(jwtEntity.getUsername());
                    if (PublicUtil.isNotEmpty(jwtEntity.getPsnName())) {
                        loginUserDTO.setRealName(jwtEntity.getPsnName());
                    } else {
                        loginUserDTO.setRealName(jwtEntity.getUsername());
                    }
                    loginUserDTO.setToken(jwtToken);
                    if (PublicUtil.isNotEmpty(jwtEntity.getDeptNo())) {
                        loginUserDTO.setDeptNo(jwtEntity.getDeptNo());
                    }
                    // loginUserDTO.setRoles(jwtEntity.getAuthorities());
                    ThreadLocalMapUtil.put(GlobalConstant.Sys.LOGIN_USER_DTO, loginUserDTO);
                    userDto = new UserDto(jwtEntity.getUsername(), OpsGetTokenUtil.getClientIpByRequest());
                }
            } else {
                userDto = new UserDto(loginAuthDto.getUserName(), OpsGetTokenUtil.getClientIpByRequest());
            }
        } catch (Exception e) {
            log.info("token无效 --- " + token);
        }
        return userDto;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/9/10 15:22
     * @description：获取用户信息
     */
/*
   public UserDto getUser() {
        String authorization = request.getHeader("authorization");
        String token = authorization.replace("Bearer ", "");
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        String username = accessToken.getAdditionalInformation().get("username").toString();
        UserDto userDto = new UserDto(username, getIpAddress());
        return userDto;
    }*/


    /**
     * @author ：c02483
     * @date ：Created in 2021/9/10 15:22
     * @description：获取分页信息
     */
    public RowBounds getPageBounds() {
        if (this.request.getParameter("pageIndex") != null && this.request.getParameter("pageSize") != null) {
            Integer pageIndex = (Integer) NumberUtils.parseNumber(this.request.getParameter("pageIndex"), Integer.class);
            Integer pageSize = (Integer) NumberUtils.parseNumber(this.request.getParameter("pageSize"), Integer.class);
            RowBounds rowBounds = new RowBounds(pageIndex + 1, pageSize > 0 ? pageSize : 20);

            return rowBounds;
        } else {
            return new RowBounds(0, 20);
        }
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/9/10 15:23
     * @description：获取排序信息
     */
    public String getOrderByClause() {
        String sortField = this.request.getParameter("sortField");
        String sortOrder = this.request.getParameter("sortOrder");
        return StringUtils.hasText(sortField) ? sortField + " " + sortOrder : null;
    }

    protected String getSortField() {
        String sortField = this.request.getParameter("sortField");
        return sortField;
    }

    protected String getSortOrder() {
        String sortOrder = this.request.getParameter("sortOrder");
        return sortOrder;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/9/10 15:23
     * @description：获取请求IP地址
     */
    protected String getIpAddress() {
        String ip = this.request.getHeader("x-forwarded-for");
        if (StringUtils.hasText(ip)) {
            return ip.indexOf(",") > 0 ? ip.substring(0, ip.indexOf(",")) : ip;
        } else {
            ip = this.request.getHeader("X-Real-IP");
            return StringUtils.hasText(ip) ? ip : this.request.getRemoteAddr();
        }
    }

}
