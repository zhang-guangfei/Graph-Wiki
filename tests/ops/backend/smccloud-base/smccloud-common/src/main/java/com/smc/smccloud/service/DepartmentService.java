package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.CodeName;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.Department.DepartmentDTO;
import com.smc.smccloud.model.Department.DepartmentVO;
import com.smc.smccloud.model.DepartmentDO;
import com.smc.smccloud.model.HROrganizationVO;

import java.util.List;

public interface DepartmentService {

    PageInfo<DepartmentDO> findDepartmentData(DepartmentDTO info, Page page);

    //保存或修改部门信息
    ResultVo<String> saveOrUpdateDeptInfo(DepartmentVO info);

    //根据id删除部门信息
    ResultVo<String> deleteDeptInfoById(Integer id);

    ResultVo<DepartmentVO> getDepartmentInfo(String deptNo);

    ResultVo<DepartmentVO> getDeptNoByOldNo(String oldDeptNo);

    ResultVo<List<DataCodeVO>> findDepartments();

    ResultVo<List<DepartmentVO>> listDepartment();

    /**
     * 根据deptNo获取部门名称
     *
     * @param deptNo deptNo
     * @return deptName
     */
    ResultVo<String> getDeptNameByNo(String deptNo);

    /**
     * 2位部门编码转6位
     *
     * @param deptNo
     * @return
     */
    ResultVo<String> getSixDeptNoByTwoDeptNo(String deptNo);


    /**
     * 根据销售部门代码查询营业所代码
     *
     * @param hrSalesDeptNo 销售部门代码
     * @return 营业所代码
     */
    ResultVo<String> getDeptNoByHRSalesDeptNo(String hrSalesDeptNo);

    ResultVo<String> getParentNumberByDeptNo(String deptNo);

    ResultVo<HROrganizationVO> findHrOrganByDeptNo(String deptNo);

    ResultVo<List<HROrganizationVO>> listHrOrganAllData(String deptNo);

    ResultVo<Integer> getDepartmentDlvDayByDeptNo(String deptNo);

    /**
     * deptNo为空时,默认返回 (北京/上海/广州)分公司/行业开发部 的树状部门数据结果.
     * deptNo不为空时,按deptNo返回树状部门数据结果
     *
     * @param deptNos (可空)
     */
    ResultVo<List<CodeName>> getDeptTreeByNo(List<String> deptNos);

    void cacheAllDepartmentInfo();

}
