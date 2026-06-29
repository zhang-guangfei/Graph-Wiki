package com.smc.smccloud.controller;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.BaseExceptionCode;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
// @RequestMapping("")
public class LoginController{

	@Resource
	private TokenStore tokenStore;


	/**
	 * loginSuccess:异步ajax请求，登录成功后，转向的URL
	 * 多使用于登录方式为异步请求成功后，前端自行跳转至首页面(如：手机端原生app)
	 * @return
	 */
	@RequestMapping(value="/loginSuccess")
	public ResultVo<String> loginSuccess(){
		return ResultVo.success();
	}
	
	/**
	 * loginFailure:异步ajax请求，登录失败后，转向的URL
	 * 多使用于登录方式为异步请求失败后，前端进行提示具体错误(如：手机端原生app)
	 * @return
	 */
	@RequestMapping(value="/loginFailure")
	public ResultVo<String> loginFailure(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Exception exception = (Exception) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	    response.setStatus(500);
		return ResultVo.failure(BaseExceptionCode.登录错误, exception.getMessage());
		//return WrapMapper.error(exception.getMessage());
	}
	
	/**
	 * logoutSuccess:退出时清空，token缓存
	 * @return
	 */
	@RequestMapping(value="/logoutSuccess")
	public ResultVo<String> logoutSuccess(String token){
		OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
		if(oAuth2AccessToken != null) {
			tokenStore.removeAccessToken(oAuth2AccessToken);
			OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
			tokenStore.removeRefreshToken(oAuth2RefreshToken);
			tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
		}
		// return WrapMapper.ok("ok");
		return ResultVo.success();
	}




}
