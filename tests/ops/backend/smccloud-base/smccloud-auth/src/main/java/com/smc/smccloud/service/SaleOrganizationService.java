package com.smc.smccloud.service;

import com.smc.smccloud.model.authority.SaleOrganization;

import java.util.List;

public interface SaleOrganizationService
{
    /**
     * 获取全部组织机构
     *
     * @author B82561
     * @return
     */
    public List<SaleOrganization> findAllOrganizations();

}
