package com.sales.ops.db.batchdao;

import java.util.List;

import com.sales.ops.db.entity.OpsRequestpurchase;

public interface AddBatchRequestPurchaseDao {

	void insertBatch(List<OpsRequestpurchase> record);
}
