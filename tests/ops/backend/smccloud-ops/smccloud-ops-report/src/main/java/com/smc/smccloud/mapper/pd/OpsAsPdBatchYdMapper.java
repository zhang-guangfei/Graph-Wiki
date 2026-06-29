package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsPdBatchYdDO;
import com.smc.smccloud.model.pd.OpsPdExecPlanManageDO;
import com.smc.smccloud.model.pd.OpsPdStepManageDO;
import org.apache.ibatis.annotations.*;

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
public interface OpsAsPdBatchYdMapper extends BaseMapper<OpsAsPdBatchYdDO> {


    @Select("INSERT INTO ops_as_wms_inventory_data (\n" +
            "\t[pd_batch_no],\n" +
            "\t[warehouse_code],\n" +
            "\t[shelves_no],\n" +
            "\t[order_no],\n" +
            "\t[invoice_no],\n" +
            "\t[location_no],\n" +
            "\t[case_no],\n" +
            "\t[barcode],\n" +
            "\t[pd_no],\n" +
            "\t[pd_sort],\n" +
            "\t[model_no],\n" +
            "\t[bill_qty],\n" +
            "\t[pd_data_type],\n" +
            "\t[pd_bill_type],\n" +
            "\t[update_time],\n" +
            "\t[create_time],\n" +
            "\t[update_user],\n" +
            "\t[create_user],\n" +
            "\t[is_ass],\n" +
            "\t[warehouse_type] \n" +
            ") SELECT\n" +
            "pd_batch_no,\n" +
            "i.[warehouse_code],\n" +
            "[shelves_no],\n" +
            "[order_no],\n" +
            "[invoice_no],\n" +
            "[location_no],\n" +
            "[case_no],\n" +
            "[barcode],\n" +
            "[pd_no],\n" +
            "[pd_sort],\n" +
            "[model_no],\n" +
            "[bill_qty],\n" +
            "[pd_data_type],\n" +
            "[pd_bill_type],\n" +
            "getDate() AS [update_time],\n" +
            "getDate() AS [create_time],\n" +
            "'yd_sys' AS [update_user],\n" +
            "'yd_sys' AS [create_user],\n" +
            "[is_ass],\n" +
            "i.[warehouse_type] \n" +
            "FROM\n" +
            "\t[ops_sharedb].[dbo].[ops_as_wms_inventory_data] i\n" +
            "\tLEFT JOIN ops_core.dbo.ops_warehouse w ON i.warehouse_code = w.warehouse_code \n" +
            "WHERE\n" +
            "\tw.delflag = 0 and i.pd_batch_no = #{batchNo}")
    void batchInsertOpsAsWmsInventory(@Param("batchNo") String batchNo);

    @Delete("delete from ops_as_wms_inventory_data where pd_batch_no = #{batchNo} ")
    void deleteOpsAsWmsInventoryData(@Param("batchNo") String batchNo);

    @Select("INSERT INTO ops_as_wms_sub_inventory_data ( [pd_batch_no], [warehouse_code], [order_no], [model_no], [qty], [create_user], [create_time], [update_user], [update_time] ) SELECT\n" +
            "pd_batch_no,\n" +
            "i.[warehouse_code],\n" +
            "[order_no],\n" +
            "[model_no],\n" +
            "[qty],\n" +
            "'sys' AS [create_user],\n" +
            "getDate() AS [create_time],\n" +
            "'sys' AS [update_user],\n" +
            "getDate() AS [update_time]\n" +
            "FROM\n" +
            "[ops_sharedb].[dbo].[ops_as_wms_sub_inventory_data] i\n" +
            "LEFT JOIN ops_core.dbo.ops_warehouse w ON i.warehouse_code = w.warehouse_code \n" +
            "WHERE\n" +
            "w.delflag = 0 and i.pd_batch_no = #{batchNo} ")
    void batchInsertOpsAsWmsSubInventoryData(@Param("batchNo") String batchNo);

    @Delete("delete from ops_as_wms_sub_inventory_data where pd_batch_no = #{batchNo} ")
    void deleteOpsAsWmsSubInventoryData(@Param("batchNo") String batchNo);


    @Select("select * from ops_pd_step_manage where del_flag = '0' order by exec_seq asc ")
    List<OpsPdStepManageDO> getExecStepList();

    @Update("update ops_pd_step_manage set status = '0',remark = null, exec_time = getDate(), update_time = getDate() ")
    void resetPdExecStep();
}
