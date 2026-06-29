package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.invoice.*;
import com.smc.smccloud.service.ImpInvoiceService;
import com.smc.smccloud.service.InvoiceServiceFeignApi;
import com.smc.smccloud.service.PoInvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-03 13:57
 * Description:
 */
@Slf4j
@RestController
public class InvoiceServiceFeignClient implements InvoiceServiceFeignApi {

    @Resource
    private ImpInvoiceService impInvoiceService;

    @Resource
    private PoInvoiceService poInvoiceService;

    @Override
    public ResultVo<String> impImportInvoiceInfo(String plantMark, String invNo, Date startTime, Date endTime) {
        try {
            return impInvoiceService.importGWInvoice(plantMark, invNo, startTime, endTime);
        } catch (Exception e) {
            log.error("导入关务进口发票数据失败: {}", e.getMessage(), e);
            return ResultVo.failure(e.getMessage());
        }
    }

    @Override
    public ResultVo<String> addImpInvoiceData(List<ImportOrderInfoVO> importOrderInfoVOs) {
        return impInvoiceService.addImpInvoiceDataPack(importOrderInfoVOs);
    }

    @Override
    public ResultVo<String> importJPShippingFile(MultipartFile file) {
        return impInvoiceService.importJPShippingFile(file);
    }

    @Override
    public ResultVo<String> updateOpsInvoice(Integer invoiceId) {
        return poInvoiceService.updateOpsInvoice(invoiceId);
    }

    /*
      入库发票收货确认
     */
    @Override
    public ResultVo<String> receiveGoods(ImpInvoiceReceiveDTO dto) {

        //把发票从预计到货改成已收货待入库
        //调用关务系统回调接口更新已收货

        //触发自动入库调用入库方法--后期加
        return impInvoiceService.receiveGoods(dto);
    }

    /**
     * 北京制造发货录入发票分包数据
     *
     * @return
     */
    @Override
    public ResultVo<String> synImpCNInvoicePack(String optTime, Integer type) {
        return impInvoiceService.syncImpCNInvoicePack(optTime, type);
    }

    /**
     * 北京制造发票数据录入发票分包数据
     *
     * @return
     */
    @Override
    public ResultVo<String> synVExportImpCNInvoicePack(String optTime, Integer type) {
        return impInvoiceService.syncVExportImpCNInvoicePack(optTime, type);
    }

    /**
     * 广州制造录入发票分包数据
     *
     * @return
     */
    @Override
    public ResultVo<String> synImpGZInvoicePack(String optDate) {
        return impInvoiceService.syncImpGZInvoicePack(optDate);
    }

    /**
     * 广州制造录入增值发票发票数据
     *
     * @return
     */
    @Override
    public ResultVo<String> syncGZSalesinvoiceToIMP(String optDate) {
        return impInvoiceService.syncGZSalesinvoiceToIMP(optDate);
    }

    @Override
    public ResultVo<String> impInvoiceStatusFrmCMS() {
        return impInvoiceService.impInvoiceStatusFrmCMS();
    }

    @Override
    public ResultVo<List<InvoiceBalaceDTO>> getSalesInvoiceBalaceData(Date fromDate, Date toDate) {
        return impInvoiceService.getSalesInvoiceBalaceData(fromDate, toDate);
    }

    @Override
    public ResultVo<String> synAdjustDetailDifferentAmt() {
        return poInvoiceService.synAdjustDetailDifferentAmt();
    }

    @Override
    public ResultVo<String> synToImpData() {
        return impInvoiceService.syncToImpData();
    }

    @Override
    public ResultVo<String> autoDataToCost() {
        return poInvoiceService.autoDataToCost();
    }

    @Override
    public ResultVo<String> autoGPconfirmPOInvoice(String optDate) {
        return impInvoiceService.autoGPconfirmPOInvoice(optDate);
    }

    @Override
    public ResultVo<String> autoConfirmPODetail() {
        return impInvoiceService.autoConfirmPODetail();
    }

    @Override
    public ResultVo<String> updatePoInvoiceDetailCost(PoInvoiceDetailVO vo) {
        return poInvoiceService.updatePoInvoiceDetailCost(vo);
    }

    @Override
    public ResultVo<BigDecimal> getExchangeRateByinvoiceId(long invoiceId) {
        return poInvoiceService.getExchangeRateByinvoiceId(invoiceId);
    }

    @Override
    public  ResultVo<OpsPoInvoiceVO>  getOpsPoInvoice(long invoiceId) {
        return poInvoiceService.getOpsPoInvoice(invoiceId);
    }

    @Override
    public ResultVo<String> finishConfirmImpInvoice(ImpInvoiceProcessDTO dto) {
        return impInvoiceService.finishConfirmImpInvoice(dto);
    }

    @Override
    public void monthlyInventorySummary() {
        poInvoiceService.monthlyInventorySummary();
    }


}
