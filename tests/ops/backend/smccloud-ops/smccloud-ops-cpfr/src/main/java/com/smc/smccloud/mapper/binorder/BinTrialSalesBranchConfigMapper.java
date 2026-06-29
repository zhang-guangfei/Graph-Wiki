package com.smc.smccloud.mapper.binorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.BinTrialSalesBranchConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wuweidong
 * @create 2023/6/2 10:11
 * @description
 */
@Mapper
@DS("opsreport")
public interface BinTrialSalesBranchConfigMapper extends BaseMapper<BinTrialSalesBranchConfigDO> {

    @Select("<script>"
            + " update  ops_bin_trial_salesbranch_config set warehouse_code_update=warehouse_code,warehouse_master_update=warehouse_master "
            + " where job_id=#{jobId}"
            + "<if test = 'Ids != null and  Ids.size() &gt; 0' >"
            + " and id in "
            + "  <foreach collection='Ids' item='item' index='index' open='(' separator=',' close=')'> "
            + "  #{item}"
            + "  </foreach>"
            + "</if>"
            + "</script>")
    String restoreConfig(@Param("jobId") Long jobId, @Param("Ids") List<Long> Ids);


    @Select("<script> " +
            " insert into ops_bin_trial_salesbranch_config(job_id,sales_branch_id,warehouse_code,warehouse_code_update,warehouse_master,warehouse_master_update,create_user,create_time) " +
            " VALUES" +
            "<if test = 'data != null and  data.size() &gt; 0' >" +
            "  <foreach collection='data' item='item' index='index'  separator=',' > " +
            " (#{item.jobId}, #{item.salesBranchId}, #{item.warehouseCode},#{item.warehouseCodeUpdate},#{item.warehouseMaster},#{item.warehouseMasterUpdate},#{item.createUser},#{item.createTime})" +
            "  </foreach>" +
            "</if>" +
            "</script>")
    void  InsertByBatch(@Param("data") List<BinTrialSalesBranchConfigDO> list);

    @Select(" declare @begindate datetime " +
            " declare @EndDate datetime " +
            " exec [dbo].[GetLastMonthRange] 36 ,@begindate OUTPUT, @EndDate OUTPUT " +
            " select a.*From " +
            "  (select distinct dept_no as sales_branch_id,warehouse_code  From model_exp_detail where month_date between @begindate and @EndDate )" +
            " A left join (    select sales_branch_id From ops_bin_trial_salesbranch_config where job_id=#{jobId} )B on a.sales_branch_id=b.sales_branch_id" +
            " where b.sales_branch_id is null  and a.sales_branch_id is not null")
    List<BinTrialSalesBranchConfigDO>getAddOtherBranchConfig( @Param("jobId")  Long jobId);
}
