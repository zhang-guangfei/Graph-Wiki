package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.cost.ImpdataAdjustDO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-03-03 15:40
 * Description:
 */
public interface ImpdataAdjustService {

    /**
     * 根据单号查询已入成本票号
     *
     * @param orderNo 单号
     * @return 票号
     */
    String getInvoiceNoByOrderNo(String orderNo);

    /**
     * 按申请批量写入组换申请成本
     *
     * @return 写入结果
     */
    ResultVo<String> importAssemblyCostData(List<ImpdataAdjustDO> costDataList);

//    /**
//     * 录入组换申请成本
//     *
//     * @return 录入结果
//     */
//    ResultVo<String> importAssemblyCostData();
}
