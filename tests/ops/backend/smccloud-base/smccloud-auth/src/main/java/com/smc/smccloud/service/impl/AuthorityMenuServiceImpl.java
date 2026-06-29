package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.mapper.Menu.AuthorityMenuMapper;
import com.smc.smccloud.model.authority.AuthorityMenu;
import com.smc.smccloud.service.authority.AuthorityMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthorityMenuServiceImpl implements AuthorityMenuService {

    @Resource
    private AuthorityMenuMapper authorityMenuMapper;

    @Override
    public List<AuthorityMenu> findByAuthorityId(String authorityId) {
        QueryWrapper<AuthorityMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("AUTHORITY_ID",authorityId);
        return authorityMenuMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteByAuthorityId(String authorityId) {

    }

    @Override
    public AuthorityMenu saveAuthMenu(AuthorityMenu authorityMenu) {
        authorityMenuMapper.insert(authorityMenu);
        return authorityMenu;
    }
}
