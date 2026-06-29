package com.smc.smccloud.service.resource;

import com.smc.smccloud.model.authority.DataFilter;

import java.util.List;

public interface DataFilterService
{
    /**
     * 根据角色ID查询配置的过滤器类型
     */
    public DataFilter findByRoleId(String roleId);

    /**
     * 根据角色ID删除配置的数据权限
     * @return
     */
    public void deleteByRoleId(String roleId);

    /**
     * 绑定角色数据权限
     *
     * @param dataFilter 主要包含角色数据权限类型
     * @param objects 部门ID列表，行业代码列表，或者客户代码列表
     */
    public void bindDataFilter(DataFilter dataFilter, List<String> objects);

    /**
     * 获取用户绑定数据权限
     * @param userId
     * @return
     */
   // public DataAuthority findDataAuthorityByUserId(String userId);

    /**
     * 获取用户绑定数据权限
     * @param userId
     * @return
     */
    // public List<DataRole> findDataRoleByUserId(String userId);

    /**
     * 根据组名称获取DataFilter
     * @return
     */
    public List<DataFilter> findByGroupName(List<String> list);
}
