package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.borrowstock.BrwApplyDTO;
import com.smc.smccloud.service.BorrowStockFeignApi;
import com.smc.smccloud.service.BorrowStockService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BorrowStockFeignClient implements BorrowStockFeignApi {

    @Resource
    private BorrowStockService borrowStockService;

    @Override
    public ResultVo<String> createBorrowStockApply(BrwApplyDTO brwApplyDTO) {

        return borrowStockService.saveBrwStockApply(brwApplyDTO);
    }

}
