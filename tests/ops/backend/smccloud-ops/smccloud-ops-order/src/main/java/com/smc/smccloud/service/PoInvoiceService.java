package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.POOrderNODTO;
import com.smc.smccloud.model.adapter.order.POOrderNOVO;
import com.smc.smccloud.model.adapter.order.SplitOrderVO;
import com.smc.smccloud.model.invoice.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/12/22 16:35
 */
public interface PoInvoiceService {

    /**
     * 查询入库发票数据主表
     * @param request
     * @return
     */
    ResultVo<PageInfo<OpsPoInvoiceVO>> listPoInvoice(PoInvoiceMasterRequest request);

    /**
     * 查询入库后发票明细数据
     * @param request
     * @return
     */
    ResultVo<PageInfo<PoInvoiceDetailVO>> listPoInvoiceDetail(PoInvoiceDetailRequest request);

    //    <!--add by WuWeiDong 20230109 bug 9276 -->
    /**
     * 手动更改修改成本明细
     * @param poInvoiceDetailVO
     * @return
     */
    ResultVo<String> updatePoInvoiceDetailCost(PoInvoiceDetailVO poInvoiceDetailVO);

    ResultVo<String> updatePoInvoiceDetailFee(PoInvoiceDetailVO poInvoiceDetailVO);
    boolean updatePoInvoiceMasterFee(OpsPoInvoiceDO opsPoInvoiceDO);
    /**
     * 取某个发票的汇率
     * @param invoiceId
     * @return
     */
    ResultVo<BigDecimal> getExchangeRateByinvoiceId(long invoiceId) ;
    /**
     * 更新入库发票明细数据
     * @param vo
     * @return
     */
    ResultVo<String> updatePoinvoice(OpsPoInvoiceVO vo);

    /**
     * 导入成本系统
     * @param dto
     * @return
     */
    ResultVo<String> exportDataToCost(PoInvoiceToCostDTO dto);

    ResultVo<String> doDataToCost(Long invoiceId, Date costDate);

    ResultVo<String> updateDataToCost(OpsPoInvoiceDO invoiceMasterDO,List<PoInvoiceDetailDO> detaillist, Date costDate ) ;

    /**
     * 导出入库发票数据
     * @param request
     * @return
     */
    void exportPoinvoice(PoInvoiceMasterRequest request);

    /**
     * 导出入库发票明细数据
     * @param request
     * @return
     */
    void exportPoInvoiceDetail(PoInvoiceDetailRequest request);

    /**
     * impdata录入ops_impdata，该状态为2
     * @param invoiceId
     * @return
     */
    ResultVo<String> updateOpsInvoice(Integer invoiceId);

    boolean updateImpInvoiceDetailPack(Integer status,Long invoiceId);

    /**
     * 查询汇率，按月份
     * @param currency
     * @param monthDate
     * @return
     */
    ResultVo<BigDecimal> getExchangeRate(String currency, Date monthDate);

    ResultVo<BigDecimal> getComExchangeRate(String currencyId, Date monthDate);

    /**
     * 更新发票明细的人民币金额
     * @param invoiceId
     * @return
     */
    ResultVo<String> updatePODetailRMBAmount(Long invoiceId);

    /**
     * 查询明细总金额
     * @param invoiceId
     * @return
     */
    ResultVo<OpsPoInvoiceDO> getPoInvoiceDetailAmount(Long invoiceId);

    /**
     * 调整明细金额
     * @param toUpdateDO
     * @return
     */
    ResultVo<String> adjustDetailDifferentAmt(OpsPoInvoiceDO toUpdateDO,Integer type);

    /**
     * 异步调整明细金额
     * @param InvoiceId
     */
    ResultVo<String> AsyncAdjustDetailDifferentAmt(Long InvoiceId);

    /**
     * 定时调整明细金额
     * @return
     */
    ResultVo<String> synAdjustDetailDifferentAmt();

    /**
     * 导出导出三国发票明细
     * @param request
     * @return
     */
    void exportOtherInvoiceData(PoInvoiceMasterRequest request);

    List<InvoiceNoAndShipDateVO> listOPSTExportIvoiceNoTest(String optTime);

    List<InvoiceNoAndShipDateVO> listOPSTExportIvoiceNo(String optTime);

    List<OPSTExportRequestToSalesDO> listExportRequestToSales(InvoiceNoAndShipDateVO vo);

    List<OPSTExportRequestToSalesDO> listExportRequestToSalesTest(InvoiceNoAndShipDateVO vo);

    List<OPSVExportInvoicePriceToSalesDO> listOPSVExportIvoice(String optTime);

    List<OPSVExportInvoicePriceToSalesDO> listOPSVExportIvoiceTest(String optTime);

    /**
     * 自动成本结算
     * @return
     */
    ResultVo<String> autoDataToCost();

    /**
     * 导出增值税发票月次统计
     *
     */
    void exportValueImpinvoice(ImpInvoiceMasterRequest request);

    /**
     * 更新发货金额
     * @return
     */
    ResultVo<String> updImpShipAmount(Long invoiceId);

    /**
     * 重新结转
     * @param invoiceId
     * @return
     */
    ResultVo<String> redoCostInvoice(String invoiceId);

//<!--add by WuWeiDong 20221104 task 2089 -->
    /**
     * 关务系统查询采购订单接口
     * @param orderNos poNO,lineItem
     * @return 采购单号，采购项，型号，数量，剩余入库数量，状态，供应商，收货仓库，采购日期
     */
    ResultVo<List<POOrderNOVO>>listPOOrder(List<POOrderNODTO> orderNos);

    ResultVo<OpsPoInvoiceVO>  getOpsPoInvoice(Long invoiceID);


    /**
     * 分公司出入账票月决算
     */
    void monthlyInventorySummary();
}
