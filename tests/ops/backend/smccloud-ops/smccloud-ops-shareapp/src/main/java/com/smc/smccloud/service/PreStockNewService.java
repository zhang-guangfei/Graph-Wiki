package com.smc.smccloud.service;

import com.sales.ops.db.entity.OpsPrepareOrder;
import com.sales.ops.dto.prepareOrder.PrepareOrderTransferDto;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.prestock.PreStockApplyDO;
import com.smc.smccloud.model.prestock.PreStockDetailDO;
import com.smc.smccloud.model.prestock.PreStockResultDTO;
import com.smc.smccloud.model.prestock.RejectPrepareOrderUpStockStatusDto;

import java.util.List;

public interface PreStockNewService {

    /**
     * 生成采购订单
     *
     * @param applyInfo    申请信息
     * @param detailInfo   申请项信息
     * @param purchaseList 采购处理项
     */
    ResultVo<String> createPurchaseOrder(PreStockApplyDO applyInfo, PreStockDetailDO detailInfo, List<PreStockResultDTO> purchaseList, boolean autoFlag);

    ResultVo<String> savePurchaseOrder(List<OpsPrepareOrder> opsPrepareOrderList, List<RequestPurchaseInfoDto> PurchaseOrderList, List<OrderStateVO> orderStateList,
                                       PreStockDetailDO detailInfo, List<PreStockResultDTO> purchaseList);


    /**
     * 准备订单拒单 修改先行在库申请的状态为待处理, result表数据状态改为取消处理, 如果明细里有一项为待处理状态则主项申请状态为待处理
     */
    ResultVo<String> rejectPrepareOrderUpPreStockStatus(RejectPrepareOrderUpStockStatusDto dto);

    /**
     * 准备订单转订 result状态改为3已转定 新增一条对应数据
     */
    ResultVo<String> prepareOrderTransferWithPresotckResult(PrepareOrderTransferDto dto);
}
