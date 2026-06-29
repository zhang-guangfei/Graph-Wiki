package com.smc.smccloud.service;

import com.smc.smccloud.model.adapter.BinDataApply;
import com.smc.smccloud.model.adapter.ChinaRegionWarehouseSupplyApply;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

/**
 * Author: B90034
 * Date: 2022-03-09 17:04
 * Description: SMSAdapterService
 */
public interface SMSAdapterService {

    /**
     * 获取在库补库申请信息
     */
    ResultVo<BinDataApply> findBinApply(String no);

    /**
     *  获取分库补库申请信息
     */
    ResultVo<ChinaRegionWarehouseSupplyApply> findChinaRegionWarehouse(String no);
}
