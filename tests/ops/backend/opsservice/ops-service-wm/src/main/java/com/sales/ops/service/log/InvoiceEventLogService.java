package com.sales.ops.service.log;

import com.sales.ops.db.entity.ImpInvoiceEventLog;
import com.sales.ops.dto.flux.RoConfirmItem;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2023/3/14 16:47
 */
public interface InvoiceEventLogService {
    void insertInvoiceConfirmLog(String invoiceNo, List<RoConfirmItem> resultList, Date date);

    Long insertInvoiceEventLog(ImpInvoiceEventLog log);
}
