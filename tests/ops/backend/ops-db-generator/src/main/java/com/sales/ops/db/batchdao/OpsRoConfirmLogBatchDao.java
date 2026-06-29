package com.sales.ops.db.batchdao;


import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.OpsRoConfirmLog;

import java.util.List;

/**
 * RO-ConfirmLog相关联批量操作类
 *
 * @author B28029
 * @version 1.0
 * @date 2022/7/27 14:21
 */
public interface OpsRoConfirmLogBatchDao {
    /**
     * 批量增加入库OpsRoConfirmLog
     * @param roBarcodes
     */
    Integer insertRoConfirmLogBatch(List<OpsRoConfirmLog> roBarcodes);
}
