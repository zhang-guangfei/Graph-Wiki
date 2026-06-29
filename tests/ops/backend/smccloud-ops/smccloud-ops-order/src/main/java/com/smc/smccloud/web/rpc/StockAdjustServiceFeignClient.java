package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adjust.StockAdjustApplyDTO;
import com.smc.smccloud.model.adjust.StockAdjustDTO;
import com.smc.smccloud.model.adjust.StockAdjustRequest;
import com.smc.smccloud.model.adjust.StockAdjustVO;
import com.smc.smccloud.service.StockAdjustService;
import com.smc.smccloud.service.StockAdjustServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class StockAdjustServiceFeignClient implements StockAdjustServiceFeignApi {

    @Resource
    private StockAdjustService stockAdjustService;

    @Override
    public ResultVo<String> addAdjustData(StockAdjustApplyDTO dto) {
        return stockAdjustService.addAdjustData(dto);
    }

    @Override
    public ResultVo<PageInfo<StockAdjustVO>> listAdjustData(StockAdjustRequest request) {
        return stockAdjustService.listAdjustData(request);
    }

    @Override
    public void exportStockAdjustData(StockAdjustRequest request) {
        stockAdjustService.exportStockAdjustData(request);
    }

    @Override
    public ResultVo<String> delAdjustData(Integer id) {
        return stockAdjustService.delAdjustData(id);
    }

    @Override
    public ResultVo<String> createInvoiceNo() {
        return stockAdjustService.createInvoiceNo();
    }

    @Override
    public ResultVo<StockAdjustDTO> getOrderInfoForImpAdjuest(String fullOrderNo) {
        return stockAdjustService.getOrderInfoForImpAdjuest(fullOrderNo);
    }

    @Override
    public ResultVo<String> importStockAdjustData(MultipartFile file) {
        return stockAdjustService.importStockAdjustData(file);
    }

    @Override
    public ResultVo<String> determineStockAdjust(String invoiceNo) {
        return stockAdjustService.determineStockAdjust(invoiceNo);
    }

    @Override
    public ResultVo<String> threeCProductAdjust() {
        return stockAdjustService.threeCProductAdjust();
    }

    @Override
    public ResultVo<String> createStockAssembleFromInvoiceError() {
        return stockAdjustService.createStockAssembleFromInvoiceError();
    }
}
