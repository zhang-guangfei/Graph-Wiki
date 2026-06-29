package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.db.entity.OpsWarehouseSupplierConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops库存查询相关 redis
 * @date ：Created in 2021/10/18 17:23
 */
public interface OPSWarehouseDao {


    /**
     * @author C14717
     * 根据仓库
     * @param warehouseCode
     * @return
     */
    @Select("SELECT warehouse_code, warehouse_type, warehouse_name, province,city,district,adress,link_phone,link_mobile, " +
            "post_code, linkman, mail, customer_no, customer_linkman, customer_phone, customer_mail, smc_linkman, smc_phone, " +
            "    smc_mail, disable_flag, assembly_flag, centralize_flag, delflag, cre_time, creator,  modify_time, modifier, " +
            "purchase_flag FROM dbo.ops_warehouse WHERE warehouse_code=#{warehouseCode} ")
    public OpsWarehouse queryWarehouseByWarehouseCode(@Param("warehouseCode") String warehouseCode);



    /**
     * @author C14717
     * 根据仓库和供应商关系表
     * @param warehouseCode
     * @return
     */
    @Select("SELECT warehouseCode, supplierId, id, priority, matchPattern, updateTime FROM dbo.ops_warehouse_supplier_config WHERE warehouseCode=#{warehouseCode} ")
    public List<OpsWarehouseSupplierConfig> queryOpsWarehouseSupplierConfigByWarehouseCode(@Param("warehouseCode") String warehouseCode);

    /**
     * @author C14717
     * 根据仓库营业所关联关系表
     * @param warehouseCode
     * @return
     */
    @Select("SELECT id, sales_branch_id, warehouse_code, priority, description, delivery_day, delflag,creator, cre_time, modifier, modify_time " +
            " FROM dbo.ops_warehouse_salesbranch_config WHERE warehouse_code=#{warehouseCode} ")
    public List<OpsWarehouseSalesbranchConfig> queryOpsOpsWarehouseSalesbranchConfigByWarehouseCode(@Param("warehouseCode") String warehouseCode);

    /**
     * @author C14717
     * 根据最近一小时新增或更新来删除redis  table
     * @return
     */
    @Select("SELECT warehouse_code FROM dbo.ops_warehouse where DateDiff(mi,cre_time,getDate())<=#{time} UNION ALL SELECT warehouse_code FROM dbo.ops_warehouse where DateDiff(mi,modify_time,getDate())<=#{time}")
    public List<String> refreshOpsWarehouseData(@Param("time") Long time);

    /**
     * @author C14717
     * 根据最近一小时新增或更新来删除redis ops_warehouse_supplier_config table
     * @return
     */
    @Select("SELECT warehouseCode FROM dbo.ops_warehouse_supplier_config where DateDiff(mi,updateTime,getDate())<=#{time}")
    public List<String> refreshOpsWarehouseSupplierConfigData(@Param("time") Long time);

    /**
     * @author C14717
     * 根据最近一小时新增或更新来删除redis ops_warehouse_salesbranch_config  table
     * @return
     */
    @Select("SELECT DISTINCT warehouse_code FROM dbo.ops_warehouse_salesbranch_config where DateDiff(mi,cre_time,getDate())<=#{time} UNION ALL SELECT DISTINCT warehouse_code FROM dbo.ops_warehouse_salesbranch_config where DateDiff(mi,modify_time,getDate())<=#{time}")
    public List<String> refreshOpsWarehouseSalesBranchConfig(@Param("time") Long time);

    /**
     * 获取仓库类型获取仓库代码集合
     */
//    @Select("select warehouse_code from ops_warehouse where warehouse_type= #{warehouseType} and delflag=0 ")
//    List<String> getWarehouseCodesByWarehouseType(@Param("warehouseType") String warehouseType);
}
