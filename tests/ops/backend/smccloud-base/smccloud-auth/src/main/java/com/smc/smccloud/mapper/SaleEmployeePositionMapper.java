package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.login.EmployeePosition;
import com.smc.smccloud.model.login.SaleEmployeePosition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@DS("opscmm")
@Mapper
public interface SaleEmployeePositionMapper extends BaseMapper<SaleEmployeePosition>
{
    @Select("SELECT " +
            " E.ID PSNSMCID ," +
            " E.ID EM_ID," +
            " E.NAME PSNNAME," +
            " E.STATUS," +
            " P.ID POSITIONID," +
            " P.NAME POSITIONNAME," +
            " EP.ISPRIMARYPOSITION ISPRIMARY," +
            " 0 ISFUZEREN," +
            " O.ID UNITID," +
            " O.NAME UNITNAME," +
            " 'DEPTCODE' DEPTCODE," +
            " 'UNITCODE' UNITCODE," +
            "  O.FULLNAME" +
            "  FROM HR_EMPLOYEE E, Hr_employee_position EP ," +
            "  HR_POSITION P , HR_ORG_POSITION OP ,HR_organization O  " +
            "  WHERE E.ID = EP.EMPLOYEEID " +
            "  AND EP.POSITIONID = P.ID " +
            "  AND P.ID = OP.POSITION_ID " +
            "  AND OP.ORG_ID = O.ID " +
            "  AND E.ID = #{username} AND EP.STATUS = '1'")
    List<EmployeePosition> getByEmployeeId(@Param("username") String username);

    @Select("SELECT COUNT(*) FROM Hr_employee_position where PositionId = #{positionId} and status = 1 ")
    Integer getCountByPositionId(@Param("positionId") String positionId);
}
