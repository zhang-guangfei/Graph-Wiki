package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.Purchase.OpsPurchaseInvoiceDO;
import com.smc.smccloud.model.invoice.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-03 08:54
 * Description:
 */
public interface ImpInvoiceService {

    /**
     * 导入进口发票数据
     *
     * @param plantMark 厂别(必填)
     * @param invNo     发票号 (如: YCS3352111)
     * @param startTime 起始时间 (yyyy-MM-dd HH:mm:ss)
     * @param endTime   结束时间 (yyyy-MM-dd HH:mm:ss)
     * @return 导入结果
     */
    ResultVo<String> importGWInvoice(String plantMark, String invNo, Date startTime, Date endTime);


    /**
     * 导入进口发票数据
     *
     * @param plantMark 厂别(必填)
     * @param invNo     发票号 (如: YCS3352111)
     * @param startTime 起始时间 (yyyy-MM-dd HH:mm:ss)
     * @param endTime   结束时间 (yyyy-MM-dd HH:mm:ss)
     * @return 导入结果
     */
    ResultVo<String> importInvoiceDetailFromGW(String plantMark, String invNo, Date startTime, Date endTime);

//    /**
//     * 导入txt发票数据
//     * @param file
//     * @return
//     */
//    ResultVo<String> impInvoiceDataPack(MultipartFile file);

    /**
     * 查询发发票数据主表
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<ImpInvoiceMasterDO>> listImpInvoiceMaster(ImpInvoiceMasterRequest request);

    /**
     * 获取发票入库总金额
     *
     * @param request
     * @return
     */
    ResultVo<BigDecimal> getImpInvoiceAmountTotal(ImpInvoiceMasterRequest request);

    /**
     * 取消删除发票数据
     *
     * @param invoiceId
     * @return
     */
    ResultVo<String> cancelImpInvoiceDataById(Integer invoiceId);

    /**
     * 登记预计到达日期
     *
     * @param request
     * @return
     */
    ResultVo<String> updateImpInvoicePreArriveDate(ImpInvoiceArriveDateRequest request);

    /**
     * 发票明细清单 by invoiceId
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<ImpInvoiceDetailPackDO>> listImpInvoiceDetailPackByInvoiceId(ImpInvoiceDetailRequest request);

    /**
     * 发票明细清单 by invoiceId
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<ImpInvoiceDetailDO>> listImpInvoiceDetailByInvoiceId(ImpInvoiceDetailRequest request);

    /**
     * 发票明细差异对比
     * 明细与分包数据对比
     * @param request
     * @return
     */
//    ResultVo<PageInfo<ImpInvoiceDetailDiffVO>> listImpInvoiceDetailDiffByInvoiceId(ImpInvoiceDetailDiffRequest request) ;

    /**
     * @param request
     * @return
     */
    ResultVo<PageInfo<ImpInvoiceErrorDO>> listImpInvoiceErrorByInvoiceId(ImpInvoiceErrorRequest request);

    /**
     * 编辑子项
     *
     * @param detailDO
     * @return
     */
    ResultVo<String> updateImpInvoiceDetail(ImpInvoiceDetailVO detailDO);

    /**
     * 编辑分包子项
     *
     * @param detailDO
     * @return
     */
    ResultVo<String> updateImpInvoiceDetailPack(ImpInvoiceDetailPackDTO detailDO);

    /**
     * 取消子项
     *
     * @param invoiceId
     * @param detailId
     * @return
     */
    ResultVo<String> delImpInvoiceDetail(Integer invoiceId, Integer detailId);

    /**
     * 取消分包子项
     *
     * @param invoiceId
     * @param detailId
     * @return
     */
    ResultVo<String> delImpInvoiceDetailPack(Integer invoiceId, Integer detailId);

    /**
     * 把imp_invocie_detail 发票数据写入到ops_po_invoice_detail
     * imp_invoice_detail_pack 分包数据写入到impdata
     * <p>
     * 写一条到ops_po_invoice 是发票主表
     *
     * @param invoiceId
     * @return
     */
    ResultVo<String> confirmPOInvoice(Integer invoiceId);

