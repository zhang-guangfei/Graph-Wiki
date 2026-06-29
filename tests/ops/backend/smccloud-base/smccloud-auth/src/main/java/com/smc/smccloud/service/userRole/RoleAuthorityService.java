package com.smc.smccloud.service.userRole;

import com.smc.smccloud.model.authority.RoleAuthority;

import java.util.List;

public interface RoleAuthorityService
{
    /**
     * 添加
     * @param roleId
     * @param authorityIds
     */
    public void add(String roleId, List<String> authorityIds);

    /**
     * 通过角色ID获取该角色绑定的权限
     * @param roleId
     * @return
     */
    public List<RoleAuthority> findByRoleId(String roleId);

    /**
     * 通过角色ID删除角色绑定的资源
     * @param roleId
     * @return
     */
    public void deleteByRoleId(String roleId);

}
