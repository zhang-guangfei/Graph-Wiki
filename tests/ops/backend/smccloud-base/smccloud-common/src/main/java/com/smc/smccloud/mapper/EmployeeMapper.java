package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Employee.EmployeeVO;
import com.smc.smccloud.model.EmployeeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opscmm")
public interface EmployeeMapper extends BaseMapper<EmployeeDO> {

    /**
     * 根据行业部门代码查询行业负责人信息
     *
     * @param deptNo 行业部门代码
     * @return 行业负责人信息
     */
    @Select(" select id, Name, CellPhone, Email " +
            "  from hr_employee e inner join hr_employee_position p on e.Id=p.EmployeeId " +
            "  where e.Status='1' and p.PositionId in ( " +
            "  select position_id from hr_org_position p where p.is_deleted=0 and p.position_id in ( " +
            "  select id from hr_position where Name='行业负责人' and org_id=#{deptNo}))")
    EmployeeVO getIndManageInfoByDeptNo(@Param("deptNo") String deptNo);



    @Select(" select id, Name" +
            " from hr_employee e inner join hr_employee_position p on e.Id=p.EmployeeId " +
            " where e.status=1 and OrgId=#{deptNo}")
    List<EmployeeVO> getEmployeeByDeptNo(@Param("deptNo") String deptNo);
}
