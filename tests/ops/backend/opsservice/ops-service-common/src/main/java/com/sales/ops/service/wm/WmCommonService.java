package com.sales.ops.service.wm;

import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * WM公共服务类
 *
 * @author B28029
 * @date 2023/1/10 10:24
 */
public interface WmCommonService {

    ResultVo<OpsWarehouse> getWarehouseByCode(String warehouseCode);

    void refreshWarehouseCache(String warehouseCode);

    ResultVo<String> getWarehouseTypeByCode(String warehouseCode);

    // bug10347 马腾 获取可采购仓库的基础信息
    ResultVo<List<OpsWarehouse>> getWarehouseCanPurchase();

    // 获取国内运输时间
    CommonResult<Integer> getWarehouseSalesbranchConfigByCode(String branchId, String warehouseCode);

    // 获取仓间调拨运输时间
    CommonResult<Integer> getWarehouseDeliveryDayByCode(String fromWarehouseCode, String toWarehouseCode);
}
