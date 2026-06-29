package com.smc.smccloud.service.group;

import com.github.pagehelper.PageInfo;

import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.group.GroupRole;
import com.smc.smccloud.model.group.GroupRoleCondition;

import java.util.List;

public interface GroupRoleService {
    /**
     * 添加
     * @param groupIds
     * @param groupNames
     * @param roleIds
     */
    public void add(List<String> groupIds, List<String> groupNames, List<String> roleIds);

    /**
     * 通过用户组ID获取该组绑定的角色
     * @param groupId
     * @return
     */
    public List<GroupRole> findByGroupId(String groupId);

    /**
     * 通过用户组ID删除该组绑定的角色
     * @param groupId
     * @return
     */
    void deleteByGroupId(String groupId);

    /**
     * 查询绑定明细
     * @param condition
     * @param page
     * @return
     */
    PageInfo<GroupRole> query(GroupRoleCondition condition, Page page);
}
