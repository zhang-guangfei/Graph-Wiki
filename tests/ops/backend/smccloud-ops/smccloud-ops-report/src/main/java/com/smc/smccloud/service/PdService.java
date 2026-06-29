package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.pd.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/8 11:00
 * @Descripton TODO
 */
public interface PdService {

    // 分页 盘点查询
    ResultVo<PageInfo<OpsAsPdBatchVO>> listPdBatchList(OpsAsPdBatchRequestVO requestVO,int pageNumber, int pageSize);

    // 模糊查询盘点批次号(前端ui使用)
    ResultVo<List<OpsAsPdBatchVO>> findBatchNo(String batchNo);

    // 新建盘点任务
    ResultVo<String> newPD();

    //清空本次盘点数据
    ResultVo<String> cleanPdData();

    // 到货未入数据查询
    ResultVo<PageInfo<OpsAsWmsInventoryArrivedNotInDO>> listArriveNotInDetail(OpsArriverNotInRequestVO dto);

    // 到货未入发票数据查询
    ResultVo<PageInfo<OpsAsWmsInventoryArrivedNotInDO>> listArriveNotInWithGroup(OpsArriverNotInRequestVO dto);
    // 到货未入数据导出
    void exportArriverNotIn(OpsArriverNotInRequestVO dto);

    ResultVo<String> getExportMsgPrompt();

    void downImpExel();

    // 到货未入页面导入物流确认数据
    ResultVo<String> batchImpArriverNotIn(MultipartFile file,  String loginUser);

    ResultVo<OpsAsPdBatchDO> getBatchNoWithIsActive();

    // wms中间表导入数据至到货未入
    ResultVo<String> expotToArriveNotInByWmsData();

    // 到货未入数据写入到盘点票结果集 (到货未入页面确定按钮)
    ResultVo<String> arriveNotInInsertToBillData();

    // 盘点形式-- 确定按钮
    ResultVo<String> surePdType(String pdType,String pdDataType);

    // 确认盘点形式
    ResultVo<String> surePdType2(String pdType,String pdDataType);

    // 票据生成及打印 -- 生成盘点票按钮
    ResultVo<String> createPdBill(CreateBlankRequest dto);

    // 是否可生成到货未入空白票
    ResultVo<String> isCreDhwrBlankBill();

    // 获取上次盘点形式的确认
    ResultVo<String> getPdSureDateType();

    // 打印现品盘点票
    void printPdXpBillData(String title,String billType);

    void uploadAllFileForPD();

    void printPdXpBillDataToServer(String title,String billType,String fileName,String fileSavePath,String warehouse);

    void printLhBillDataWithPdfToServer(String title,String billType,String fileName,String fileSavePath,String warehouse);

    void printPdArriveNotInWithPdfToServer(String title,String billType,String fileName,String fileSavePath,String warehouse);

    ResultVo<List<BillPrintShowVO>> findModelNoCount(String dataType);

    ResultVo<List<BillPrintShowVO>> findModelNoCountByMaster(String dataType);

    ResultVo<List<BillPrintShowVO>> findModelNoCountBySUB(String dataType);

    ResultVo<List<BillPrintShowVO>> findModelNoCountByWT(String dataType);

    void printLhBillDataWithPdf(String title,String billType);

    void printPdArriveNotInWithPdf(String title,String billType);

    void printWtBillDataWithPdf(String warehouseCode);

    void printWtBillDataWithPdf2(String warehouseCode,HttpServletResponse response);

    // 盘点录入-现品票录入查询
    ResultVo<PageInfo<OpsAsPdBillDataDO>> listPdBillData(PdInputRequestVO dto);

    // 根据盘点票id更新数量
    ResultVo<String> updatePdBillDataById(PdInputDto dto);

    // 清空当前盘点票数量
    ResultVo<String> clearPdXpBillDataQtyById(PdInputDto dto);

    // 根据盘点票id更新现品空白票录入信息
    ResultVo<String> upXpBlankBillInputInfo(PdInputDto dto);

    ResultVo<String> clearPdXpBlankBillDataQtyById(PdInputDto dto);

    // 根据盘点票id更新到货未入空白票录入信息
    ResultVo<String> upArriveNotINBlankBillInputInfo(PdInputDto dto);

    ResultVo<String> clearArriverNotInBlankBillDataQtyById(PdInputDto dto);

    // 下载借库导入模板
    void downBorrowExel();

    // 导入借库数据
    ResultVo<String> batchImpBorrowData(MultipartFile file,  String loginUser);

    // 通过id删除借库数据
    ResultVo<String> delBorrowDataById(PdBorrowDto dto);

    // 根据借库数据id更新借库信息
    ResultVo<String> upBorrowInfo(PdBorrowDto dto);

    ResultVo<PageInfo<OpsAsPdBorrowDataDO>> listBorrowDataList(PdInputRequestVO requestVO);

    ResultVo<PageInfo<OpsAsPdBillDataDO>> findPdBillDataListWithDiff(PdDiffDataRequestVO dto);

