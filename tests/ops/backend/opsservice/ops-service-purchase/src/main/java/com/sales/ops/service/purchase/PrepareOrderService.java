package com.sales.ops.service.purchase;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsPrepareOrder;
import com.sales.ops.db.entity.OpsPrepareOrderAccount;
import com.sales.ops.dto.prepareOrder.*;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface PrepareOrderService {

    /**
     * 准备订单校验接口
     * @param modelNos
     * @return
     */
    ResultVo<Map<String, Boolean>> prepareOrderVerify(List<String> modelNos);

    /**
     * 创建准备订单接口
     * @param opsPrepareOrders
     * @return
     */
    ResultVo<String> prepareOrderCreate(List<OpsPrepareOrder> opsPrepareOrders);


    /**
     * 准备订单预处理
     * @return
     */
    ResultVo<String> prepareOrderPreHandle();


    /**
     * 向PSI推送准备订单
     */
    ResultVo<String> pushPrepareOrderToPSI();

    /**
     * 从PSI拉取返信信息
     */
    ResultVo<String> pullPrepareOrderDeliveryInfoFromPsi();

    /**
     * 从psi拉取数据解析失败 计入异常
     */
    void insertPsiParseFailData(Long id, String content, String sourceType,String remark);

    /**
     * 获取可使用的准备订单清单
     */
    ResultVo<OpsPrepareOrderVO> getAvailablePrepareOrderList(CanUsePrepareOrderParam  param);


    /**
     * (UI)准备订单管理查询
     */
    ResultVo<PageInfo<OpsPrepareOrderVO>> queryPrepareOrderList(PrepareOrderQueryParam param);

    /**
     * 导出准备订单
     */
    void exportPrepareOrder(PrepareOrderQueryParam param, HttpServletResponse response);

    /**
     * 编辑准备订单可用用户
     */
    ResultVo<String> editPrepareOrderAvailableUser(EditPrepareOrderAvailableUserDto editDto);

    /**
     * 根据订单号获取bom拆分信息
     */
    ResultVo<List<OpsPrepareOrderBomDetailDto>> getPrepareOrderBomDetail(String orderFNo);

    /**
     * 根据订单号获取核销信息
     */
    ResultVo<List<OpsPrepareOrderReconciliationDto>> getPrepareOrderVerifyInfo(String orderFNo);

    /**
     * 根据订单号获取决算信息
     */
    ResultVo<List<OpsPrepareOrderAccount>> getPrepareOrderAccountInfo(String orderFNo);

    /**
     * 根据订单号获取清算结果
     */
    ResultVo<List<OpsPrepareOrderLiquidationDto>> getPrepareOrderLiquidationInfo(String orderFNo);

    /**
     * 获取准备订单信息
     */
    ResultVo<OpsPrepareOrder> getPrepareOrderInfo(String orderFno);

    /**
     * 准备订单转订
     */
    ResultVo<String> prepareOrderTransfer(PrepareOrderTransferDto dto);

    /**
     * 追加准备订单预占
     */
    ResultVo<String> updatePreQty(PrepareOrderPreQtyUpdateDto dto);

    /**
     * 获取准备订单交货期天数
     */
    ResultVo<String> getDlvDays();

    /**
     * 更新准备订单交货期天数
     */
    ResultVo<String> updateDlvDays(int dlvDays);

}
