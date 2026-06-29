package com.sales.ops.service.log;

import com.sales.ops.dto.flux.RoConfirmItem;

import java.util.List;

public interface OpsRoConfirmLogService {
    void insertInvoiceConfirmItemLog(List<RoConfirmItem> resultList);
}
