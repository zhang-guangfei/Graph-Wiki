package com.sales.ops.serviceimpl.order;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.extdao.OrderReturnQtyDao;
import com.sales.ops.dto.order.OpsInvoiceResDto;
import com.sales.ops.dto.util.MenhuResult;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.feign.OpsInvoiceFeignApi;
import com.sales.ops.service.feign.CreditSystemApi;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.OpsCustomerOrderService;
import com.sales.ops.service.wmOrder.BaseDoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 客户订单信息业务类
 * @author C12961
 * @date 2021/11/25 16:32
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AnalyzeCustomerOrderServiceImpl implements OpsCustomerOrderService {
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Resource
    private CreditSystemApi creditSystemApi;
    @Autowired
    private BaseDoService baseDoService;
    @Resource
    private OpsInvoiceFeignApi opsInvoiceFeignApi;
    @Resource
    private OrderReturnQtyDao orderReturnQtyDao;

    //允许
    @Override
    public void checkChangeable(RcvOrderStatusEnum fromStatus, RcvOrderStatusEnum toStatus) throws OpsException {
        Boolean enable = baseCustomerOrderService.enableUpdateStatus(fromStatus, toStatus);
        if (!enable) {
            throw Exceptions.OpsException(String.format("状态变更顺序异常：【%s】不允许变更为【%s】", fromStatus.getType(), toStatus.getType()));
        }
    }

    //当所有do上的货齐时间有值时，取最晚一条时间记录在rcvdetail中
    @Override
    public Date analyzeRcvReadyTime(String orderId, String orderItem) throws OpsException {
        //计算rcvdetail级别的货齐时间
        List<OpsDo> jyckDoList = baseDoService.findAllJYCKByOrder(orderId, orderItem);
        //bug15269 货齐时间优化 2024-10-17
        List<Date> doReadyTimes = new ArrayList<>();
        for (OpsDo opsDo : jyckDoList) {
            Date doReadyTime = opsDo.getDoReadyTime();
            if (doReadyTime == null) {
                return null;
            }
            doReadyTimes.add(doReadyTime);
        }
        if (doReadyTimes.isEmpty()) {
            return null;
        } else {
            Date rcvReadyTime = doReadyTimes.stream().max(Date::compareTo).get();
            return rcvReadyTime;
        }
    }

    @Override
    public Date analyzeRcvEntryTime(String orderId, String orderItem) throws OpsException {
        //计算rcvdetail级别的入库时间
        List<OpsDo> jyckDoList = baseDoService.findAllJYCKByOrder(orderId, orderItem);
        //bug15269 货齐时间优化 2024-10-17
        List<Date> dbReadyTimes = new ArrayList<>();
        for (OpsDo opsDo : jyckDoList) {
            Date dbReadyTime = opsDo.getDbReadyTime();
            if (dbReadyTime == null) {
                return null;
            }
            dbReadyTimes.add(dbReadyTime);
        }
        if (dbReadyTimes.isEmpty()) {
            return null;
        } else {
            Date rcvReadyTime = dbReadyTimes.stream().max(Date::compareTo).get();
            return rcvReadyTime;
        }
    }


    //批量：通过十位订单号查询信用拦截状态，返回map， key=orderFno ,value= 1：拦截，0：不拦截
    @Override
    public Map<String, String> checkCreditList(List<String> rorderFnoList) throws OpsException {
        Map<String, String> creditMap = creditSystemApi.getCreditInterceptFlagBatch(rorderFnoList);
        if (creditMap == null) {
            throw Exceptions.OpsException("查询拦截状态异常");
        }
        return creditMap;
    }

    //通过十位单号查询信用拦截 拦截：1，不拦截：0
    @Override
    public boolean checkCredit(String orderNo, String orderItem) throws OpsException {
        String orderFno = orderNo + "-" + orderItem;
        Map<String, String> map = checkCreditList(Collections.singletonList(orderFno));
        String intercept = map.get(orderFno);
        if (StringUtils.isNotBlank(intercept) && "1".equals(intercept)) {
            return true;
        }
        return false;
    }

    @Override
    public List<OpsInvoiceResDto> getInvoiceInfo(List<String> rorderFnoList) throws OpsException {
        MenhuResult<List<OpsInvoiceResDto>> invoiceInfo = opsInvoiceFeignApi.getInvoiceInfo(rorderFnoList);
        log.info("{}", JSONUtil.toJsonStr(invoiceInfo));
        if (invoiceInfo.isSuccess()) {
            return invoiceInfo.getContent();
        } else {
            throw Exceptions.OpsException("发票查询失败：" + invoiceInfo.getMessage());
        }
    }

    @Override
    public int getReturnedQty(String orderFno) {
        Integer qty = orderReturnQtyDao.selectReturnedQtyByRorderFno(orderFno);
        return Optional.ofNullable(qty).orElse(0);
    }


}
