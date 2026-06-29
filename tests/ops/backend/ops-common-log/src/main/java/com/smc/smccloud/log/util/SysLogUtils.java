package com.smc.smccloud.log.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.JwtEntity;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.log.model.AuthenticationVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;


/**
 * 系统日志工具类
 */
@Slf4j
public class SysLogUtils {


	/**
	 * 获取客户端
	 *
	 * @return clientId
	 */
	public static String getClientId() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication instanceof OAuth2Authentication) {
//			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
//            return auth2Authentication.getOAuth2Request().getClientId();
//		}
		return null;
	}

	/**
	 * 获取用户名称
	 * @return username
	 */
	public static String getUsername() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null) {
//			return null;
//		}
//		return authentication.getName();
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("未知");
        }
        return loginAuthDto.getUserNo();
    }

	/**
	 * 参数转为Json串
	 * @return
	 */
	public static String convertJson(Object obj[]) {
		if(obj == null)
			return null;
		JSONArray json = new JSONArray(obj);
		return json.toString();
	}

	public JwtEntity getLoginInfo(String token) {
	    if (StringUtils.isBlank(token)) {
	        return null;
        }
        Claims body = Jwts.parser().setSigningKey(Constants.JWT_TOKEN_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        String s = JSONObject.toJSONString(body);
        return JSONObject.parseObject(s, JwtEntity.class);
    }
}