    /**
     * 批量入库
     *
     * @param invoiceIds
     * @return
     */
    ResultVo<String> confirmPOInvoices(List<Integer> invoiceIds);

    /**
     * 写入发货分包数据
     *
     * @param importOrderInfoVOs
     * @return
     */
    ResultVo<String> addImpInvoiceDataPack(List<ImportOrderInfoVO> importOrderInfoVOs);

    /**
     * 新增或修改发票主数据
     */
    ResultVo<String> addInvoiceMasterData(ImpInvoiceMasterDO invoiceMasterDO);


    /**
     * 新增发票明细
     */
    ResultVo<String> addInvoiceDetailData(ImpInvoiceDetailDO invoiceDetailDO);

    /**
     * 新增发票分包
     */
    ResultVo<String> addInvoiceDetailPackData(ImpInvoiceDetailPackDO invoiceDetailDO);

    /**
     * 发票转成本结算
     * 按imp_invoice_master.id 的待转成本的发票,生成到ops_invoice和ops_invoice_detail中
     * 处理国内集团内先发货后开票,开票的数据转入做成本
     *
     * @param request
     * @return
     */
    ResultVo<String> impInvoiceToCost(ImpInvoiceToCostRequest request);

    /**
     * 复制发票明细到分包明细
     *
     * @param invoiceId
     * @return
     */
    ResultVo<String> copyToInvoicedetailPack(Long invoiceId);

    /**
     * 查询入库数据
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<ImpDataVO>> listImpdata(ImpdataRequest request);

    /**
     * 查询入库中数据
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<ImpInvoiceDetailPackVO>> listNoImpInvoiceDetailPack(ImpdataRequest request);

    /**
     * 手动入库入库失败的数据
     *
     * @param request
     * @return
     */
    // ResultVo<String> handimpInvoiceDetailPack(ImpdataRequest request);

    /**
     * 明细项完成录入
     *
     * @return
     */
    ResultVo<String> finishImpInvoiceDeatailAdd(Integer invoiceId);

    /**
     * 收货确认
     *
     * @param dto
     * @return
     */
    ResultVo<String> receiveGoods(ImpInvoiceReceiveDTO dto);

    /**
     * 检查发票差异
     *
     * @param invoiceId
     * @return
     */
    ResultVo<Integer> checkImpInvoiceError(Long invoiceId);

    ResultVo<String> updateImpInvoiceIgnoreError(ImpInvoiceErrorDTO dto);

    /**
     * 北京制造录入发票分包数据
     *
     * @return
     */
    ResultVo<String> syncImpCNInvoicePack(String optTime,Integer type);

    /**
     * 北京制造发票录入发票分包数据
     *
     * @return
     */
    ResultVo<String> syncVExportImpCNInvoicePack(String optTime,Integer type);

    /**
     * 广州制造发货录入发票分包数据
     *
     * @return
     */
    ResultVo<String> syncImpGZInvoicePack(String optDate);

    /**
     * 广州制造发票录入发票数据
     *
     * @return
     */
    ResultVo<String> syncGZSalesinvoiceToIMP(String optDate);
    /**
     *导出发票主表数据
     * @param request
     */
    void exportImpInvoiceMaster(ImpInvoiceMasterRequest request);
    /**
     * 导出发票明细数据
     */
    void exportImpInvoiceDetail(ImpInvoiceDetailRequest request);

    /**
     * 导出发票分包数据
     */
    void exportImpInvoiceDetailPack(ImpInvoiceDetailRequest request);

    /**
     * 导出发票差异数据
     */
    void exportImpInvoiceError(ImpInvoiceErrorRequest request);

    /**
     * 导入日本分包数据文件
     *
     * @param file
     * @return
     */
    ResultVo<String> importJPShippingFile(MultipartFile file);

    List<ImpInvoiceDetailPackVO> listNoImpInvoiceDetailPackExcel(ImpdataRequest request);

    /**
     * 获取采购数据
     *
     * @param poNo
     * @return
     */
    ResultVo<OpsPurchaseInvoiceDO> getopspurchaseInvoice(String poNo, String poItemNo);

    /**
     * 导入OPS_V_ImpInvoiceStatusFrmCMS数据
     */
    ResultVo<String> impInvoiceStatusFrmCMS();

