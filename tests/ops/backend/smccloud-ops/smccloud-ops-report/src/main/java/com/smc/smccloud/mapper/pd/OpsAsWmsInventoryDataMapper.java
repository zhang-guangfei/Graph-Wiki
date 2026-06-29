package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.pd.OpsAsPdCompensateDataDO;
import com.smc.smccloud.model.pd.OpsAsWmsInventoryDataDO;
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
 * @since 2023-06-12
 */
@Mapper
@DS("reportdb")
public interface OpsAsWmsInventoryDataMapper extends BaseMapper<OpsAsWmsInventoryDataDO> {

    @Delete("TRUNCATE TABLE ops_as_wms_inventory_data")
    void delOpsAsWmsInventoryData();

    // 发票数据
    @Select("<script>" +
            " select invoice_no,warehouse_code from ops_as_wms_inventory_data \n" +
            " where pd_data_type in " +
            "<if test = 'dataType != null and dataType.size() &gt; 0  '> " +
            "   <foreach collection = 'dataType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            "</if>" +
            " GROUP BY invoice_no,warehouse_code" +
            "</script>")
    List<OpsAsWmsInventoryDataDO> findOpsAsWmsInventoryGroupByInventory(@Param("dataType") List<String> dataType);

    // 明细数据
    @Select("<script>" +
            " select invoice_no,warehouse_code,case_no,barcode,model_no,bill_qty from ops_as_wms_inventory_data " +
            " where pd_data_type in " +
            "<if test = 'dataType != null and dataType.size() &gt; 0  '> " +
            "   <foreach collection = 'dataType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            "</if>"+
            "</script>")
    List<OpsAsWmsInventoryDataDO> findOpsAsWmsInventory(@Param("dataType") List<String> dataType);

    // 获取盘点形式
    @Select("select top(1) pd_bill_type from ops_as_wms_inventory_data where pd_data_type = '2' ")
    String getPdBillTypeByDataType();


    // 查询委托在库库存数据
    @Select("select a.modelno as modelNo, d.shelves as shelvesNo,\n" +
            "quantity as billQty ,a.warehouse_code as warehouseCode ,b.warehouse_type as warehouseType\n" +
            "from ops_core.dbo.ops_inventory a \n" +
            "left join ops_core.dbo.ops_warehouse b on a.warehouse_code=b.warehouse_code\n" +
            "left join ops_core.dbo.ops_customer c on b.customer_no=c.customer_no\n" +
            "left join ops_sharedb.dbo.ops_store_entrust_shelves d on d.agent_No=b.customer_no and d.model_No=a.modelno\n" +
            "where quantity>0 and a.warehouse_code like 'W%'\n")
    List<OpsAsWmsInventoryDataDO> wtDataImpWmsInventoryData();

}
