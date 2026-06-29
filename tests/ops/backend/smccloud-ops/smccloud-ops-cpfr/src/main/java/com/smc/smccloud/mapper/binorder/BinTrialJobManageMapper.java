package com.smc.smccloud.mapper.binorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.BinTrialJobManageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wuweidong
 * @create 2023/6/2 10:09
 * @description
 */
@Mapper
@DS("opsreport")
public interface BinTrialJobManageMapper  extends BaseMapper<BinTrialJobManageDO> {


    @Select(" insert into  ops_bin_trial_salesbranch_config (job_id,sales_branch_id,warehouse_code,warehouse_code_update,warehouse_master,warehouse_master_update,create_user,create_time) " +
            "    select  #{newId},sales_branch_id,warehouse_code,warehouse_code_update,warehouse_master,warehouse_master_update,#{createUser},getdate() from ops_bin_trial_salesbranch_config " +
            "    where job_id=#{jobId} ")
    String copybinTrialSalesbranchConfig(@Param("jobId") Long jobId, @Param("newId") Long newId,@Param("createUser") String createUser);


//    @Select(" insert into  ops_bin_trial_salesbranch_config (job_id,sales_branch_id,warehouse_code,warehouse_code_update,create_user,create_time) " +
//            "select #{newId},sales_branch_id,warehouse_code,warehouse_code,#{createUser},getdate() From (" +
//            "select sales_branch_id,warehouse_code,priority,ROW_NUMBER() over(partition by sales_branch_id  order by priority) as idx From  ops_core.[dbo].[ops_warehouse_salesbranch_config] " +
//            ")A where idx=1  ")
//    String insertBaseSalesbranchConfig( @Param("newId") Long newId,@Param("createUser") String createUser);




}
