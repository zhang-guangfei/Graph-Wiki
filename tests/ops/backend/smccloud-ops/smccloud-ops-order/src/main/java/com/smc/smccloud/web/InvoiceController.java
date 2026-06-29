package com.smc.smccloud.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.Purchase.OpsPurchaseInvoiceDO;
import com.smc.smccloud.model.invoice.*;
import com.smc.smccloud.service.ImpInvoiceService;
import com.smc.smccloud.service.PoInvoiceService;
import org.assertj.core.util.Arrays;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 导入发票数据
 *
 * @author wsf
 * @version 1.0
 * @date 2021/12/7 10:53
 */
@RestController
@RequestMapping("/order/invoice")
@CrossOrigin
public class InvoiceController {
    @Resource
    private ImpInvoiceService impInvoiceService;

    @Resource
    private PoInvoiceService poInvoiceService;


    /**
     * 导入备库型号清单设置
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importinvoiceData", method = RequestMethod.POST)
    public ResultVo<String> impInvoiceData(@RequestBody MultipartFile file) {
        return impInvoiceService.importJPShippingFile(file);
    }

    @RequestMapping(value = "/listImpInvoiceMaster", method = RequestMethod.POST)
    public ResultVo<PageInfo<ImpInvoiceMasterDO>> listImpInvoiceMaster(@RequestBody ImpInvoiceMasterRequest request) {
        return impInvoiceService.listImpInvoiceMaster(request);
    }

//    @RequestMapping(value = "/listImpInvoiceDetail", method = RequestMethod.POST)
//    public ResultVo<PageInfo<ImpInvoiceMasterDO>>  listImpInvoiceDetail(@RequestBody ImpInvoiceMasterRequest request) {
//        return impInvoiceService.listImpInvoiceMaster(request);
//    }

    @RequestMapping(value = "/cancelImpInvoiceDataById", method = RequestMethod.GET)
    public ResultVo<String> cancelImpInvoiceDataById(@RequestParam Integer invoiceId) {
        return impInvoiceService.cancelImpInvoiceDataById(invoiceId);
    }

    @RequestMapping(value = "/updateImpInvoicePreArriveDate", method = RequestMethod.POST)
    public ResultVo<String> updateImpInvoicePreArriveDate(@RequestBody ImpInvoiceArriveDateRequest request) {
        return impInvoiceService.updateImpInvoicePreArriveDate(request);
    }

    @RequestMapping(value = "/listImpInvoiceDetailByInvoiceId", method = RequestMethod.POST)
    public ResultVo<PageInfo<ImpInvoiceDetailDO>> listImpInvoiceDetailByInvoiceId(@RequestBody ImpInvoiceDetailRequest request) {
        return impInvoiceService.listImpInvoiceDetailByInvoiceId(request);
    }

    @RequestMapping(value = "/listImpInvoiceDetailPackByInvoiceId", method = RequestMethod.POST)
    public ResultVo<PageInfo<ImpInvoiceDetailPackDO>> listImpInvoiceDetailPackByInvoiceId(@RequestBody ImpInvoiceDetailRequest request) {
        return impInvoiceService.listImpInvoiceDetailPackByInvoiceId(request);
    }

    /**
     * 发票明细数据与发票分包数据差异对比
     *
     * @param request
     * @return
     */
//    @RequestMapping(value = "/listImpInvoiceDetailDiffByInvoiceId", method = RequestMethod.POST)
//    public ResultVo<PageInfo<ImpInvoiceDetailDiffVO>> listImpInvoiceDetailDiffByInvoiceId(@RequestBody ImpInvoiceDetailDiffRequest request){
//        return impInvoiceService.listImpInvoiceDetailDiffByInvoiceId(request);
//    }
    @RequestMapping(value = "/listImpInvoiceErrorByInvoiceId", method = RequestMethod.POST)
    public ResultVo<PageInfo<ImpInvoiceErrorDO>> listImpInvoiceErrorByInvoiceId(@RequestBody ImpInvoiceErrorRequest request) {
        return impInvoiceService.listImpInvoiceErrorByInvoiceId(request);
    }

    @RequestMapping(value = "/updateImpInvoiceDetail", method = RequestMethod.POST)
    public ResultVo<String> updateImpInvoiceDetail(@RequestBody ImpInvoiceDetailVO detailDO) {
        return impInvoiceService.updateImpInvoiceDetail(detailDO);
    }

    @RequestMapping(value = "/updateImpInvoiceDetailPack", method = RequestMethod.POST)
    public ResultVo<String> updateImpInvoiceDetailPack(@RequestBody ImpInvoiceDetailPackDTO detailDO) {
        return impInvoiceService.updateImpInvoiceDetailPack(detailDO);
    }

