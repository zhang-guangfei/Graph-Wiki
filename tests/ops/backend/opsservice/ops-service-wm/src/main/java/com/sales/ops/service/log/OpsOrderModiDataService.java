package com.sales.ops.service.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsOrderModidata;
import com.sales.ops.db.entity.Rcvdetail;

import java.util.Date;

/**
 * @author C12961
 * @date 2022/4/13 8:46
 */
public interface OpsOrderModiDataService {
    int insertOpsOrderModiData(OpsOrderModidata modidata) throws OpsException;

    int insertModiDataForCancelPo(String orderId, Integer orderItem, String orderFullNo, String remark) throws OpsException;

    int insertModiDataForResetInit(String orderId, Integer orderItem, String orderFullNo, String remark) throws OpsException;

    int insertModiDataForCancelOrder(String orderId, Integer orderItem, String orderFullNo, String reason, String duty, String origin) throws OpsException;

    int insertModiDataForModifyOrder(String orderId, Integer orderItem, String orderFullNo, Date oDlvdate, Date nDlvdate, String creator) throws OpsException;

    int insertModiDataForFinishOrder(Rcvdetail newRcv, Rcvdetail oldRcv, String userName) throws OpsException;
}
