package com.sales.ops.web.controller.purchase;

import com.sales.ops.dto.expdetail.TransferVO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.purchase.TransferConfirmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/11/27 13:44
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/transferConfrim")
public class TransferConfirmController {

    @Autowired
    private TransferConfirmService transferConfirmService;

    @Autowired
    private OpsRedissonLock opsRedissonLock;

    /**
     * bugid:19186 c14717 20251127 合并smccode 转运信息确认
     * @param param
     * @return
     */
    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> handleTransferConfirm(@RequestBody TransferVO param) {
        boolean lock = false;
        String key = "ops:transfer:confirm:" + param.getOriginalInvoiceNo();
        try {
            lock = opsRedissonLock.addLock(key);
            return transferConfirmService.handleTransferConfirm(param);
        } catch (Exception e) {
            log.error("handleTransferConfirm",e);
            return CommonResult.failure(500,"服务异常");
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(key);
            }
        }
    }
}
