package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsPdThreeReportWareDO;
import com.smc.smccloud.model.pd.SearchReportWareParams;
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
 * @since 2023-12-19
 */
@Mapper
@DS("reportdb")
public interface OpsAsPdThreeReportWareMapper extends BaseMapper<OpsAsPdThreeReportWareDO> {


    @Select("select * from ops_as_pd_three_report_ware where ops_sum_qty <> wms_sum_qty and pd_batch_no = #{pdBatchNo} ")
    List<OpsAsPdThreeReportWareDO> listSumOpsQtyNeSumWmsQty(@Param("pdBatchNo")String pdBatchNo);

    @Select("<script>" +
            " select * from ops_report.dbo.ops_as_pd_three_report_ware where pd_batch_no like 'YPD%' " +
            "<if test = 'params.pdBatchNo != null and params.pdBatchNo != \"\" ' >" +
            " and pd_batch_no = #{params.pdBatchNo} " +
            "</if>" +
            "<if test = 'params.modelNo != null and params.modelNo != \"\" ' >" +
            " and model_no like #{params.modelNo} " +
            "</if>" +
            "<if test = 'params.getWarehouseList() != null and params.getWarehouseList().size() &gt; 0 ' >" +
            " and warehouse_code in " +
            "   <foreach collection = 'params.getWarehouseList()' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "    #{item}" +
            "   </foreach>" +
            "</if>"+
            " order by  model_no desc , pd_batch_no desc " +
            "</script>")
    List<OpsAsPdThreeReportWareDO> listYDPdThreeReportWare(@Param("params") SearchReportWareParams params);
}