    @RequestMapping(value = "/delImpInvoiceDetail", method = RequestMethod.GET)
    public ResultVo<String> delImpInvoiceDetail(@RequestParam Integer invoiceId, Integer detailId) {
        return impInvoiceService.delImpInvoiceDetail(invoiceId, detailId);
    }

    @RequestMapping(value = "/delImpInvoiceDetailPack", method = RequestMethod.GET)
    public ResultVo<String> delImpInvoiceDetailPack(@RequestParam Integer invoiceId, Integer detailId) {
        return impInvoiceService.delImpInvoiceDetailPack(invoiceId, detailId);
    }

    /**
     * 发票入库
     *
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/ImpInvoiceDetailToImpData", method = RequestMethod.GET)
    public ResultVo<String> ImpInvoiceDetailToImpData(@RequestParam Integer invoiceId) {
        return impInvoiceService.confirmPOInvoice(invoiceId);
    }

    /**
     * 发票入库
     *
     * @param invoiceIds
     * @return
     */
    @RequestMapping(value = "/confirmPOInvoices", method = RequestMethod.POST)
    public ResultVo<String> confirmPOInvoices(@RequestBody List<Integer> invoiceIds) {
        return impInvoiceService.confirmPOInvoices(invoiceIds);
    }

    /**
     * 查询入库发票数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listPoinvoice", method = RequestMethod.POST)
    public ResultVo<PageInfo<OpsPoInvoiceVO>> listPoinvoice(@RequestBody PoInvoiceMasterRequest request) {
        return poInvoiceService.listPoInvoice(request);
    }

    /**
     * 查询入库发票明细数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listPoInvoiceDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<PoInvoiceDetailVO>> listPoInvoiceDetail(@RequestBody PoInvoiceDetailRequest request) {
        return poInvoiceService.listPoInvoiceDetail(request);
    }

    /**
     * 更新入库发票明细数据
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updatePoinvoice", method = RequestMethod.POST)
    public ResultVo<String> updatePoinvoice(@RequestBody OpsPoInvoiceVO vo) {
        return poInvoiceService.updatePoinvoice(vo);
    }

    /**
     * 导入成本系统
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/exportDataToCost", method = RequestMethod.POST)
    public ResultVo<String> exportDataToCost(@RequestBody PoInvoiceToCostDTO dto) {
        return poInvoiceService.exportDataToCost(dto);
    }

    /**
     * 导出入库发票数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/exportPoinvoice", method = RequestMethod.POST)
    public void exportPoinvoice(@RequestBody PoInvoiceMasterRequest request) {
        poInvoiceService.exportPoinvoice(request);
    }

    /**
     * 导出入库发票明细数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/exportPoInvoiceDetail", method = RequestMethod.POST)
    public void exportPoInvoiceDetail(@RequestBody PoInvoiceDetailRequest request) {
        poInvoiceService.exportPoInvoiceDetail(request);
    }

    /**
     * 新增或修改发票主数据
     *
     * @param invoiceMasterDO
     * @return
     */
    @RequestMapping(value = "/addInvoiceMasterData", method = RequestMethod.POST)
    public ResultVo<String> addInvoiceMasterData(@RequestBody ImpInvoiceMasterDO invoiceMasterDO) {
        return impInvoiceService.addInvoiceMasterData(invoiceMasterDO);
    }

    /**
     * 新增发票明细
     *
     * @param invoiceDetailDO
     * @return
     */
    @RequestMapping(value = "/addInvoiceDetailData", method = RequestMethod.POST)
    public ResultVo<String> addInvoiceDetailData(@RequestBody ImpInvoiceDetailDO invoiceDetailDO) {
        return impInvoiceService.addInvoiceDetailData(invoiceDetailDO);
    }

    /**
     * 新增发票分包
     *
     * @param invoiceDetailDO
     * @return
     */
    @RequestMapping(value = "/addInvoiceDetailPackData", method = RequestMethod.POST)
    public ResultVo<String> addInvoiceDetailPackData(@RequestBody ImpInvoiceDetailPackDO invoiceDetailDO) {
        return impInvoiceService.addInvoiceDetailPackData(invoiceDetailDO);
    }

