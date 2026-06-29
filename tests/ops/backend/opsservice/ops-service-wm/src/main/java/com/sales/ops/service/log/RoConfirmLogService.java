package com.sales.ops.service.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRoConfirmLog;

import java.util.List;

/**
 * @author C12961
 * @date 2023/4/20 10:25
 */
public interface RoConfirmLogService {
    /**
     * 批量写入物流入库指令日志返回
     * 到货确认结果保存
     */
    Integer insertBatchConfirmLog(List<OpsRoConfirmLog> list) throws OpsException;

    List<OpsRoConfirmLog> getRoConfirmLogsByInvoiceId(Long invoiceId, String invoiceNo);
}
