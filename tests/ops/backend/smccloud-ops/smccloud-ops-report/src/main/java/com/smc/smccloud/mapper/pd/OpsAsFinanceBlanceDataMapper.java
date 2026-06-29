package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsFinanceBlanceDataDO;
import org.apache.ibatis.annotations.Delete;
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
 * @since 2023-06-26
 */
@Mapper
@DS("reportdb")
public interface OpsAsFinanceBlanceDataMapper extends BaseMapper<OpsAsFinanceBlanceDataDO> {

    @Select(" SELECT balQty as balanceQty,modelNo, '1' as dataSource \n" +
            " FROM [10.116.32.17].[smc_cost_auto].[dbo].[Balance] where convert(varchar(6),OptDate,112)=#{execDate}")
    @DS("smcdb30")
    List<OpsAsFinanceBlanceDataDO> getCwjcData(@Param("execDate") String execDate );


    @Select(" SELECT balQty as balanceQty,modelNo, '2' as dataSource\n" +
            " FROM V_Balance_GZ where convert(varchar(6),OptDate,112)=#{execDate} and StockNO= 'KGZ-GZ000'")
    @DS("gzcbdb")
    List<OpsAsFinanceBlanceDataDO> getGzCwjcData(@Param("execDate") String execDate );


    @Delete("delete from ops_as_finance_blance_data where pd_batch_no = #{batchNo}  ")
    void delCwjcData(@Param("batchNo") String batchNo);

    @Delete("delete from ops_as_finance_blance_data where pd_batch_no = #{batchNo}  ")
    @DS("sharedb")
    void delCwjcDataSharedb(@Param("batchNo") String batchNo);
}