    /**
     * 发票转成本结算
     * 按imp_invoice_master.id 的待转成本的发票,生成到ops_invoice和ops_invoice_detail中
     * 处理国内集团内先发货后开票,开票的数据转入做成本
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/impInvoiceToCost", method = RequestMethod.POST)
    public ResultVo<String> impInvoiceToCost(@RequestBody ImpInvoiceToCostRequest request) {
        return impInvoiceService.impInvoiceToCost(request);
    }

    @RequestMapping(value = "/updateMasterNoStorage", method = RequestMethod.POST)
    public ResultVo<String> updateMasterNoStorage( @RequestParam("ids") List<Long> ids) {
        ImpInvoiceMasterRequest request =new ImpInvoiceMasterRequest();
        request.setIds(ids);
        return impInvoiceService.updateMasterNoStorage(request);
    }


    /**
     * 发票转成本结算
     *
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/updToInvoicedetailPack", method = RequestMethod.POST)
    public ResultVo<String> updToInvoicedetailPack(@RequestParam Long invoiceId) {
        return impInvoiceService.copyToInvoicedetailPack(invoiceId);
    }

    /**
     * 查询入库数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listImpdata", method = RequestMethod.POST)
    public ResultVo<PageInfo<ImpDataVO>> Impdata(@RequestBody ImpdataRequest request) {
        return impInvoiceService.listImpdata(request);
    }

    /**
     * 查询入库中数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listNoImpInvoiceDetailPack", method = RequestMethod.POST)
    public ResultVo<PageInfo<ImpInvoiceDetailPackVO>> listNoImpInvoiceDetailPack(@RequestBody ImpdataRequest request) {
        return impInvoiceService.listNoImpInvoiceDetailPack(request);
    }

    /**
     * 查询入库中数据
     * 前端excel导出
     * @param request
     * @return
     */
    @RequestMapping(value = "/listNoImpInvoiceDetailPackExcel", method = RequestMethod.POST)
    public ResultVo<List<ImpInvoiceDetailPackVO>> listNoImpInvoiceDetailPackExcel(@RequestBody ImpdataRequest request) {
        return ResultVo.success(impInvoiceService.listNoImpInvoiceDetailPackExcel(request));
    }

    /**
     * 手动入库入库失败的数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/handimpInvoiceDetailPack", method = RequestMethod.POST)
    public ResultVo<String> handimpInvoiceDetailPack(@RequestBody ImpdataRequest request) {
        // return impInvoiceService.handimpInvoiceDetailPack(request);
        return ResultVo.failure("该功能已取消");
    }

    /**
     * 明细项完成录入
     *
     * @return
     */
    @RequestMapping(value = "/finishImpInvoiceDeatailAdd", method = RequestMethod.POST)
    public ResultVo<String> finishImpInvoiceDeatailAdd(@RequestParam Integer invoiceId) {
        return impInvoiceService.finishImpInvoiceDeatailAdd(invoiceId);
    }

    /**
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/checkImpInvoiceError", method = RequestMethod.POST)
    public ResultVo<Integer> checkImpInvoiceError(@RequestParam(value = "invoiceId") Long invoiceId) {
        return impInvoiceService.checkImpInvoiceError(invoiceId);
    }

    @RequestMapping(value = "/updateImpInvoiceIgnoreError", method = RequestMethod.POST)
    public ResultVo<String> updateImpInvoiceIgnoreError(@RequestBody ImpInvoiceErrorDTO dto) {
        return impInvoiceService.updateImpInvoiceIgnoreError(dto);
    }

    /**
     * 导出发票主要数据
     */
    @RequestMapping(value = "/exportImpInvoiceMaster", method = RequestMethod.POST)
    public void exportImpInvoiceMaster(@RequestBody ImpInvoiceMasterRequest dto) {
        impInvoiceService.exportImpInvoiceMaster(dto);
    }

    /**
     * 导出发票明细数据
     */
    @RequestMapping(value = "/exportImpInvoiceDetail", method = RequestMethod.POST)
    public void exportImpInvoiceDetail(@RequestBody ImpInvoiceDetailRequest dto) {
        impInvoiceService.exportImpInvoiceDetail(dto);
    }

    /**
     * 导出发票分包数据
     */
    @RequestMapping(value = "/exportImpInvoiceDetailPack", method = RequestMethod.POST)
    public void exportImpInvoiceDetailPack(@RequestBody ImpInvoiceDetailRequest dto) {
        impInvoiceService.exportImpInvoiceDetailPack(dto);
    }

    /**
     * 导出发票差异数据
     */
    @RequestMapping(value = "/exportImpInvoiceError", method = RequestMethod.POST)
    public void exportImpInvoiceError(@RequestBody ImpInvoiceErrorRequest dto) {
        impInvoiceService.exportImpInvoiceError(dto);
    }

    /**
     * 更新采购接单型号
     *
     * @param orderNo    完整订单型号
     * @param newModelNo
     */
    @RequestMapping(value = "/changePoModelNo", method = RequestMethod.POST)
    public void changePoModelNo(@RequestParam(value = "orderNo") String orderNo,
                                @RequestParam(value = "newModelNo") String newModelNo) {
        impInvoiceService.changePoModelNo(orderNo, null, newModelNo);
    }

