package com.smc.smccloud.service;

import com.sales.ops.dto.eta.ETAPageDto;
import com.sales.ops.dto.eta.FindETADataDto;
import com.sales.ops.dto.purchase.BaseDataDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.delivery.WarehouseSendDateVO;
import com.smc.smccloud.model.invoice.SalesInvoiceRequest;
import com.smc.smccloud.model.order.JudgeShipParams;
import com.smc.smccloud.model.order.SendProcessOrderWithRiskLevel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2023/8/11 13:35
 * @Descripton TODO
 */
public interface AdapterService {

    /**
     * 通过订单号 获取物流交货期
     */
    ResultVo<List<WarehouseSendDateVO>> getWarehouseSendDateByOrderNo(List<WarehouseSendDateVO> info);

    // 二次审批-获取风险等级
    ResultVo<List<SendProcessOrderWithRiskLevel>> findSendProcessRiskLevel(SalesInvoiceRequest request);

    // 获取所有的供应商列表
    ResultVo<List<BaseDataDto>> getAllSupplier();

    //查询参考货期
    ResultVo<ETAPageDto> findEtaAPI(List<FindETADataDto> dataList);

    // 运单号查询接口
    ResultVo getOrderRoute(String expressNo);

    ResultVo<List<JudgeShipParams>> judgeShipNew(List<JudgeShipParams> params);

    ResultVo<List<JudgeShipParams>> judgeShip(List<JudgeShipParams> params);
}
