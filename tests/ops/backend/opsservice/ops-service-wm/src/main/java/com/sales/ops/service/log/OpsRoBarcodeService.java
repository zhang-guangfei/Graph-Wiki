package com.sales.ops.service.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsImpdata;
import com.sales.ops.db.entity.OpsRoBarcode;

import java.util.List;

/**
 * @author C12961
 * @date 2023/4/20 10:30
 */
public interface OpsRoBarcodeService {
    List<OpsRoBarcode> findRoBarcodeByRoId(String roId);

    OpsRoBarcode getRoBarcodeByBarcode(String roId, String barcdoe);

    OpsRoBarcode initOpsRoBarcodeForInvoice(String roId, String warehouseCode, OpsImpdata opsImpdata,
                                            int qty, String orderno, Integer itemno, Integer splititemno);

    Integer insertBatchBarcode(List<OpsRoBarcode> list);

    void updateOpsRoBarcodeForRoConfirm(String roId, String userName) throws OpsException;

    void delOpsRoBarcodeByRoId(String roId) throws OpsException;
}
