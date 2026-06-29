package com.sales.ops.dto.util;


import com.alibaba.fastjson.JSON;
import com.sales.ops.db.entity.ImpInvoiceEventLog;

public class LogUtil {

    public static ImpInvoiceEventLog createInvoiceLog(String url, Object params) {
        ImpInvoiceEventLog log = new ImpInvoiceEventLog();
        log.setOpType(url);
        log.setRequestParam(JSON.toJSONString(params));
        return log;
    }


}
