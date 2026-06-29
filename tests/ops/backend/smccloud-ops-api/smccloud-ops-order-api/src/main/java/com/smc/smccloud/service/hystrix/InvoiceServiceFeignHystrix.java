package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.invoice.*;
import com.smc.smccloud.service.InvoiceServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-03 13:43
 * Description:
 */
@Component
public class InvoiceServiceFeignHystrix implements FallbackFactory<InvoiceServiceFeignApi> {
    @Override
    public InvoiceServiceFeignApi create(Throwable cause) {
        return new InvoiceServiceFeignApi() {

            @Override
            public ResultVo<String> impImportInvoiceInfo(String plantMark, String invNo, Date startTime, Date endTime) {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<String> addImpInvoiceData(List<ImportOrderInfoVO> importOrderInfoVOs) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> importJPShippingFile(MultipartFile file) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updateOpsInvoice(Integer invoiceId) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> receiveGoods(ImpInvoiceReceiveDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> synImpCNInvoicePack(String optTime,Integer type) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> synVExportImpCNInvoicePack(String optTime,Integer type){
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> synImpGZInvoicePack(String optDate) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> syncGZSalesinvoiceToIMP(String optDate) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> synAdjustDetailDifferentAmt() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> impInvoiceStatusFrmCMS() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<InvoiceBalaceDTO>> getSalesInvoiceBalaceData(Date fromDate, Date toDate) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> synToImpData() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoDataToCost() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoGPconfirmPOInvoice(String optDate) {
                return null;
            }

            @Override
            public ResultVo<String> autoConfirmPODetail(){return ResultVo.failure("服务降级");}

            @Override
            public ResultVo<String> updatePoInvoiceDetailCost(PoInvoiceDetailVO vo){return ResultVo.failure("服务降级");}
            @Override
            public ResultVo<BigDecimal> getExchangeRateByinvoiceId( long invoiceId){return ResultVo.failure("服务降级");};
            @Override
            public ResultVo<OpsPoInvoiceVO> getOpsPoInvoice( long invoiceId){return ResultVo.failure("服务降级");}

            @Override
            public ResultVo<String> finishConfirmImpInvoice(ImpInvoiceProcessDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public void monthlyInventorySummary() {

            }
        };
    }
}
