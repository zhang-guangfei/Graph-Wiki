package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.CodeName;
import com.smc.smccloud.model.Employee.EmployeePositionVO;
import com.smc.smccloud.model.Employee.EmployeeVO;
import com.smc.smccloud.model.customer.CstoAndUserInfoVO;

/**
 * Author: B90034
 * Date: 2022-01-22 11:54
 * Description:
 */
public interface EmployeeService {

    ResultVo<PageInfo<EmployeeVO>> findEmployeeInfoByIdOrName(EmployeeVO employeeVO);

    /**
     * 根据员工工号获取员工姓名
     *
     * @param employeeNo 工号
     * @return employeeName
     */
    ResultVo<String> getEmployeeNameByNo(String employeeNo);

    ResultVo<EmployeeVO> getEmployeeInfo(String employeeNo);

    ResultVo<CstoAndUserInfoVO> getCstoAndUserInfo(String customerNo, String userNo, String empSale, String endUserNo);

    /**
     * 根据部门代码查询行业负责人信息
     */
    ResultVo<EmployeeVO> getIndManageInfoByDeptNo(String deptNo);

    /**
     * 职位信息
     * @param positionId
     * @return
     */
    ResultVo<EmployeePositionVO> getEmployeePosition(String employeeId);
    /**
     * 根据部门代码查询部门所属员工
     *
     * @param deptNo 部门代码
     * @return CodeName
     */
    ResultVo<CodeName> getEmployeeCodeByDeptNo(String deptNo);

    void cacheAllEmployee();
}
