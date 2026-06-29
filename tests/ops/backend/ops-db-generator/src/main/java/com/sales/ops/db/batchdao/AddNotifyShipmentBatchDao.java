package com.sales.ops.db.batchdao;

import com.sales.ops.dto.replacement.NotifyShipmentPlanDto;
import com.sales.ops.dto.replacement.NotifyShipmentPlanItemDto;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/29 10:45
 */
public interface AddNotifyShipmentBatchDao {

    void insert(NotifyShipmentPlanDto record);

    void batchInsert(List<NotifyShipmentPlanDto> record);

    void batchInsertItem(List<NotifyShipmentPlanItemDto> record);
}
