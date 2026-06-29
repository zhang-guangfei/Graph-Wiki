package com.smc.smccloud.service;

import com.smc.smccloud.model.authority.TreeInfo;

import java.util.List;

public interface OrganService
{
    /**
     * 获取所有组织机构
     * @return
     */
    public List<TreeInfo> findAllOrganizations();
}
