package com.sales.ops.serviceimpl.log;

import com.alibaba.fastjson.JSON;
import com.sales.ops.db.entity.ImpInvoiceEventLog;
import com.sales.ops.db.extdao.ImpInvoiceEventLogDao;
import com.sales.ops.dto.flux.RoConfirmItem;
import com.sales.ops.service.log.InvoiceEventLogService;
import com.sales.ops.service.log.OpsRoConfirmLogService;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2023/3/14 16:46
 */
@Slf4j
@Service
public class InvoiceEventLogServiceImpl implements InvoiceEventLogService {


    @Autowired
    private ImpInvoiceEventLogDao impInvoiceEventLogDao;
    @Autowired
    private OpsRoConfirmLogService opsRoConfirmLogService;


    /**
     * @description 发票确认日志
     * @author C12961
     * @date 2023/3/14 17:09
     */
    @Override
    public void insertInvoiceConfirmLog(String invoiceNo, List<RoConfirmItem> resultList, Date date) {
        ImpInvoiceEventLog log = new ImpInvoiceEventLog();
        log.setOpType("/order/confirmgoods");
        log.setRequestParam(invoiceNo);
        log.setOpStartTime(date);
        log.setReturnData(JSON.toJSONString(resultList));
        //插入发票事件日志
        insertInvoiceEventLog(log);
        //插入发票确认结果日志
        opsRoConfirmLogService.insertInvoiceConfirmItemLog(resultList);
    }

    /**
     * @description 【通用方法】插入发票事件日志
     * @author C12961
     * @date 2023/3/14 17:09
     */
    @Override
    public Long insertInvoiceEventLog(ImpInvoiceEventLog log) {
        if (StringUtil.isEmpty(log.getRequestParam())) {
            return 0L;
        }
        Long useMilliSecond = 0L;// 毫秒
        Date endTime = new Date();
        if (ObjectUtils.isEmpty(log.getOpStartTime())) {
            log.setOpStartTime(endTime);
        }
        useMilliSecond = endTime.getTime() - log.getOpStartTime().getTime();
        log.setOpStatus(0);
        log.setOpEndTime(endTime);
        log.setDuration(useMilliSecond);
        impInvoiceEventLogDao.insertImpInvoiceEventLog(log);
        return log.getId();
    }


}
