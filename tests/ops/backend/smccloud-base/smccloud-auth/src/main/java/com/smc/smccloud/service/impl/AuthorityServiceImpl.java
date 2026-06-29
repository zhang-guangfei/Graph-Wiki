package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.UUIDGenerator;
import com.smc.smccloud.mapper.AuthorityMapper;
import com.smc.smccloud.model.authority.Authority;
import com.smc.smccloud.model.authority.AuthorityCondition;
import com.smc.smccloud.service.authority.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public Authority saveAuthority(Authority authority) {
        authority.setId(UUIDGenerator.getUUID());
        String randomNum = UUIDGenerator.getRandomNum();
        authority.setCode(randomNum);
        authorityMapper.insert(authority);
        return authority;
    }

    @Override
    public Authority updateAuthority(Authority authority) {
        int i = authorityMapper.updateById(authority);
        if (i != 1){
            return null;
        }
        return authority;
    }


    @Override
    public List<Authority> findAll(AuthorityCondition condition) {
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        queryWrapper.eq(PublicUtil.isNotEmpty(condition.getType()),"TYPE",condition.getType());
        return authorityMapper.selectList(queryWrapper);
    }

    @Override
    public Authority detail(String id) {
        return null;
    }

    @Override
    public void deleteByCode(String code) {
        authorityMapper.deleteByCode(code);
    }

    @Override
    public Set<String> findByUserId(String userId) {
        Set<String> set = new HashSet<String>();
        authorityMapper.getByUserId(userId).forEach(authority -> set.add(authority.getCode()));
        return set;

    }

    @Override
    public Set<String> findByGroupIds(List<String> groupIds) {
        Set<String> set = new HashSet<String>();
        // authorityServiceMapper.getByGroupIds(groupIds).forEach(authority -> set.add(authority.getCode()));
        List<Authority> byGroupIds = authorityMapper.getByGroupIds(groupIds);
        for (Authority item: byGroupIds)
        {
            set.add(item.getCode());
        }
        return set;
    }

    @Override
    public Set<Authority> findByRoleId(String roleId) {
        return authorityMapper.getByRoleId(roleId);
    }
}
