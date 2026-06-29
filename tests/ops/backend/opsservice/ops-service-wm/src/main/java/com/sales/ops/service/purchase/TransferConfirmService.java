package com.sales.ops.service.purchase;

import com.sales.ops.dto.expdetail.TransferVO;
import com.sales.ops.dto.util.CommonResult;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/11/27 10:56
 */
public interface TransferConfirmService {
    CommonResult<String> handleTransferConfirm(TransferVO param);
}
