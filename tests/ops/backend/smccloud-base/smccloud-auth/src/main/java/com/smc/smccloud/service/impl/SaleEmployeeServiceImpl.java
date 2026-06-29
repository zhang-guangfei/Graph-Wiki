package com.smc.smccloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.mapper.SaleEmployeeMapper;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.login.SaleEmployee;
import com.smc.smccloud.service.employeeAndOrgan.SaleEmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SaleEmployeeServiceImpl implements SaleEmployeeService {

    @Resource
    private SaleEmployeeMapper saleEmployeeMapper;

    @Override
    public PageInfo<List<SaleEmployee>> findShortUsers(SaleEmployee saleEmployee, Page page) {
//        QueryWrapper<SaleEmployee> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("Status","1");
//        queryWrapper.eq(PublicUtil.isNotEmpty(saleEmployee.getId()),"id",saleEmployee.getId());
//        queryWrapper.eq(PublicUtil.isNotEmpty(saleEmployee.getName()),"name",saleEmployee.getName());
        return PageHelper.startPage(page.getPageNumber(), page.getPageSize()).doSelectPageInfo(() -> saleEmployeeMapper.getShortUsers(saleEmployee.getId(),saleEmployee.getName()));
    }

    @Override
    public List<SaleEmployee> findAllEmployee(String orgId) {
        return null;
    }

    @Override
    public SaleEmployee findEmployeeByIdAndName(String id, String name) {
        return null;
    }
}
