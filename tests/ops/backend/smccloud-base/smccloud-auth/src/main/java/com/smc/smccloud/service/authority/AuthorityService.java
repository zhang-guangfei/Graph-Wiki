package com.smc.smccloud.service.authority;

import com.smc.smccloud.model.authority.Authority;
import com.smc.smccloud.model.authority.AuthorityCondition;

import java.util.List;
import java.util.Set;

public interface AuthorityService
{

    Authority saveAuthority(Authority authority);

    Authority updateAuthority(Authority authority);

    /**
     * 获取所有权限
     * @return
     */
    public List<Authority> findAll(AuthorityCondition condition);

    /**
     * 详情
     * @param id
     * @return
     */
    public Authority detail(String id);

    /**
     * 删除
     */
    public void deleteByCode(String code);

    /**
     * 通过用户Id获取按钮权限
     */
    public Set<String> findByUserId(String userId);

    /**
     * 根据组名称获取用户对应的权限
     * @return
     */
    //public Set<String> findByGroupNames(List<String> groupNames);

    /**
     * 根据组名称获取用户对应的权限
     * @return
     */
    Set<String> findByGroupIds(List<String> groupIds);

    /**
     * 根据角色Id获取用户对应的权限
     * @return
     */
    public Set<Authority> findByRoleId(String roleId);
}
