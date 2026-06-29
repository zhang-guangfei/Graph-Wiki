package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.login.UserRole;
import com.smc.smccloud.model.login.UserRoleCondition;

import java.util.List;

public interface UserRoleService {
    /**
     * 添加
     * @param userId
     * @param psnname
     * @param roleIds
     */
    public void add(String userId, String psnname, List<String> roleIds);

    /**
     * 通过用户ID获取该用户绑定的角色
     * @param userId
     * @return
     */
    //public List<Role> findByUserId(String userId);

    /**
     * 通过用户ID删除用户绑定的角色
     * @param userId
     * @return
     */
    public void deleteByUserId(String userId);

    /**
     * 查询
     * @param condition
     * @param page
     * @return
     */
    public PageInfo<UserRole> query(UserRoleCondition condition, Page page);
}
