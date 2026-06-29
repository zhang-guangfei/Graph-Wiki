package com.smc.smccloud.service.impl;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.AdVerifyUtils;
import com.smc.smccloud.model.Constant;
import com.smc.smccloud.model.DevelopDomainServer;
import com.smc.smccloud.service.userRole.LdapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
public class LdapServiceImpl implements LdapService {
    @Resource
    private DevelopDomainServer developDomainServer;

    public LdapServiceImpl() {
    }

    /**
     * 525	用户不存在
     * 52e	密码或凭证无效
     * 530	此时不允许登录
     * 531	在此工作站上不允许登录
     * 532	密码过期
     * 533	账户禁用
     * 701	账户过期
     * 773	用户必须重置密码
     * 775	用户账户锁定
     *
     * @param username
     * @param password
     * @return
     * @throws AuthenticationException
     * @throws NamingException
     */

    public boolean login(String username, String password) throws AuthenticationException, NamingException {
        log.info("ladp 域 ==> {} = {}", developDomainServer.getDomainName(),
                developDomainServer.getAuthentication());

       /* try {
            Properties env = new Properties();
            env.put("java.naming.factory.initial", this.developDomainServer.getFactoryInitial());
            env.put("java.naming.security.authentication", this.developDomainServer.getAuthentication());
            env.put("java.naming.security.principal", username + "@" + this.developDomainServer.getDomainName()); // C18097@smcitTest.cn
            env.put("java.naming.security.credentials", password);
            env.put("java.naming.provider.url", this.developDomainServer.getUrl());
            new InitialLdapContext(env, (Control[])null);
            return true;
        } catch (NamingException var4) {
            throw var4;
        }*/

        // update by LiYingChao from bug 9783 in 2023-02-24
        String pwd = PublicUtil.decipheringPwd(password, Constant.decipheringPwdCount);
        password = pwd.substring(1);
        Hashtable<String, String> env = new Hashtable<String, String>();
        DirContext ctx = null;
        env.put("java.naming.factory.initial", this.developDomainServer.getFactoryInitial());
        env.put("java.naming.security.authentication", this.developDomainServer.getAuthentication());
        env.put("java.naming.security.principal", username + "@" + this.developDomainServer.getDomainName()); // C18097@smcitTest.cn
        env.put("java.naming.security.credentials", password);
        env.put("java.naming.provider.url", this.developDomainServer.getUrl());

        try {
            ctx = new InitialDirContext(env);
        } catch (NamingException e) {
            throw e;
        } finally {
            try {
                if (ctx != null) {
                    ctx.close();
                    ctx = null;
                }
                env.clear();
            } catch (Exception e) {
                log.error("Ldap context close出错:", e);
            }
        }
        return true;
    }

    public ResultVo<String> validate(String username, String password) {

        try {
            this.login(username, password);
            return ResultVo.success();
        } catch (NamingException var4) {
            log.error("[门户校验]登录连接门户ladp库: " + username + " " + AdVerifyUtils.errorCodeVerify(var4.getMessage()) + " " + var4.getMessage());
            return AdVerifyUtils.errorCodeVerify(var4.getMessage());
        }

        /*try {
            this.login(username, password);
            return WrapMapper.ok();
        } catch (AuthenticationException var4) {
            log.error(var4.getMessage());
            return WrapMapper.error(var4.getMessage());
        } catch (NamingException var5) {
            log.error(var5.getMessage());
            return WrapMapper.error(var5.getMessage());
        }*/
    }

    @Override
    public Boolean verify(Map<String, String> map) {
        return true;
    }
}
