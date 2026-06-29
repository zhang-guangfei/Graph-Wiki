package com.smc.smccloud.service.employeeAndOrgan;

import com.smc.smccloud.model.login.EmployeePosition;
import com.smc.smccloud.model.login.SaleEmployeePosition;

import java.util.List;

public interface SaleEmployeePositionService
{
    /**
     * 查询员工岗位
     * @param employeeId
     * @return
     */
    public List<EmployeePosition> findEmployeePositionsByEmployeeId(String employeeId);

    /**
     *
     * findApprover:获取审批人接口
     *
     * @param
     * @returns
     */
    List<SaleEmployeePosition> findApprover(String employeeId, String orgId,
                                            String businessType);

    /**
     * 获取全部员工职位
     * @return
     */
    public List<SaleEmployeePosition> findAllEmployeePositions();

    /**
     * 获取全部员工职位
     * @return
     */
    public List<EmployeePosition> findAllKdEmployeePositions();
}
