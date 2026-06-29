package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adjust.StockAdjustApplyDTO;
import com.smc.smccloud.model.adjust.StockAdjustDTO;
import com.smc.smccloud.model.adjust.StockAdjustRequest;
import com.smc.smccloud.model.adjust.StockAdjustVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StockAdjustService {

    /**
     * 查询入库调整信息
     * @return
     */
    ResultVo<PageInfo<StockAdjustVO>> listAdjustData(StockAdjustRequest request);

    /**
     * 新增或修改入库调整信息
     * @return
     */
    ResultVo<String> addAdjustData(StockAdjustApplyDTO dto);

    /**
     * 导出数据
     * @param request
     */
    void exportStockAdjustData(StockAdjustRequest request);

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    ResultVo<String> delAdjustData(Integer id);

    ResultVo<String> createInvoiceNo();

    ResultVo<StockAdjustDTO> getOrderInfoForImpAdjuest(String fullOrderNo);

    ResultVo<String> importStockAdjustData(MultipartFile file);

    ResultVo<String> determineStockAdjust(String invoiceNo);

    ResultVo<String> threeCProductAdjust();

    /**
     * 发票入库型号与入库型号不一致，做组换调整
     * @return
     */
    ResultVo<String> createStockAssembleFromInvoiceError();
}
