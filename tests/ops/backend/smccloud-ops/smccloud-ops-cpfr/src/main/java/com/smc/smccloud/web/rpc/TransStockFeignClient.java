package com.smc.smccloud.web.rpc;


import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.trans.TransOrderCancelDTO;
import com.smc.smccloud.model.trans.TransOrderRequest;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.service.TransStockFeignApi;
import com.smc.smccloud.service.TransStockService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TransStockFeignClient implements TransStockFeignApi {

    @Resource
    private TransStockService transStockService;

    @Override
    public ResultVo<String> transStock(List<TransOrderVO> voList) {
        return transStockService.transStock(voList);
    }


    @Override
    public ResultVo<String> transStockAll(List<TransOrderVO> voList) {
        return transStockService.transStockAll(voList);
    }
    @Override
    public ResultVo<String> createTransOrderForMove(List<TransOrderDtoForMove> voList) {
        return transStockService.createTransOrderForMove(voList);
    }
    @Override
    public ResultVo<PageInfo<TransOrderVO>> findTransOrder(TransOrderRequest request) {
        return transStockService.findTransOrder(request);
    }

    @Override
    public ResultVo<String> cancelTransOrder(TransOrderCancelDTO dto) {
        return transStockService.cancelTransOrder(dto);
    }

    @Override
    public void exportTransData(TransOrderRequest request) {
        transStockService.exportTransData(request);
    }
}
