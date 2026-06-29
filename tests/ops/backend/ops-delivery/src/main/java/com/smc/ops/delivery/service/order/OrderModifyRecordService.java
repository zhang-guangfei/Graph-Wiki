package com.smc.ops.delivery.service.order;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/4 9:44
 */
public interface OrderModifyRecordService {
    void getOrderModifyReportToSaveExcelAndMailDb();

    @Transactional(rollbackFor = Exception.class)
    void saveMailToDb(String fileUrls, String mailAddress, String subject);
}
