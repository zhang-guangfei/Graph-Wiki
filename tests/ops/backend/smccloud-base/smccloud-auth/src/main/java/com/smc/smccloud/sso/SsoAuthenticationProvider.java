package com.smc.smccloud.sso;


import com.smc.smccloud.service.SsoTokenVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SsoAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

	@SuppressWarnings("unused")
	private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	private UserDetailsService userDetailsService;

	@Autowired
	private SsoTokenVerifyService ssoTokenVerifyService;

    @SuppressWarnings("unused")
	private boolean hideUserNotFoundExceptions = true;
    
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String grant_type = getRequest().getParameter("grant_type");
        if(!SsoTokenGranter.GRANT_TYPE.equals(grant_type)){
            return null;
        }
        
        String username = (String) authentication.getPrincipal();
        
        if (StringUtils.isBlank(username)) {
            throw new BadCredentialsException("用户名错误");
        }
        String token = (String) authentication.getCredentials();
        if (StringUtils.isBlank(token)) {
        	log.error("缺失token参数");
        	throw new BadCredentialsException("token参数缺失");
            //throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Missing code"));
        }
        SsoAuthenticationToken authenticationToken = (SsoAuthenticationToken) authentication;
		Map<String, String> map = new HashMap<>(); 
		map.put("auth_code", authenticationToken.getUsernumber());
		map.put("token", token);
		map.put("sys", "sms");
		boolean bool = ssoTokenVerifyService.verify(map);
		log.info(" bool -> {}", bool);
		
		if(!bool)
			throw new InternalAuthenticationServiceException("统一门户系统token失效");
		
		UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
		
		if (user == null) {
			throw new InternalAuthenticationServiceException("获取用户信息失败");
		}

		SsoAuthenticationToken authenticationResult = new SsoAuthenticationToken(user, token, user.getAuthorities());
		authenticationResult.setDetails(authenticationToken.getDetails());

		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SsoAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public SsoTokenVerifyService getSsoTokenVerifyService() {
		return ssoTokenVerifyService;
	}

	public void setSsoTokenVerifyService(SsoTokenVerifyService ssoTokenVerifyService) {
		this.ssoTokenVerifyService = ssoTokenVerifyService;
	}
	
	public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
    }
 
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  ra.getRequest();
        return request;
    }
	
}
