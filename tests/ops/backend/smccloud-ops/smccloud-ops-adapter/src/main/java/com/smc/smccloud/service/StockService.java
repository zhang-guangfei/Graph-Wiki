package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.AdapterResult;
import com.smc.smccloud.model.adapter.order.AdapterReturn;
import com.smc.smccloud.model.adapter.stock.*;
import com.smc.smccloud.model.stock.AdapterPreStockDTO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-02-16 11:27
 * Description:
 */
public interface StockService {

    /**
     * 生成分库补库申请
     *
     * @param apply 分库补库申请信息
     * @return 申请结果
     */
    AdapterResult handleSubTreasuryCreateMQ(ChinaRegionWarehouseSupplyApply apply);


    /**
     * 生成客户在库申请
     *
     * @param apply 申请信息
     * @return 处理结果
     */
    AdapterResult handleCustomersInStockCreateMQ(BinDataApply apply);

    /**
     * 回调先行在库申请项处理状态
     */
    ResultVo<Boolean> sendPreStockDetailStatusMessage(AdapterPreStockDTO dto);

    /**
     * 添加预占库存
     *
     * @param preSaleInventory 添加预占库存信息
     * @return 添加结果
     */
    List<AdapterResult> handlePreOccupiedInventoryCreateMQ(PreSaleInventory preSaleInventory);

    /**
     * 取消预占库存
     *
     * @param preSaleInventory 取消预占库存信息
     * @return 取消结果
     */
    AdapterResult handlePreOccupiedInventoryCancelMQ(PreSaleInventory preSaleInventory);

    /**
     * 客户在库调库
     *
     * @param info
     * @return
     */
    List<BinTransferReturn> handleCustomerInStockTransferCreateMQ(BinTransferInfo info);

}
