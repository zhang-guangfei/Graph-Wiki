package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.login.Role;
import com.smc.smccloud.model.login.RoleCondition;

import java.util.List;

public interface RoleService
{
    /**
     * 添加
     * @param roleList
     * @return
     */
    public void add(List<Role> roleList);

    /**
     * 详情
     * @param id
     * @return
     */
    public Role detail(String id);

    /**
     * 查询接口
     * @param condition
     * @param page
     * @return
     */
    public PageInfo<Role> query(RoleCondition condition, Page page);

    /**
     * 获取所有角色
     * @return
     */
    public List<Role> findAll();

    /**
     * 通过组ID查询组绑定的角色
     * @param groupId
     * @return
     */
    public List<Role> findByGroupId(String groupId);

    /**
     * 通过组名称查询组绑定的角色
     */
    public List<Role> findByGroupName(String groupName);

    /**
     * 通过用户ID获取该用户绑定的角色
     * @param userId
     * @return
     */
    public List<Role> findByUserId(String userId);

    /**
     * 通过id删除角色
     */
    void deleteByRoleId(String id);

    /**
     * 更新角色
     */
    int update(Role form);
}
