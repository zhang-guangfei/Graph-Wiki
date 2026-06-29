package com.sales.ops.service.order;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.order.OpsInvoiceResDto;
import com.sales.ops.enums.RcvOrderStatusEnum;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author C12961
 * @date 2021/11/25 18:11
 */
public interface OpsCustomerOrderService {

    void checkChangeable(RcvOrderStatusEnum fromStatus, RcvOrderStatusEnum toStatus) throws OpsException;

    //当所有do上的货齐时间有值时，取最晚一条时间记录在rcvdetail中
    Date analyzeRcvReadyTime(String orderId, String orderItem) throws OpsException;

    Date analyzeRcvEntryTime(String orderId, String orderItem) throws OpsException;

    Map<String, String> checkCreditList(List<String> rorderFnoList) throws OpsException;

    //拦截：1，不拦截：0
    boolean checkCredit(String orderNo, String orderItem) throws OpsException;

    List<OpsInvoiceResDto> getInvoiceInfo(List<String> rorderFnoList) throws OpsException;

    int getReturnedQty(String orderFno);
}
