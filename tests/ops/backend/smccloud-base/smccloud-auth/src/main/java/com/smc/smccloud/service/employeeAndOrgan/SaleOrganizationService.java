package com.smc.smccloud.service.employeeAndOrgan;

import com.smc.smccloud.model.authority.SaleOrgPositionBean;
import com.smc.smccloud.model.authority.SaleOrganization;

import java.util.List;

public interface SaleOrganizationService
{
    /**
     * 获取全部组织机构
     *
     * @return
     */
    public List<SaleOrganization> findAllOrganizations();

    /**
     * 根据部门编码和岗位名称精确查询所需要的组装信息
     */
    List<SaleOrgPositionBean> findByUnitIdAndPositionName(String id, String positionName);

    /**
     * 根据部门编码和岗位名称模糊查询所需要的组装信息
     */
    List<SaleOrgPositionBean> findByUnitIdAndPositionNameLike(String unitId, String positionName);
}