    /**
     * 获取采购数据
     *
     * @param poNo
     * @return
     */
    @RequestMapping(value = "/getopspurchaseInvoice", method = RequestMethod.GET)
    public ResultVo<OpsPurchaseInvoiceDO> getopspurchaseInvoice(@RequestParam(value = "poNo") String poNo, @RequestParam(value = "poItemNo") String poItemNo) {
        return impInvoiceService.getopspurchaseInvoice(poNo, poItemNo);
    }

    /**
     * 查询汇率，按月份
     *
     * @param currency
     * @param monthDate
     * @return
     */
    @RequestMapping(value = "/getExchangeRate", method = RequestMethod.GET)
    public ResultVo<BigDecimal> getExchangeRate(@RequestParam(value = "currency") String currency, @RequestParam(value = "monthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date monthDate) {
        return poInvoiceService.getExchangeRate(currency, monthDate);
    }

    /**
     * 更新发票明细的人民币金额
     *
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/updatePODetailRMBAmount", method = RequestMethod.GET)
    public ResultVo<String> updatePODetailRMBAmount(@RequestParam(value = "invoiceId") Long invoiceId) {
        return poInvoiceService.updatePODetailRMBAmount(invoiceId);
    }

    /**
     * 获取采购数据
     *
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/getPoInvoiceDetailAmount", method = RequestMethod.GET)
    public ResultVo<OpsPoInvoiceDO> getPoInvoiceDetailAmount(@RequestParam(value = "invoiceId") Long invoiceId) {
        return poInvoiceService.getPoInvoiceDetailAmount(invoiceId);
    }

    @RequestMapping(value = "/confirmPOInvoiceDetail", method = RequestMethod.GET)
    public ResultVo<String> confirmPOInvoiceDetail(@RequestParam(value = "invoiceId") Long invoiceId) {
        return impInvoiceService.confirmPOInvoiceDetail(invoiceId, 1);
    }

    /**
     * 清除关务明细
     */
    @RequestMapping(value = "/clearPOInvoiceDetail", method = RequestMethod.GET)
    public ResultVo<String> clearPOInvoiceDetail(@RequestParam(value = "invoiceId") Long invoiceId) {
        return impInvoiceService.clearPOInvoiceDetail(invoiceId);
    }

    /**
     * 获取发票入库总金额
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getImpInvoiceAmountTotal", method = RequestMethod.POST)
    public ResultVo<BigDecimal> getImpInvoiceAmountTotal(@RequestBody ImpInvoiceMasterRequest request) {
        return impInvoiceService.getImpInvoiceAmountTotal(request);
    }

    /**
     * 导出导出三国发票明细
     */
    @RequestMapping(value = "/exportOtherInvoiceData", method = RequestMethod.POST)
    public void exportOtherInvoiceData(@RequestBody PoInvoiceMasterRequest request) {
        poInvoiceService.exportOtherInvoiceData(request);
    }

    /**
     * 导出增值税发票月次统计
     */
    @RequestMapping(value = "/exportValueImpinvoice", method = RequestMethod.POST)
    public void exportValueImpinvoice(@RequestBody ImpInvoiceMasterRequest request) {
        poInvoiceService.exportValueImpinvoice(request);
    }

    /**
     * 获取采购数据
     *
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/updImpShipAmount", method = RequestMethod.GET)
    public ResultVo<String> updImpShipAmount(@RequestParam(value = "invoiceId") Long invoiceId) {
        return poInvoiceService.updImpShipAmount(invoiceId);
    }

    @RequestMapping(value = "/redoCostInvoice", method = RequestMethod.GET)
    public ResultVo<String> redoCostInvoice(@RequestParam(value = "invoiceId") String invoiceId) {
        return poInvoiceService.redoCostInvoice(invoiceId);
    }

    /**
     * 获取原大发票号
     *
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "/listOverseaInvoiceData", method = RequestMethod.GET)
    public ResultVo<List<PoInvoiceDetailDO>> listOverseaInvoiceData(@RequestParam(value = "invoiceId") Long invoiceId) {
        return impInvoiceService.listOverseaInvoiceData(invoiceId);
    }

    @RequestMapping(value = "/UpdateDeleteDetailPack", method = RequestMethod.POST)
    public ResultVo<String> UpdateDeleteDetailPack(@RequestBody PoInvoiceRequest request) {
        return impInvoiceService.updateDeleteDetailPack(request.getPoInvoiceDTOS(), request.getDoType(),request.getEndUser());

    }

}
