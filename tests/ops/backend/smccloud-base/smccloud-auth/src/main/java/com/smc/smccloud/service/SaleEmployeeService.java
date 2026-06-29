package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.login.SaleEmployee;

import java.util.List;

public interface SaleEmployeeService
{
    /**
     * 分页查询员工信息（根据id和name）
     * findShortUsers:(这里用一句话描述这个方法的作用).
     */
    public PageInfo<List<SaleEmployee>> findShortUsers(SaleEmployee saleEmployee, Page page);

    /**
     * 获取部门下的所有员工
     */
    public List<SaleEmployee> findAllEmployee(String orgId);

    /**
     * findEmployeeByIdAndName:(根据员工Id或者Name获取员工信息).
     */
    public SaleEmployee findEmployeeByIdAndName(String id, String name);
}
