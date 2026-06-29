package com.smc.smccloud.service.resource;

import com.smc.smccloud.model.authority.DataRole;

import java.util.List;

public interface DataRoleService {
    /**
     * 根据角色ID删除配置的数据权限
     * @return
     */
    public void deleteByRoleId(String roleId);

    /**
     * 根据角色ID查询配置的数据权限
     */
    public List<DataRole> findByRoleId(String roleId);

    /**
     * 添加
     */
    public void add(String roleId, List<DataRole> list);
}
