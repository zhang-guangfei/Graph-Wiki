package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.ordermodify.PurchaseModifyVO;
import com.smc.smccloud.service.PurchaseModifyApplyFeignApi;
import com.smc.smccloud.service.PurchaseModifyApplyService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PurchaseModifyApplyFeignClient implements PurchaseModifyApplyFeignApi {

    @Resource
    private PurchaseModifyApplyService purchaseModifyApplyService;


    @Override
    public ResultVo<String> handlePurchaseModify() {
        try {
            return purchaseModifyApplyService.handlePurchaseModify();
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    @Override
    public ResultVo<String> insertPurchaseModify(PurchaseModifyVO vo) {
        return purchaseModifyApplyService.insertPurchaseModify(vo);
    }
}
