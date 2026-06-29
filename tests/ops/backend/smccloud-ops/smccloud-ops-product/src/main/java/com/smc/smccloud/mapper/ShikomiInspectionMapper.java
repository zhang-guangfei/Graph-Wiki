package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.shikomi.ShikomiInspectionDO;
import com.smc.smccloud.model.shikomi.ShikomiInspectionRequest;
import com.smc.smccloud.model.shikomi.ShikomiInspectionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opsdb")
public interface ShikomiInspectionMapper extends BaseMapper<ShikomiInspectionDO> {


    @Select("<script>" +
            "SELECT i.id,i.ShikomiNo,i.ModelNo,i.ApplyNo,i.ApplyType,i.ApplyQty,i.ApplicantNo,i.ApplyDate,i.inspectStatus," +
            "i.RemainQty,i.CancelQty,i.CustomerQty,i.RepairQty,i.ExpirationHandle,i.RetentionDurationDate," +
            "v.DeptNo,v.CustomerNo,v.IndCode,v.QtyNoord FROM shikomi_inspection i INNER JOIN v_shikomi_total v " +
            "ON i.ShikomiNo = v.ShikomiNo AND i.ModelNo = v.modelno WHERE 1 = 1" +
            " <if test='request.modelNo!=null and request.modelNo!=\"\" '> and i.ModelNo=#{request.modelNo} </if>" +
            " <if test='request.shikomiNo!=null and request.shikomiNo!=\"\" '> and i.ShikomiNo=#{request.shikomiNo} </if>" +
            " <if test='request.applyType!=null and request.applyType!=\"\" '> and i.ApplyType=#{request.applyType} </if>" +
            " <if test='request.applyNo!=null and request.applyNo!=\"\" '> and i.ApplyNo=#{request.applyNo} </if>" +
            " <if test='request.indCode!=null and request.indCode!=\"\" '> and v.IndCode=#{request.indCode} </if>" +
            " <if test='request.customerNo!=null and request.customerNo!=\"\" '> and v.CustomerNo=#{request.customerNo} </if>" +
            " <if test='request.inspectStatus!=null and request.inspectStatus!=\"\" '> and i.inspectStatus=#{request.inspectStatus} </if>" +
            " <if test='request.retentionStartDate!=null and request.retentionStartDate!=\"\" '> and i.RetentionDurationDate &gt;= #{request.retentionStartDate} and i.retentionDurationDate &lt;= #{request.retentionEndDate} </if>" +
            "</script>")
    List<ShikomiInspectionVO> listShikomiInspectionData(@Param("request") ShikomiInspectionRequest request);

    // bugid:19263
    @Select("SELECT OrgId FROM ops_ui.dbo.hr_employee_position WHERE is_deleted =0 and EmployeeId = #{employeeId}")
    String getEmployeeDeptNo(@Param("employeeId") String employeeId);
}
