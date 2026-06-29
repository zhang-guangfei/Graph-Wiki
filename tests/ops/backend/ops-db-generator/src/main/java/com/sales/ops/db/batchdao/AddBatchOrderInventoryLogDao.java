package com.sales.ops.db.batchdao;


import com.sales.ops.db.entity.OpsOrderInventoryLog;

import java.util.List;

public interface AddBatchOrderInventoryLogDao {

    void batchInsertOrderBatchOrderInventoryLog(List<OpsOrderInventoryLog> orderInventoryLogList);
}