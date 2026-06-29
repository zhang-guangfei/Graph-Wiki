package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.AuthorityResourceMapper;
import com.smc.smccloud.model.authority.AuthorityResource;
import com.smc.smccloud.service.authority.AuthorityResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityResourceServiceImpl implements AuthorityResourceService {

    @Resource
    private AuthorityResourceMapper authorityResourceMapper;

    @Override
    public List<AuthorityResource> findByAuthorityId(String authorityId) {
        QueryWrapper<AuthorityResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("AUTHORITY_ID",authorityId);
        return authorityResourceMapper.selectList(queryWrapper);
    }

    @Override
    public void add(String authorityId, List<String> resourceIds) {
        if(PublicUtil.isEmpty(resourceIds)) {
            return;
        }
        deleteByAuthorityId(authorityId);
        for(String resourceId : resourceIds) {
            AuthorityResource authorityResource = new AuthorityResource();
            authorityResource.setAuthorityId(authorityId);
            authorityResource.setResourceId(resourceId);
            authorityResourceMapper.insert(authorityResource);
        }
    }

    @Override
    public void deleteByAuthorityId(String authorityId) {
        authorityResourceMapper.deleteByAuthorityId(authorityId);
    }
}
