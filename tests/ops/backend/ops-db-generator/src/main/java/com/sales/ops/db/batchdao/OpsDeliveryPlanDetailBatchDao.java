package com.sales.ops.db.batchdao;


import com.sales.ops.db.entity.OpsDeliveryPlanDetail;

import java.util.List;

//bugid: 13685
public interface OpsDeliveryPlanDetailBatchDao {

    Integer batchInsert(List<OpsDeliveryPlanDetail> detailList);
}
