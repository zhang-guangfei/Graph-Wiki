package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.security.service.ReadAuthorityCacheService;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.AuthorityMapper;
import com.smc.smccloud.mapper.RoleAuthorityMapper;
import com.smc.smccloud.model.authority.RoleAuthority;
import com.smc.smccloud.service.userRole.RoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService
{
       @Resource
       private RoleAuthorityMapper roleAuthorityMapper;

       @Resource
       private AuthorityMapper authorityMapper;

       @Resource
       private ReadAuthorityCacheService readAuthorityCacheService;


    @Override
    public void add(String roleId, List<String> authorityIds) {
        if (PublicUtil.isEmpty(authorityIds)) {
            return;
        }
        deleteByRoleId(roleId);
        authorityIds.addAll(authorityMapper.getCommonAuthority());
        // 去重
        List<String> collect = authorityIds.stream().distinct().collect(Collectors.toList());
        for(String authorityId : collect) {
            RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setAuthorityId(authorityId);
            roleAuthority.setRoleId(roleId);
            roleAuthorityMapper.insert(roleAuthority);
        }
        readAuthorityCacheService.reloadRoleResourse();
    }

    @Override
    public List<RoleAuthority> findByRoleId(String roleId) {
        QueryWrapper<RoleAuthority> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("ROLE_ID",roleId);
        return roleAuthorityMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteByRoleId(String roleId) {
        roleAuthorityMapper.deleteByRoleId(roleId);
        readAuthorityCacheService.reloadRoleResourse();
    }
}
