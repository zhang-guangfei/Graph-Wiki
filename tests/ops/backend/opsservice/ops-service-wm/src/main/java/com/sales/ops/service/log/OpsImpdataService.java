package com.sales.ops.service.log;

import com.sales.ops.db.entity.OpsRo;

/**
 * @author C12961
 * @date 2023/4/20 11:42
 */
public interface OpsImpdataService {
    void updateImpdataForRoConfirm(String invoiceNo,Long invoiceId, OpsRo opsRo, String scanType, String roBarcode, String caseNo);
}
