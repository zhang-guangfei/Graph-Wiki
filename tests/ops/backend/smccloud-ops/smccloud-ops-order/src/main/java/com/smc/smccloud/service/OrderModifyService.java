package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.OrderDeleteReturn;
import com.smc.smccloud.model.order.orderEdit.UpApproveReplayVO;
import com.smc.smccloud.model.order.orderEdit.UpPurOrderSupplierVO;
import com.smc.smccloud.model.ordermodify.*;
import com.smc.smccloud.model.receiveorder.ReceiveOrderRequest;
import com.smc.smccloud.model.receiveorder.ReceiveOrderVO;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
public interface OrderModifyService {

    ResultVo<PageInfo<OrderModifyVO>> listOrderModifyByPage(OrderModifyRequest request);

    //订单修改查询导出
    void exportOrderModifyData(OrderModifyRequest request);

    ResultVo<String> modifyOrders(ModifyRcvOrderDTO info);

    ResultVo<List<ReceiveOrderVO>> listRcvModifyOrders(ReceiveOrderRequest request);

    ResultVo<String> approveOrderModify(ApproveOrderModifyDTO dto);

    /**
     * 门户取消订单
     */
    ResultVo<List<OrderDeleteReturn>> applyOrderCancel(List<OrderModifyVO> list);

//    ResultVo<List<Long>> applyOrderModify(List<OrderModifyVO> list);

    /**
     * 驳回
     */
    ResultVo<String> returnOrderModify(ApproveOrderModifyDTO dto);

    //ResultVo<String> modifyOrderEprice(Date fromTime);

    ResultVo<String> calcImportLotEPrice();

    ResultVo<String> updateOrderModifyInfo(OrderModifyVO info);

    // 订单转订 业务回复审批内容
    ResultVo<String> upApproveReplay(UpApproveReplayVO info);

    // 变更采购单 业务回复审批内容
    ResultVo<String> upPoApproveReplay(UpApproveReplayVO info);

    // 订单转订-> 变更供应商
    ResultVo<OpsSalesCommonParamVO> upPurOrderSupplier(UpPurOrderSupplierVO upPurOrderSupplierVO);

    ResultVo<String> updateOrderModifyStatusById(UpdateOrderModifyParam info);


    ResultVo<String> calcExportLotEPrice();

    ResultVo<String> calcExportLotEPriceAssOrder(Date fromDate);
}
