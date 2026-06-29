package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.UUIDGenerator;
import com.smc.smccloud.mapper.RoleServiceMapper;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.login.Role;
import com.smc.smccloud.model.login.RoleCondition;
import com.smc.smccloud.service.userRole.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService
{
    @Resource
    private RoleServiceMapper roleServiceMapper;

    @Override
    public void add(List<Role> roleList) {
        Date nowDate = DateUtil.getNow();
        for(Role role:roleList) {
            role.setId(UUIDGenerator.getUUID());
            role.setCreateTime(nowDate);
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("NAME",role.getName());
            Role existRole = roleServiceMapper.selectOne(queryWrapper);
            if(existRole != null)
                continue;
            roleServiceMapper.insert(role);
        }
    }

    @Override
    public Role detail(String id) {
        return null;
    }

    @Override
    public PageInfo<Role> query(RoleCondition condition, Page page) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        queryWrapper.eq(PublicUtil.isNotEmpty(condition.getName()),"NAME",condition.getName());
        return PageHelper.startPage(page.getPageNumber(), page.getPageSize()).doSelectPageInfo(() ->
                roleServiceMapper.selectList(queryWrapper));
    }

    @Override
    public List<Role> findAll() {
        return roleServiceMapper.findAll();
    }

    @Override
    public List<Role> findByGroupId(String groupId) {
        return null;
    }

    @Override
    public List<Role> findByGroupName(String groupName) {
        return null;
    }

    @Override
    public List<Role> findByUserId(String userId) {
        List<Role> roles = roleServiceMapper.getByUserId(userId);
        if (PublicUtil.isEmpty(roles)) {
            return null;
        }
        return roles;
    }

    @Override
    public void deleteByRoleId(String id) { roleServiceMapper.deleteByRoleId(id); }

    @Override
    public int update(Role form) {
        int i = roleServiceMapper.updateById(form);
        return i;

        /**
         *  int i = 0;
         *         if (PublicUtil.isEmpty(form.getId())) {
         *             i = roleServiceMapper.insert(form);
         *         } else {
         *             i = roleServiceMapper.updateById(form);
         *         }
         *         return i;
         */
    }
}
