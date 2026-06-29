package com.sales.ops.serviceimpl.event.v3.stockadjust;

import com.sales.ops.common.opsexception.OpsException;

import java.util.Map;

public interface OpsTransferOrderEventService {

    int updateForWait(String transNo, int transItem) throws OpsException;

    int updateForOuting(String transNo, int transItem) throws OpsException;

    int updateForOuted(String transNo, int transItem) throws OpsException;

    int updateForSignIn(String transNo, int transItem) throws OpsException;

    int updateForConfirm(String transNo, int transItem) throws OpsException;

    int updateForFinish(String transNo, int transItem) throws OpsException;

    void exeStockTransferPlan(String poNo, Integer poItem, Integer poSplitNo, Map<String, Object> param) throws OpsException;
}
