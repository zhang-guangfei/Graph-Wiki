package com.sales.ops.db.batchdao;

import com.sales.ops.db.entity.ErrorMsgNotice;
import com.sales.ops.db.entity.OpsDeliveryEstimateResult;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/11/9 8:47
 */
public interface BatchInsertEstimateResultDao {

        void batchInsert(List<OpsDeliveryEstimateResult> record);

        void batchInsertByValues(List<OpsDeliveryEstimateResult> record);

        void batchInsertErrorMsgNotice(List<ErrorMsgNotice> record);
}
