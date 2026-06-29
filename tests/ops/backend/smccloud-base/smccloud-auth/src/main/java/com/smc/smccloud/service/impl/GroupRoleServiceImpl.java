package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.GroupRoleMapper;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.group.GroupRole;
import com.smc.smccloud.model.group.GroupRoleCondition;
import com.smc.smccloud.service.group.GroupRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GroupRoleServiceImpl implements GroupRoleService {
    @Resource
    private GroupRoleMapper groupRoleMapper;


    @Override
    public void add(List<String> groupIds, List<String> groupNames, List<String> roleIds) {

        if(PublicUtil.isEmpty(roleIds)) {
            return;
        }

        for (int i = 0; i <groupIds.size() ; i++) {
            String groupId = groupIds.get(i);
            deleteByGroupId(groupId);
            String groupName = groupNames.get(i);
            for(String roleId : roleIds) {
                GroupRole groupRole = new GroupRole();
                groupRole.setGroupId(groupId);
                groupRole.setRoleId(roleId);
                groupRole.setDescription(groupName);
                groupRoleMapper.insert(groupRole);
            }
        }
    }

    @Override
    public List<GroupRole> findByGroupId(String groupId) {
        QueryWrapper<GroupRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("GROUP_ID", groupId);
        return groupRoleMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteByGroupId(String groupId) {
        groupRoleMapper.deleteByGroupId(groupId);
    }

    @Override
    public PageInfo<GroupRole> query(GroupRoleCondition condition, Page page) {
        return null;
    }
}
