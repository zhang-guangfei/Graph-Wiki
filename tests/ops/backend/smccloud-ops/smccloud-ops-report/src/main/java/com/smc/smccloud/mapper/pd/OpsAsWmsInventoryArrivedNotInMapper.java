package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsArriverNotInRequestVO;
import com.smc.smccloud.model.pd.OpsAsWmsInventoryArrivedNotInDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-06-13
 */
@Mapper
@DS("reportdb")
public interface OpsAsWmsInventoryArrivedNotInMapper extends BaseMapper<OpsAsWmsInventoryArrivedNotInDO> {

    @Select("<script>" +
            " select pd_batch_no,wms_sys_warehouse_code,wms_sys_invoice_no,l_is_all,l_invoice_no,l_warehouse_code,wms_sys_is_all,logistics_confirm " +
            " from ops_as_wms_inventory_arrived_not_in  " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " pd_batch_no = #{batchNo} and " +
            "<if test = 'dto.warehouseCodes != null and dto.warehouseCodes.size() &gt; 0 ' >" +
            " wms_sys_warehouse_code in " +
            "   <foreach collection = 'dto.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "   </foreach>" +
            " and " +
            "</if>"+
            "<if test = 'dto.invoiceNo != null and dto.invoiceNo != \"\" '>" +
            " wms_sys_invoice_no like #{dto.invoiceNo} and " +
            "</if>"+
            "<if test = 'dto.logisticsConfirm != null and dto.logisticsConfirm != \"\" '>" +
            " logistics_confirm = #{dto.logisticsConfirm} and " +
            "</if>"+
            "</trim>" +
            " group by pd_batch_no,wms_sys_warehouse_code,wms_sys_invoice_no,wms_sys_is_all,l_invoice_no,l_warehouse_code,l_is_all,logistics_confirm "+
            " order by wms_sys_warehouse_code asc,wms_sys_invoice_no asc " +
            "</script>")
    List<OpsAsWmsInventoryArrivedNotInDO> listArriveNotInWithGroup(@Param("dto") OpsArriverNotInRequestVO dto,@Param("batchNo")String batchNo);



    @Select("<script>" +
            " select pd_batch_no,wms_sys_warehouse_code,wms_sys_invoice_no,l_invoice_no,l_warehouse_code,case_no,model_no,bill_qty,logistics_confirm,create_time as createTime2,order_no,pd_data_type,barcode " +
            " from ops_as_wms_inventory_arrived_not_in  " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " pd_batch_no = #{batchNo} and " +
            "<if test = 'dto.warehouseCodes != null and dto.warehouseCodes.size() &gt; 0 ' >" +
            " wms_sys_warehouse_code in " +
            "   <foreach collection = 'dto.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "   </foreach>" +
            " and " +
            "</if>"+
            "<if test = 'dto.invoiceNo != null and dto.invoiceNo != \"\" '>" +
            " wms_sys_invoice_no like #{dto.invoiceNo} and " +
            "</if>"+
            "<if test = 'dto.logisticsConfirm != null and dto.logisticsConfirm != \"\" '>" +
            " logistics_confirm = #{dto.logisticsConfirm} and " +
            "</if>"+
            "</trim>" +
            "</script>")
    List<OpsAsWmsInventoryArrivedNotInDO> listArriveNotInDetail(@Param("dto") OpsArriverNotInRequestVO dto,@Param("batchNo")String batchNo);


    @Select("select count(*) from ops_as_wms_inventory_arrived_not_in ")
    int selectCountNum();

    @Delete("delete from ops_as_wms_inventory_arrived_not_in where pd_batch_no = #{pdNo} and data_resource = #{dataResource}")
    void delarrivedNotIn(@Param("pdNo") String pdNo,@Param("dataResource") String dataResource);

    @Update("update ops_as_wms_inventory_arrived_not_in set l_invoice_no = null,l_warehouse_code = null,l_is_all = null,logistics_confirm = null " +
            " where pd_batch_no = #{pdNo} and data_resource = #{dataResource} ")
    void updateLastOpt(@Param("pdNo") String pdNo,@Param("dataResource") String dataResource);


    @Select("<script> " +
            "select top(10) wms_sys_invoice_no from ops_as_wms_inventory_arrived_not_in where isnull(wms_sys_invoice_no,'') != '' " +
            "<if test='invoiceNo != null and invoiceNo != \"\" ' >" +
            " and wms_sys_invoice_no like #{invoiceNo}  " +
            "</if>" +
            " group by wms_sys_invoice_no order by wms_sys_invoice_no asc" +
            "</script>")
    List<OpsAsWmsInventoryArrivedNotInDO> getTopTenInvoiceLike(@Param("invoiceNo") String invoiceNo);

}
