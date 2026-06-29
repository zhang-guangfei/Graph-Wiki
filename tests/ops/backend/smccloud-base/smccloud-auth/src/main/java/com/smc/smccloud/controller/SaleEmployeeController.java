package com.smc.smccloud.controller;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.login.SaleEmployee;
import com.smc.smccloud.service.employeeAndOrgan.SaleEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value="/employee")
public class SaleEmployeeController {

    @Autowired
    private SaleEmployeeService saleEmployeeService;

    @PostMapping("/queryAllEmployees")
    public ResultVo<PageInfo<List<SaleEmployee>>> queryAllEmployees(@RequestBody SaleEmployee saleEmployee, Page page) {
        return ResultVo.success(saleEmployeeService.findShortUsers(saleEmployee,page));
    }
}
