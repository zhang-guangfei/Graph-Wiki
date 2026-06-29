package com.sales.ops.api.service.wms;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsCoordinate;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.util.CommonResult;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：接收Wms的数据
 * @date ：Created in 2021/10/27 16:53
 */
public interface OpsRecApiFromWmsService {

    CommonResult doConfirm(WmDoConfirmDto param) throws OpsException;

    CommonResult roConfirm(WmRoConfirmDto param) throws OpsException;

    CommonResult wmPCOConfirm(WmPCOConfirmDto param) throws OpsException;

    CommonResult wmDoStatus(WmDoStatusDto param) throws OpsException;

    void savaLog(OpsCoordinate opsCoordinate);

    void saveDoBarcode(WmDoBarcodeDto param);
}
