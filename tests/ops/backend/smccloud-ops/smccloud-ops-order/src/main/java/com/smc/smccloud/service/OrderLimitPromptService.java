package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.PageModel;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.OrderRemindDto;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/11 13:15
 */
public interface OrderLimitPromptService {
    ResultVo<List<OrderRemindDto>> exportExcelData(OrderRemindDto condition);

    PageInfo<OrderRemindDto> searchStockTransferPlanByPage(PageModel<OrderRemindDto> pageModel) throws OpsException;

    ResultVo selectBufferDays();

    ResultVo updateBufferDays(String bufferDays);

    ResultVo handle();
}
