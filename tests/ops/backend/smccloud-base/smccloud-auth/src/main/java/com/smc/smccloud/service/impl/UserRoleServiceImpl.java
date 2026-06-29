package com.smc.smccloud.service.impl;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.UserRoleMapper;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.login.UserRole;
import com.smc.smccloud.model.login.UserRoleCondition;
import com.smc.smccloud.service.userRole.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void add(String userId, String psnname, List<String> roleIds) {
        if( PublicUtil.isEmpty(roleIds) ) {
            deleteByUserId(userId);
            return;
        }
        deleteByUserId(userId);
        for(String roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setDescription(userId + "," + psnname);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void deleteByUserId(String userId) {
        userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public PageInfo<UserRole> query(UserRoleCondition condition, Page page) {
        return null;
    }
}