    /**
     * 导入入库发票明细
     */
    ResultVo<String> confirmPOInvoiceDetail(Long invoiceId, Integer type);

    /**
     * 清除关务明细
     */
    ResultVo<String> clearPOInvoiceDetail(Long invoiceId);

    ResultVo<List<InvoiceBalaceDTO>> getSalesInvoiceBalaceData(Date fromDate, Date toDate);

    /**
     * 定时发票入库
     */
    ResultVo<String> syncToImpData();

    /**
     * 录入北京发货数据
     */
    List<ImpInvoiceDetailVO> addBJImpInvoiceData(ImpInvoiceMasterDO masterDO, List<OPSTExportRequestToSalesDO> itemlist);

    /**
     * 录入北京增值税数据
     */
    public List<ImpInvoiceDetailVO> addBJVImpInvoice(ImpInvoiceMasterDO masterDO, List<OPSVExportInvoicePriceToSalesDO> itemlist);

    /**
     * 录入广州发货数据
     */
    List<ImpInvoiceDetailVO> addGZImpInvoiceData(ImpInvoiceMasterDO masterDO, List<ImpDataBJDO> itemlist);

    /**
     * 录入广州增值税数据
     */
    List<ImpInvoiceDetailVO> addGZImpInvoiceTmp(ImpInvoiceMasterDO masterDO, List<GZSalesinvoiceDO> itemlist);

    /**
     * 制造自动入库
     *
     * @param optDate
     * @return
     */
    ResultVo<String> autoGPconfirmPOInvoice(String optDate);

    /**
     * 制造发票入库
     *
     * @param invoiceId
     * @return
     */
    ResultVo<String> produceToconfirmPOInvoice(Long invoiceId);

    /**
     * 增值税发票入库
     * @return
     */
    ResultVo<String> doInvoiceToCost(Long id,Date costDate,String optUser);

    /**
     * 检查发票差异
     */
    Integer impInvoiceCheck(String invoiceId,String invoiceNo);

    /**
     * 导入PO明细
     */
    ResultVo<String> autoConfirmPODetail();

    /**
     * 根据来货型号变更采购接单型号 ops_requestPurchase，ops_purchaseOrder，ops_purchaseInvoice，rcvdetail
     * @param poNo 订单号
     * @param poItemNo 项
     * @param newModelNo  来货型号
     * @return
     */
    ResultVo<String> changePoModelNo(String poNo, Integer poItemNo,String newModelNo);

    /**
     * 获取原大发票号数据
     * @param invoiceId
     * @return
     */
    ResultVo<List<PoInvoiceDetailDO>> listOverseaInvoiceData(Long invoiceId);

    /**
     * 异步处理物流签收关务调用
     *
     * @return
     */
    ResultVo<String> toupdateWarehousingByGW(ImpInvoiceProcessDTO dto);
    /**
     * <!--add by WuWeiDong 20221130 bug 8614 -->
     * 登记删除1,2->3,取消登记删除 3->2
     *
     * @param doType    1：登记删除1,2->3，2：取消登记删除 3->2
     * @return
     */
      ResultVo<String> updateDeleteDetailPack(List<PoInvoiceDTO>  poInvoiceDTOList, Integer doType, String endUser);
      ResultVo<String> updateDeleteDetailPackStatusById( List<ImpInvoiceDetailPackDO> invoiceDetailPackDOS, Integer newStatus, String endUser);

    /**
     * 完成发票入库后，检查状态是否更新和更新货期状态
     * @return
     */
      ResultVo<String> finishConfirmImpInvoice(ImpInvoiceProcessDTO dto);

    /**
     * 发票入库要改下的数据
     * @return
     */
    ResultVo<String> confirmPOInvoiceUpdate(ImpInvoiceMasterDO invoiceMasterDO);

    /**
     * 写入 po_invoice
     * @param impDO
     * @param costDate
     * @return
     */
     Boolean addPoInvoice(ImpInvoiceMasterDO impDO, Date costDate);

    /**
     * 更新无需入库
     * @param request
     * @return
     */
    ResultVo<String>   updateMasterNoStorage(ImpInvoiceMasterRequest request);
}

