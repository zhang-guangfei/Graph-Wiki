package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.model.Adapter.Department;

import java.util.Date;
import java.util.List;

public interface AdapterService {

    /**
     * 查询部门列表
     * @return
     */
    ResultVo<List<Department>> findSaleDeptCommon(DataAuthoritySearchCondition condition, String deptNo);


    /**
     * 计算发货日期是否为工作日Tbl_WorkDay_Year,返回有效发货日期(当前日期的邻近前一天工作日日期)
     */
    Date calDlvDate(String deliveryDate, String today);


    void syncWorkDay();
}
