package com.smc.smccloud.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.HttpRequest;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.AuthorityMapper;
import com.smc.smccloud.mapper.RoleAuthorityMapper;
import com.smc.smccloud.mapper.UserRoleMapper;
import com.smc.smccloud.model.authority.Authority;
import com.smc.smccloud.model.authority.RoleAuthority;
import com.smc.smccloud.model.login.UserRole;
import com.smc.smccloud.service.AuthService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleAuthorityMapper roleAuthorityMapper;

    @Resource
    private AuthorityMapper authorityMapper;


    @Override
    public ResultVo<Boolean> hasPermission(String url) {
        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.toString());
        log.info(authentication.getPrincipal().toString());
        log.info(authentication.getCredentials().toString());
        log.info(authentication.getAuthorities().toString());


        
        //authentication.getPrincipal();//用户信息
       // authentication.getAuthorities();//角色
        return null;
    }

    @Override
    public Boolean hasFuncPermission(String authName,String loginUserNo) {

        if (StringUtils.isBlank(authName) || StringUtils.isBlank(loginUserNo)) {
            return false;
        }
        // 根据用户查询所属角色列表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(UserRole::getRoleId)
                .eq(UserRole::getUserId,loginUserNo);
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(userRoles)) {
            return false;
        }
        // 提取角色列表
        List<String> roleIdList = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        LambdaQueryWrapper<RoleAuthority> queryRoleAuth = new LambdaQueryWrapper<>();
        // 根据角色列表查询权限
        queryRoleAuth.in(RoleAuthority::getRoleId,roleIdList);
        List<RoleAuthority> roleAuthorities = roleAuthorityMapper.selectList(queryRoleAuth);
        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return false;
        }
        // 提取权限id
        List<String> authList = roleAuthorities.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());
        LambdaQueryWrapper<Authority> queryAuthName = new LambdaQueryWrapper<>();
        queryAuthName
                .select(Authority::getName)
                .in(Authority::getId, authList);
        List<Authority> authorities = authorityMapper.selectList(queryAuthName);
        // 提取权限名称
        List<String> authNameList = authorities.stream().map(Authority::getName).collect(Collectors.toList());
        if (authNameList.contains(authName)) {
            return true;
        }
        return false;
    }


}
