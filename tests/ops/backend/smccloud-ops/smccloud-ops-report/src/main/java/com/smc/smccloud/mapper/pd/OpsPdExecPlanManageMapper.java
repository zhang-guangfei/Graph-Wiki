package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.ExecPlanParamVO;
import com.smc.smccloud.model.pd.OpsPdExecPlanManageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-11-04
 */
@Mapper
@DS("reportdb")
public interface OpsPdExecPlanManageMapper extends BaseMapper<OpsPdExecPlanManageDO> {

    @Select("<script>" +
            " select * from ops_pd_exec_plan_manage " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " <if test='paramVO.execDate != null and paramVO.execDate != \"\" '>" +
            "  SUBSTRING(exec_date, 1, 4)  = #{paramVO.execDate}  and " +
            " </if>" +
            " <if test='paramVO.execFlag != null'>" +
            "  exec_flag = #{paramVO.execFlag} " +
            " </if>" +
            "</trim>" +
            "</script>")
    List<OpsPdExecPlanManageDO> listExecPlan(@Param("paramVO") ExecPlanParamVO paramVO);


    @Select("select * from ops_pd_exec_plan_manage where exec_date = CONVERT(VARCHAR, GETDATE(), 23) and exec_flag = 0 ")
    List<OpsPdExecPlanManageDO> selectCountPlan();

}
