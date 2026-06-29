package com.smc.smccloud.service;



import com.smc.smccloud.model.login.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService
{
    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    UserInfo findUserByUsername(String username, boolean isGetAuthorityCode);

    /**
     * validateUserUnLock:登陆前验证用户是否锁定
     * @author B82563
     * @param username
     */
    public void validateUserUnLock(String username);

    /**
     * resetFailureCount:登录成功重置登录次数
     * @author B82563
     * @param username
     */
    public void resetFailureCount(String username);

    /**
     * updateFailureCount:登陆失败更新失败次数
     * @author B82563
     * @param username
     * @return
     */
    public Integer[] updateFailureCount(String username);

    /**
     * 通过用户ID获取该用户绑定的角色
     * @param userId
     * @return
     */
    public Set<String> findByUserId(String userId);




}
