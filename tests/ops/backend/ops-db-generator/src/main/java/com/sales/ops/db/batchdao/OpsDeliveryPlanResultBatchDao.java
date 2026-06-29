package com.sales.ops.db.batchdao;


import com.sales.ops.db.entity.OpsDeliveryPlanResult;

import java.util.List;

/**
 *bugid: 13685
 */
public interface OpsDeliveryPlanResultBatchDao {

    Integer batchInsert(List<OpsDeliveryPlanResult> resultList);

}
