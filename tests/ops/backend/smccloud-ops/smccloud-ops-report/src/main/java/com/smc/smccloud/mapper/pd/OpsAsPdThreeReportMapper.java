package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsPdThreeReportDO;
import com.smc.smccloud.model.pd.OpsAsPdThreeReportWareDO;
import com.smc.smccloud.model.pd.SearchReportWareParams;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-12-19
 */
@Mapper
@DS("reportdb")
public interface OpsAsPdThreeReportMapper extends BaseMapper<OpsAsPdThreeReportDO> {

    // 生成盘点报表(分仓库)
    @Select("{ call as_pd_three_report_ware(@pd_batch_no=#{pd_batch_no, mode=IN, jdbcType=VARCHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void callAsPdThreeTeportWare(@Param("pd_batch_no") String pd_batch_no);

    // 生成盘点报表
    @Select("{ call as_pd_three_report(@pd_batch_no=#{pd_batch_no, mode=IN, jdbcType=VARCHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void callAsPdThreeTeport(@Param("pd_batch_no") String pd_batch_no);

    // 更新各仓的业务数
    @Update("update a set ${wareHouseCode}_yw_qty=b.ops_sum_qty  from  ops_as_pd_three_report a inner join \n" +
            "(select model_no,sum(ops_sum_qty) as ops_sum_qty from ops_as_pd_three_report_ware where pd_batch_no=#{pd_batch_no} and warehouse_code = #{wareHouseCode} group by model_no) b \n" +
            "on a.model_no=b.model_no\n" +
            "where pd_batch_no=#{pd_batch_no}")
    int updateYwQtyForWareHouse(@Param("wareHouseCode") String wareHouseCode,
                                @Param("pd_batch_no") String pd_batch_no);

    // 更新各仓的盘点数
    @Update("update a set ${wareHouseCode}_qty=b.wms_sum_qty  from  ops_as_pd_three_report a inner join \n" +
            "(select model_no,sum(wms_sum_qty) as wms_sum_qty from ops_as_pd_three_report_ware where pd_batch_no=#{pd_batch_no} and warehouse_code = #{wareHouseCode} group by model_no) b \n" +
            "on a.model_no=b.model_no\n" +
            "where pd_batch_no=#{pd_batch_no}")
    int updateWlQtyForWareHouse(@Param("wareHouseCode") String wareHouseCode,
                                @Param("pd_batch_no") String pd_batch_no);

    // 更新各仓的物流盘点数-业务账簿数
    @Update("update a set ${wareHouseCode}wl_yw_qty = b.qty from  ops_as_pd_three_report a inner join \n" +
            "(select model_no, (sum(wms_sum_qty) - sum(ops_sum_qty)) as qty from ops_as_pd_three_report_ware where pd_batch_no=#{pd_batch_no} and warehouse_code = #{wareHouseCode} group by model_no) b \n" +
            "on a.model_no=b.model_no\n" +
            "where pd_batch_no=#{pd_batch_no}")
    int updateWlQtySubtractYwQty(@Param("wareHouseCode") String wareHouseCode,
                                 @Param("pd_batch_no") String pd_batch_no);

    @Select("select * from ops_as_pd_three_report where ops_sum_qty <> wms_sum_qty and pd_batch_no = #{pdBatchNo}")
    List<OpsAsPdThreeReportDO> listSumOpsQtyNeSumWmsQty(@Param("pdBatchNo")String pdBatchNo);


}
