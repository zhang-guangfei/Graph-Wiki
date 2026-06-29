package com.smc.smccloud.config.AuthConfig;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.userRole.LdapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;

@Slf4j
public class WebDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Resource
    private LdapService ldapService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        try {
            //userService.validateUserUnLock(authentication.getName());
            Authentication auth = super.authenticate(authentication);
            log.debug("authentication: 【{}】", auth.toString());
            //userService.resetFailureCount(authentication.getName());
            return auth;
        } catch (BadCredentialsException e) {
        	/*Integer params[] = userService.updateFailureCount(authentication.getName());
        	if(params[0] >= 4){
        		throw new BadCredentialsException(SpringContextUtil.getMessage(BaseExceptionCode.用户管理_密码错误次数,params));
        	}*/
            throw e;
        }
//        catch (BaseException e) {
//            throw new BadCredentialsException(e.getMessage());
//        }
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.error("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();  // 从凭证获取前端输入的密码
        String username = authentication.getPrincipal().toString();

        if (StringUtils.equals(userDetails.getPassword(), "NoPassWord")) {

            ResultVo<String> result = ldapService.validate(username, presentedPassword);

            if (result == null) {
                log.error(username+"[ladp门户]登录错误");
                throw new BadCredentialsException("登录错误");
            }

			if(!result.isSuccess()) {
				String errorCode = result.getCode();
				errorCode = StringUtils.isBlank(errorCode) ? "" : errorCode + ":";
				String errorMessage = errorCode + result.getMessage();
                log.error("登陆校验失败: " + username + " " + errorMessage);
				throw new BadCredentialsException(errorMessage);
			}

        } else {  // new BCryptPasswordEncoder().encode(presentedPassword)
            if (!getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
                log.error("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }
}
