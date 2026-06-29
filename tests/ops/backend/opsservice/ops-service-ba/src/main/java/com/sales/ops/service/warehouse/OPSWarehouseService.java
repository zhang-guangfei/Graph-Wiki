package com.sales.ops.service.warehouse;

import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.db.entity.OpsWarehouseSupplierConfig;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：仓库相关缓存业务
 * @date ：Created in 2021/10/18 10:45
 */
public interface OPSWarehouseService {

    //仓库表
    OpsWarehouse selectWareHouseByCondition(String warehouseCode);
    /**
     * 根据表时间更新刷新缓存
     * @param mi 分钟
     * @return
     */
    List<String> refreshWareHouseData(String mi);

    //仓库供应商关联表
    List<OpsWarehouseSupplierConfig> selectWarehouseSupplierConfigByCondition(String warehouseCode);//supplierid

    /**
     * 根据表时间更新刷新缓存
     * @param mi 分钟
     * @return
     */
    List<String> refreshWarehouseSupplierConfigData(String mi);

    //物理仓库--营业所关系表:营业所代码/代理店
    List<OpsWarehouseSalesbranchConfig> selectOpsWarehouseSalesbranchConfig(String warehouseCode);//sales_branch_id

    /**
     * 根据表时间更新刷新缓存
     * @param mi 分钟
     * @return
     */
    List<String> refreshWarehouseSalesbranchConfigData(String mi);

}
