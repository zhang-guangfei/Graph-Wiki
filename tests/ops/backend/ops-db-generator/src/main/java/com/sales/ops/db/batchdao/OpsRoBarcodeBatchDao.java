package com.sales.ops.db.batchdao;


import com.sales.ops.db.entity.OpsRoBarcode;
import java.util.List;

/**
 * RO-Barcode相关联批量操作类
 *
 * @author B28029
 * @version 1.0
 * @date 2022/7/10 17:37
 */
public interface OpsRoBarcodeBatchDao {
    /**
     * 批量增加入库BARCODE
     * @param roBarcodes
     */
    Integer insertRoBarcodeBatch(List<OpsRoBarcode> roBarcodes);
}
