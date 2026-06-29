package com.sales.ops.service.delivery.exp_po;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.delivery.ExpPoDto;

import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/8/27 11:52
 */
public interface ExpPoService {
    /**
     * 14912 20240827 c14717
     * inq-a 根据输入的客户期望货期倒算相应的采购单的期望发货日
     * @param expDate
     * @param orderNo
     * @param orderItem
     * @return
     * @throws OpsException
     */
    List<ExpPoDto> calculatePoExpDate(Date expDate, String orderNo, Integer orderItem) throws OpsException;
}
