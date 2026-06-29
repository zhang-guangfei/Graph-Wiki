package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.OrderDelivery;
import com.smc.smccloud.model.order.UpdateOrderInfoResultVO;
import com.smc.smccloud.model.order.orderEdit.UpMasterInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderAddressInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderDlvDateInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderExpDlvType;

import java.util.List;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2022/10/20 14:21
 * @Descripton TODO
 */
public interface OrderEditService {

    /**
     * 批量修改货期
     */
    ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpDlvDate(List<UpOrderDlvDateInfo> orderDeliveryDate);

    /**
     * 批量修改地址
     */
    ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpdateAddress(List<UpOrderAddressInfo> upOrderAddressInfo);

    /**
     * 批量修改特发
     */
    ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpExpDlvType(List<UpOrderExpDlvType> upOrderExpDlvType);

    /**
     * 修改主单信息
     */
    ResultVo<Map<String,UpdateOrderInfoResultVO>> batchUpMastInfo(List<UpMasterInfo> upMasterInfos);




}