    // 查询数据导出 -- 差异调整
    void exportPdBillDiffFindDataList(PdDiffDataRequestVO dto);

    void exportPdBillDiffFindDataListByEasyExcel(PdDiffDataRequestVO dto,HttpServletResponse response);

    // 确认二次录入 -- 差异调整
    ResultVo<String> suerAgainInput(String warehouseCode);

    ResultVo<String> updateDiffPdBillDataById(PdInputDto dto);

    ResultVo<String> delDiffPdBillDataById(PdInputDto dto);

    ResultVo<List<OpsAsPdOtherdataDO>> listPdOtherDataImp();

    ResultVo<String> editOtherData(PdOtherDataDto dto);

    ResultVo<String> delOtherDataById(PdOtherDataDto dto);

    // 委托在库数据写入wms库存中间表
    ResultVo<String> wtDataImpWmsInventoryData();

    // 抽取样品结转数据到补偿表中
    ResultVo<String> impSampleorderDataToOpsCompensateData(String flag);

    // 已出库未推财务数据
    ResultVo<String> yckNotpushCwData(String flag);

    // 财务补偿数据作业
    ResultVo<String> cwbcData(String flag);

    // 调拨在途数据抽取
    ResultVo<String> dbztData(String flag);

    // OPS制造在途数据抽取
    ResultVo<String> zzztData(String flag);

    // WMS制造在途数据抽取
    ResultVo<String> wmszzztData(String flag);

    // wms组换在途数  ops_as_pd_compensate_data补偿表
    ResultVo<String> wmsZHztData(String flag);

    // ops组换在途 -> ops_as_pd_compensate_data补偿表
    ResultVo<String> opsZHztData(String flag);

    // ops组换到货未入 -> ops_as_ops_inventory_data
    ResultVo<String> opsZHdhwr(String flag);

    // 2-采购  3-调拨 4-退货
    // ops采购到货未入
    ResultVo<String> opsArriveNotInWithCG(String flag);
    //  ops退货到货未入
    ResultVo<String> opsArriveNotInWithTH(String flag);
    // ops调拨到货未入
    ResultVo<String> opsArriveNotInWithDB(String flag);

    // ops库存数据抽取
    ResultVo<String> opsInventory(String flag);

    // wms补偿数据数据抽取
    ResultVo<String> wmsbcData(String flag);

    // ops退货在途数据抽取
    ResultVo<String> opsReturnData(String flag);
    // wms退货在途数据抽取
    ResultVo<String> wmsReturnData(String flag);
    // 财务结存数据抽取
    ResultVo<String> cwBalance(String flag);

    ResultVo<String> execPdTaskImpData();

    ResultVo<String> exportOtherData(String methodCode);

   void exportOtherDataWithExcel(String methodCode,HttpServletResponse response);

    ResultVo<String> pdDataImp();

    /**
     * 开始抽取盘点数据 盘点数据抽取
     * @return
     */
    ResultVo<String> startExtractData();

    ResultVo<String> getOpsAsWmsTaskNoticeStatus();

    ResultVo<String> getPdStatusFromRedis();

    ResultVo<List<OpsAsWmsInventoryArrivedNotInDO>>  remoteInvoiceWithArriveNotIn(String invoice);

    ResultVo<List<OpsAsPdBillDataDO>>  remotePdBillNo(RemotePdBillNoVo remotePdBillNoVo);

    // 根据员工编码获取负责的仓库
    String getWarehouseConfigInfo(String person);

    // 导出货架号
    void exportShelvesData(HttpServletResponse response);

    // 导出票据统计
    void exportBillCountData(HttpServletResponse response,String dataType);

    // 作废当前盘点批次
    ResultVo<String> makeCurPBatchCancel();

    ResultVo<PageInfo<OpsPdAdjustDO>> findPdAdjustList(PdAdjustParamVO paramVO);

    // 生成调整单
    ResultVo<String> createPdAdjust(String optUser);

    // 调账单导出
    void exportPdAdjustData(PdAdjustParamVO paramVO);

    /**
     * 确认盘点账
     */
    ResultVo<String> confirmPdAccount(CommonHnadVO commonHnadVO);

    /**
     * 单条调账
     */
    ResultVo<String> createAdjustform(OpsPdAdjustDO opsPdAdjustDO);

    /**
     * 生成调账票号
     */
    ResultVo<OpsPdAdjustDO> createAdjustNo(String warehouseCode);

    /**
     * 下载批量调账模板
     */
    void quantityCanUseBatch();

    /**
     * 批量调账
     */
    ResultVo<String> batchAdjustData(MultipartFile file, String loginUser);

    /**
     * 进入盘点差异确认中
     */
    ResultVo<String> surePdDataDiff(String optUser);

    String getDataSourceByCode(String code);


    ResultVo<List<OpsPdAdjustDO>> findAdjustDoList(@RequestParam("pdBatchNo") String pdBatchNo);

}
