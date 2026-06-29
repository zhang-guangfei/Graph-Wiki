package com.sales.ops.service.purchase;

import com.sales.ops.db.entity.OpsPoDestinationConfig;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/11/27 9:15
 */
public interface SplitSmcCodeService {
    OpsPoDestinationConfig getSubCode(String supplierId, String modelNo, String warehouseCode
            , String orderType, String transType);
}
