package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.invoice.*;
import com.smc.smccloud.service.hystrix.InvoiceServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-03 13:43   url = "http://192.168.31.182:8100"
 * Description:
 */
@FeignClient(name = "order-service",
        contextId = "order-invoice",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = InvoiceServiceFeignHystrix.class)
public interface InvoiceServiceFeignApi {

    /**
     * 导入进口发票数据
     * @param plantMark 厂别(必填, 默认: AM)
     * @param invNo 发票号 (如: YCS3352111)
     * @param startTime 起始时间 (yyyy-MM-dd HH:mm:ss)
     * @param endTime 结束时间 (yyyy-MM-dd HH:mm:ss)
     * @return 导入结果
     */
    @RequestMapping(value = "/order/invoice/importInvoiceInfo", method = RequestMethod.GET)
    ResultVo<String> impImportInvoiceInfo(@RequestParam(value = "plantMark", defaultValue = "AM") String plantMark,
                                       @RequestParam(value = "invNo", required = false) String invNo,
                                       @RequestParam(value = "startTime") @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss") Date startTime,
                                       @RequestParam(value = "endTime") @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss") Date endTime);

    /**
     * 导入分包数据
     * @param importOrderInfoVOs
     * @return
     */
    @RequestMapping(value = "/order/invoice/addImpInvoiceData", method = RequestMethod.POST)
    ResultVo<String> addImpInvoiceData(@RequestBody List< ImportOrderInfoVO> importOrderInfoVOs);

    /**
     * 导入日本分包文件
     * @param file
     * @return
     */
    @PostMapping(value = "/order/invoice/importJPShippingFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVo<String> importJPShippingFile(@RequestPart("file") MultipartFile file);



    @RequestMapping(value = "/order/invoice/updateOpsInvoice", method = RequestMethod.POST)
    ResultVo<String> updateOpsInvoice(@RequestParam(value = "invoiceId") Integer invoiceId);

    /**
     * 收货确认
     * @param dto
     * @return
     */
    @RequestMapping(value = "/order/invoice/receiveGoods", method = RequestMethod.POST)
    ResultVo<String> receiveGoods(@RequestBody ImpInvoiceReceiveDTO dto);

    /**
     * 北京制造发货录入发票分包数据
     *
     * @return
     */
    @RequestMapping(value = "/order/invoice/synImpCNInvoicePack", method = RequestMethod.GET)
    ResultVo<String> synImpCNInvoicePack(@RequestParam(value = "optTime") String optTime,@RequestParam(value = "type") Integer type);

    /**
     * 北京制造发票数据录入发票分包数据
     *
     * @return
     */
    @RequestMapping(value = "/order/invoice/synVExportImpCNInvoicePack", method = RequestMethod.GET)
    ResultVo<String> synVExportImpCNInvoicePack(@RequestParam(value = "optTime") String optTime,@RequestParam(value = "type") Integer type);

    /**
     * 广州制造录入发票分包数据
     *
     * @return
     */
    @RequestMapping(value = "/order/invoice/synImpGZInvoicePack", method = RequestMethod.GET)
    ResultVo<String> synImpGZInvoicePack(@RequestParam(value = "optDate") String optDate);

    /**
     * 广州制造录入增值税发票数据
     *
     * @return
     */
    @RequestMapping(value = "/order/invoice/syncGZSalesinvoiceToIMP", method = RequestMethod.GET)
    ResultVo<String> syncGZSalesinvoiceToIMP(@RequestParam(value = "optDate") String optDate);

    @GetMapping("/order/invoice/impInvoiceStatusFrmCMS")
    ResultVo<String> impInvoiceStatusFrmCMS();

    @RequestMapping(value = "/order/invoice/getSalesInvoiceBalaceData", method = RequestMethod.GET)
    ResultVo<List<InvoiceBalaceDTO>> getSalesInvoiceBalaceData(@RequestParam("fromDate") Date fromDate,@RequestParam("toDate") Date toDate);

    /**
     * 定时调整明细金额
     * @return
     */
    @RequestMapping(value = "/order/invoice/synAdjustDetailDifferentAmt", method = RequestMethod.GET)
    ResultVo<String> synAdjustDetailDifferentAmt();

    /**
     * 定时发票入库
     * @return
     */
    @RequestMapping(value = "/order/invoice/synToImpData", method = RequestMethod.GET)
    ResultVo<String> synToImpData();

    /**
     * 定时发票入库
     * @return
     */
    @RequestMapping(value = "/order/invoice/autoDataToCost", method = RequestMethod.GET)
    ResultVo<String> autoDataToCost();

    /**
     * 定时制造发票入库
     * @return
     */
    @RequestMapping(value = "/order/invoice/autoGPconfirmPOInvoice", method = RequestMethod.GET)
    ResultVo<String> autoGPconfirmPOInvoice(@RequestParam(value = "optDate") String optDate);

    /**
     * 定时导入PO明细
     * @return
     */
    @RequestMapping(value = "/order/invoice/autoConfirmPODetail", method = RequestMethod.GET)
    ResultVo<String> autoConfirmPODetail();

    /**
     * 手动更改修改成本明细
     * @param vo
     * @return
     */
    @RequestMapping(value = "/order/invoice/updatePoInvoiceDetailCost", method = RequestMethod.POST)
    ResultVo<String> updatePoInvoiceDetailCost(@RequestBody PoInvoiceDetailVO vo);

    /**
     * 根据invoiceid查出对应的汇率
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/order/invoice/getExchangeRateByinvoiceId", method = RequestMethod.GET)
    ResultVo<BigDecimal> getExchangeRateByinvoiceId(@RequestParam(value = "invoiceId") long invoiceId);

    @RequestMapping(value = "/order/invoice/getOpsPoInvoice", method = RequestMethod.GET)
    ResultVo<OpsPoInvoiceVO> getOpsPoInvoice(@RequestParam(value = "invoiceId") long invoiceId);

    /**
     * 完成发票入库后，检查状态是否更新和更新货期状态
     * @return
     */
    @RequestMapping(value = "/order/invoice/finishConfirmImpInvoice", method = RequestMethod.POST)
    ResultVo<String> finishConfirmImpInvoice(@RequestBody ImpInvoiceProcessDTO dto);

    @RequestMapping(value = "/order/invoice/monthlyInventorySummary", method = RequestMethod.GET)
    void monthlyInventorySummary();

}
