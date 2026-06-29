package com.sales.ops.service.wm;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.order.FinishPoListForDto;

/**
 * @author ：C14717
 * @version: $
 * @description： 采购完纳扣减InvMove数量
 * @date ：Created in 2023/8/22 11:51
 */
public interface WmFinishInvMoveService {
    void exeFinishInvMoveQty(FinishPoListForDto paramDto) throws OpsException;
}
