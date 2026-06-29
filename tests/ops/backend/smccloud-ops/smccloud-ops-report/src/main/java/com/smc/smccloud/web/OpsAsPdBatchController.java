package com.smc.smccloud.web;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.enums.PdBillTypeEnum;
import com.smc.smccloud.model.pd.*;
import com.smc.smccloud.model.returnorder.PrintReturnOrderParams;
import com.smc.smccloud.service.MonthPdService;
import com.smc.smccloud.service.OpsAsPdThreeReportService;
import com.smc.smccloud.service.PdService;
import com.smc.smccloud.util.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smc
 * @since 2023-06-07
 */
@RestController
@RequestMapping("/report/pd")
@Slf4j
public class OpsAsPdBatchController {

    @Resource
    private PdService pdService;

    @Resource
    private MonthPdService monthPdService;
    @Resource
    private OpsAsPdThreeReportService opsAsPdThreeReportService;
    /**
     *  分也盘点查询
     * @param requestVO 参数
     * @return
     */
    @PostMapping("/listPdBatchList")
    public ResultVo<PageInfo<OpsAsPdBatchVO>> listPdBatchList(@RequestBody OpsAsPdBatchRequestVO requestVO) {
       return pdService.listPdBatchList(requestVO, requestVO.getPage().getPageNumber(), requestVO.getPage().getPageSize());
    }

    /**
     * 模糊查询盘点批次号(前端ui使用)
     * @param batchNo
     * @return
     */
    @GetMapping("/findBatchNo")
    public ResultVo<List<OpsAsPdBatchVO>> findBatchNo(@RequestParam("batchNo") String batchNo) {
        return pdService.findBatchNo(batchNo);
    }

    // 新建盘点
    @GetMapping("/newPd")
    public ResultVo<String> newPD() {
        return pdService.newPD();
    }

    // 清空本次准备数据
    @GetMapping("/cleanPdData")
    public ResultVo<String> cleanPdData() {
        return pdService.cleanPdData();
    }

    @PostMapping("/exportArriveNotIn")
    public void exportArriveNotIn(@RequestBody OpsArriverNotInRequestVO dto) {
         pdService.exportArriverNotIn(dto);
    }

    @GetMapping("/getExportMsgPrompt")
    public ResultVo<String> getExportMsgPrompt() {
        return pdService.getExportMsgPrompt();
    }

    @PostMapping("/downImpExel")
    public void downImpExel() {
        pdService.downImpExel();
    }

    @PostMapping("/batchImpArriverNotIn")
    public ResultVo<String> batchImpArriverNotIn(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        return pdService.batchImpArriverNotIn(file, loginUser);
    }

    @GetMapping("/getBatchNoWithIsActive")
    public ResultVo<OpsAsPdBatchDO> getBatchNoWithIsActive() {
        return pdService.getBatchNoWithIsActive();
    }

    @PostMapping("/listArriveNotInWithGroup")
    public ResultVo<PageInfo<OpsAsWmsInventoryArrivedNotInDO>> listArriveNotInWithGroup( @RequestBody OpsArriverNotInRequestVO dto) {
        return pdService.listArriveNotInWithGroup(dto);
    }

    @PostMapping("/listArriveNotInDetail")
    public ResultVo<PageInfo<OpsAsWmsInventoryArrivedNotInDO>> listArriveNotInDetail( @RequestBody OpsArriverNotInRequestVO dto) {
        return pdService.listArriveNotInDetail(dto);
    }

    @GetMapping("/expotToArriveNotInByWmsData")
    public ResultVo<String> expotToArriveNotInByWmsData() {
        return pdService.expotToArriveNotInByWmsData();
    }

    @GetMapping("/arriveNotInInsertToBillData")
    public ResultVo<String> arriveNotInInsertToBillData() {
        return pdService.arriveNotInInsertToBillData();
    }

    @GetMapping("/surePdType")
    public ResultVo<String> surePdType(@RequestParam("pdType") String pdType, @RequestParam("pdDataType")String pdDataType){
        return pdService.surePdType2(pdType,pdDataType);
    }
    @PostMapping("/createPdBill")
    public ResultVo<String> createPdBill(@RequestBody CreateBlankRequest dto){
        return pdService.createPdBill(dto);
    }

    // 打印现品票
    @RequestMapping(value = "/printPdXpBillDataWithPdf",produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.GET)
    public void printPdXpBillDataWithPdf(@RequestParam("title")String title){
        pdService.printPdXpBillData(title, PdBillTypeEnum.XPBILL.getCode());
    }
    // 打印现品空白票
    @RequestMapping(value = "/printPdXpBlankBillDataWithPdf",produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.GET)
    public void printPdXpBlankBillDataWithPdf(@RequestParam("title")String title){
        pdService.printPdXpBillData(title, PdBillTypeEnum.XPBLANKBILL.getCode());
    }

    // 打印现品到货未入空白票
    @RequestMapping(value = "/printPdArriveNotInBlankBillDataWithPdf",produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.GET)
    public void printPdArriveNotInBlankBillDataWithPdf(@RequestParam("title")String title){
        pdService.printPdXpBillData(title, PdBillTypeEnum.DHWRBLANKBILL.getCode());
    }

    @GetMapping("/findModelNoCount")
    public ResultVo<List<BillPrintShowVO>> findModelNoCount(@RequestParam("dataType") String dataType) {
        return pdService.findModelNoCount(dataType);
    }
    @GetMapping("/findModelNoCountByMaster")
    public ResultVo<List<BillPrintShowVO>> findModelNoCountByMaster(@RequestParam("dataType") String dataType) {
        return pdService.findModelNoCountByMaster(dataType);
    }
    @GetMapping("/findModelNoCountBySUB")
    public ResultVo<List<BillPrintShowVO>> findModelNoCountBySUB(@RequestParam("dataType") String dataType) {
        return pdService.findModelNoCountBySUB(dataType);
    }
    @GetMapping("/findModelNoCountByWT")
    public ResultVo<List<BillPrintShowVO>> findModelNoCountByWT(@RequestParam("dataType") String dataType) {
        return pdService.findModelNoCountByWT(dataType);
    }
    // 打印立会票
    @RequestMapping(value = "/printLhBillDataWithPdf",produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.GET)
    public void printLhBillDataWithPdf(@RequestParam("title")String title){
        pdService.printLhBillDataWithPdf(title, PdBillTypeEnum.XPBILL.getCode());
    }

    @PostMapping("/listPdBillData")
    public ResultVo<PageInfo<OpsAsPdBillDataDO>> listPdBillData( @RequestBody PdInputRequestVO dto) {
        return pdService.listPdBillData(dto);
    }

    @PostMapping("/updatePdBillDataById")
    public ResultVo<String> updatePdBillDataById(@RequestBody PdInputDto dto) {
        return pdService.updatePdBillDataById(dto);
    }

    @PostMapping("/clearPdXpBillDataQtyById")
    public ResultVo<String> clearPdXpBillDataQtyById(@RequestBody PdInputDto dto) {
        return pdService.clearPdXpBillDataQtyById(dto);
    }
    // 根据盘点票id更新现品空白票录入信息
    @PostMapping("/upXpBlankBillInputInfo")
    public ResultVo<String> upXpBlankBillInputInfo(@RequestBody PdInputDto dto) {
        return pdService.upXpBlankBillInputInfo(dto);
    }
    // 根据盘点票id清空现品空白票录入信息
    @PostMapping("/clearPdXpBlankBillDataQtyById")
    public ResultVo<String> clearPdXpBlankBillDataQtyById(@RequestBody PdInputDto dto) {
        return pdService.clearPdXpBlankBillDataQtyById(dto);
    }

    // 根据盘点票id更新到货未入空白票录入信息
    @PostMapping("/upArriveNotINBlankBillInputInfo")
    public ResultVo<String> upArriveNotINBlankBillInputInfo(@RequestBody PdInputDto dto) {
        return pdService.upArriveNotINBlankBillInputInfo(dto);
    }

    @PostMapping("/clearArriverNotInBlankBillDataQtyById")
    public ResultVo<String> clearArriverNotInBlankBillDataQtyById(@RequestBody PdInputDto dto) {
        return pdService.clearArriverNotInBlankBillDataQtyById(dto);
    }

    @PostMapping("/downBorrowExel")
    public void downBorrowExel() {
        pdService.downBorrowExel();
    }

    @PostMapping("/batchImpBorrowData")
    public ResultVo<String> batchImpBorrowData(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        return pdService.batchImpBorrowData(file, loginUser);
    }

    @PostMapping("/delBorrowDataById")
    public ResultVo<String> delBorrowDataById(@RequestBody PdBorrowDto dto) {
        return pdService.delBorrowDataById(dto);
    }

    @PostMapping("/upBorrowInfo")
    public ResultVo<String> upBorrowInfo(@RequestBody PdBorrowDto dto) {
        return pdService.upBorrowInfo(dto);
    }

    @PostMapping("/listBorrowDataList")
    public ResultVo<PageInfo<OpsAsPdBorrowDataDO>> listBorrowDataList(@RequestBody PdInputRequestVO requestVO) {
        return pdService.listBorrowDataList(requestVO);
    }

    //盘点差异-数据查询
    @PostMapping("/findPdBillDataListWithDiff")
    public ResultVo<PageInfo<OpsAsPdBillDataDO>> findPdBillDataListWithDiff(@RequestBody PdDiffDataRequestVO dto) {
        return pdService.findPdBillDataListWithDiff(dto);
    }

    @PostMapping("/exportPdBillDiffFindDataList")
    public void exportArriveNotIn(@RequestBody PdDiffDataRequestVO dto,HttpServletResponse response) {
        pdService.exportPdBillDiffFindDataListByEasyExcel(dto,response);
    }

    @PostMapping("/updateDiffPdBillDataById")
    public ResultVo<String> updateDiffPdBillDataById(@RequestBody PdInputDto dto) {
        return pdService.updateDiffPdBillDataById(dto);
    }

    @PostMapping("/delDiffPdBillDataById")
    public ResultVo<String> delDiffPdBillDataById(@RequestBody PdInputDto dto) {
        return pdService.delDiffPdBillDataById(dto);
    }

    // 打印数据票
    @RequestMapping(value = "/printPdDataBillDataWithPdf",produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.GET)
    public void printPdDataBillDataWithPdf(@RequestParam("title")String title){
        pdService.printPdXpBillData(title, PdBillTypeEnum.SJBILL.getCode());
    }

    // 打印清单票
    @RequestMapping(value = "/printPdArriveNotInWithPdf",produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.GET)
    public void printPdArriveNotInWithPdf(@RequestParam("title")String title){
        pdService.printPdArriveNotInWithPdf(title, PdBillTypeEnum.QDBILL.getCode());
    }

    // 打印寄售库存盘点票
    @RequestMapping(value = "/printWtBillDataWithPdf",produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.GET)
    public void printWtBillDataWithPdf(@RequestParam("warehouseCode")String warehouseCode,HttpServletResponse response){
        pdService.printWtBillDataWithPdf2(warehouseCode,response);
    }

    @PostMapping("/editOtherData")
    public ResultVo<String> editOtherData(@RequestBody PdOtherDataDto dto) {
        return pdService.editOtherData(dto);
    }

    @PostMapping("/delOtherDataById")
    public ResultVo<String> delOtherDataById(@RequestBody PdOtherDataDto dto) {
        return pdService.delOtherDataById(dto);
    }
    @PostMapping("/listPdOtherDataImp")
    public ResultVo<List<OpsAsPdOtherdataDO>> listPdOtherDataImp() {
        return pdService.listPdOtherDataImp();
    }

    // 确认二次录入
    @GetMapping("/suerAgainInput")
    public ResultVo<String> suerAgainInput(@RequestParam("warehouseCode") String warehouseCode) {
        return pdService.suerAgainInput(warehouseCode);
    }

    // 其他数据录入 -- 手动执行
    @GetMapping("/exportOtherData")
    public ResultVo<String> exportOtherData(@RequestParam("methodCode") String methodCode) {
        return pdService.exportOtherData(methodCode);
    }

    // 其他数据录入 -- 导出操作
    @GetMapping("/exportOtherDataWithExcel")
    public void exportOtherDataWithExcel(@RequestParam("methodCode") String methodCode,HttpServletResponse response) {
         pdService.exportOtherDataWithExcel(methodCode,response);
    }


    // 其他数据录入 -- 导出操作
    @GetMapping("/exportBillCountData")
    public void exportBillCountData(@RequestParam("dataType") String dataType,HttpServletResponse response) {
        pdService.exportBillCountData(response,dataType);
    }

    // 盘点数据抽取
    @GetMapping("/pdDataImp")
    public ResultVo<String> pdDataImp() {
        return pdService.startExtractData();
    }
    @GetMapping("/getOpsAsWmsTaskNoticeStatus")
    public ResultVo<String> getOpsAsWmsTaskNoticeStatus(){
        return pdService.getOpsAsWmsTaskNoticeStatus();
    }

    @GetMapping("/getPdStatusFromRedis")
    public ResultVo<String> getPdStatusFromRedis() {
        return pdService.getPdStatusFromRedis();
    }

    @GetMapping("/remoteInvoiceWithArriveNotIn")
    public ResultVo<List<OpsAsWmsInventoryArrivedNotInDO>> remoteInvoiceWithArriveNotIn(@RequestParam("invoice") String invoice) {
        return pdService.remoteInvoiceWithArriveNotIn(invoice);
    }

    @PostMapping("/remotePdBillNo")
    public ResultVo<List<OpsAsPdBillDataDO>> remotePdBillNo(@RequestBody RemotePdBillNoVo remotePdBillNoVo) {
        return pdService.remotePdBillNo(remotePdBillNoVo);
    }

    @GetMapping("/isCreDhwrBlankBill")
    public ResultVo<String> isCreDhwrBlankBill() {
        return pdService.isCreDhwrBlankBill();
    }

    @GetMapping("/getPdSureDateType")
    public ResultVo<String> getPdSureDateType() {
        return pdService.getPdSureDateType();
    }

    @GetMapping("/exportShelvesData")
    public void exportShelvesData(HttpServletResponse response) {
        pdService.exportShelvesData(response);
    }

    // 盘点报表查询
    @PostMapping("/pdReportList")
    public ResultVo<PageInfo<OpsAsPdThreeReportDO>> pdReportList(@RequestBody PdReportParamVO paramVO) {
        return opsAsPdThreeReportService.pdReportList(paramVO);
    }

    // 生成盘点报表
    @GetMapping("/markPdReport")
    public ResultVo<String> markPdReport() {
        return opsAsPdThreeReportService.markPdReport();
    }

    @GetMapping("/exportTwoPdReport")
    public void exportTwoPdReport(HttpServletResponse response) {
        opsAsPdThreeReportService.exportTwoPdReport(response);
    }

    @PostMapping("/exportThreePdReport")
    public void exportThreePdReport(HttpServletResponse response,@RequestBody PdReportParamVO paramVO) {
        opsAsPdThreeReportService.exportThreePdReport(response,paramVO);
    }

    // 作废当前盘点批次
    @GetMapping("/makeCurPBatchCancel")
    public ResultVo<String> makeCurPBatchCancel() {
       return pdService.makeCurPBatchCancel();
    }

    @PostMapping("/testEasyExcelExport")
    public void testEasyExcelExport(HttpServletResponse response) {
        PdDiffBillDataExportVO item= new PdDiffBillDataExportVO();
        item.setPdBatchNo("PD202307");
        item.setPdBillNo("KBJ31139420221480400001");
        item.setPdBillType("清单票");
        item.setLocationNo("CROSSDOCK");
        item.setShelvesNo("CROSSDOCK");
        item.setInvoiceNo("LCN380");
        item.setCaseNo("SHX23040601689");
        item.setBillQty(10);
        item.setModelNo1("25A-VHS40-03A");
        item.setModelNo2("25A-VHS40-03A2");
        item.setPdQty1(1);
        item.setPdQty2(2);
        item.setPdInputTime1UI(new Date());
        item.setPdInputTime2UI(new Date());
        item.setPdInputort1("C180971");
        item.setPdInputort1("C180972");
        List<PdDiffBillDataExportVO> list = new ArrayList<>();
        list.add(item);
        int count = 1;
        long lstart = System.currentTimeMillis();
        EasyExcelUtil.export("test.xlsx",list,PdDiffBillDataExportVO.class,response);
//        try {
//            String fileName = URLEncoder.encode("盘点差异数据导出", "UTF-8").replaceAll("\\+", "%20");
//            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
//            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
//            InputStream inputStream = new ClassPathResource("templates/excel/盘点差异调整导出.xlsx").getInputStream();
//            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PdDiffBillDataExportVO.class)
//                    .withTemplate(inputStream).autoCloseStream(Boolean.FALSE).build();
//            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
//            for (int i = 0; i<20; i++) {
//                List<PdDiffBillDataExportVO> list = new ArrayList<>();
//                for (int num = 1 ; num<=5000; num++) {
//                    item.setId((long) count);
//                    list.add(item);
//                    count++;
//                }
//                excelWriter.write(list,writeSheet);
//            }
//            excelWriter.finish();
//            log.info("导盘点差异数据结束 耗时 {} (s)",(System.currentTimeMillis() - lstart) / 1000);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @PostMapping("/findPdAdjustList")
    public ResultVo<PageInfo<OpsPdAdjustDO>> findPdAdjustList(@RequestBody PdAdjustParamVO paramVO) {
        return pdService.findPdAdjustList(paramVO);
    }
    @PostMapping("/exportPdAdjustData")
    public void exportPdAdjustData(@RequestBody PdAdjustParamVO paramVO) {
        pdService.exportPdAdjustData(paramVO);
    }

    /**
     * 生成调账单
     */
    @GetMapping("/createPdAdjust")
    public ResultVo<String> createPdAdjust(@RequestParam("optUser") String optUser) {
        return pdService.createPdAdjust(optUser);
    }

    /**
     * 确认盘点账
     */
    @PostMapping("/confirmPdAccount")
    public ResultVo<String> confirmPdAccount(@RequestBody CommonHnadVO commonHnadVO) {
        return pdService.confirmPdAccount(commonHnadVO);
    }

    /**
     * 单条调账
     */
    @PostMapping("/createAdjustform")
    public ResultVo<String> createAdjustform(@RequestBody OpsPdAdjustDO opsPdAdjustDO) {
        return pdService.createAdjustform(opsPdAdjustDO);
    }

    /**
     * 生成调账单号
     */
    @GetMapping("/createAdjustNo")
    public ResultVo<OpsPdAdjustDO> createAdjustNo(@RequestParam("warehouseCode") String warehouseCode) {
        return pdService.createAdjustNo(warehouseCode);
    }

    /**
     * 下载批量调账模板
     */
    @GetMapping("/dowmBatchAdjusExcel")
    public void quantityCanUseBatch() {
        pdService.quantityCanUseBatch();
    }

    @PostMapping("/batchAdjustData")
    public ResultVo<String> batchAdjustData(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        return pdService.batchAdjustData(file,loginUser);
    }

    @GetMapping("/surePdDataDiff")
    public ResultVo<String> surePdDataDiff(@RequestParam("optUser") String optUser) {
        return pdService.surePdDataDiff(optUser);
    }

    // --------- 月度盘点 ---------

    @PostMapping("/listYdPdBatchList")
    public ResultVo<PageInfo<OpsAsPdBatchYdDO>> listYdPdBatchList(@RequestBody YdPdSearchParams requestVO) {
        return monthPdService.listYdPdBatchList(requestVO,requestVO.getPage().getPageNumber(), requestVO.getPage().getPageSize());
    }

    @PostMapping("/listPdExecPlan")
    public ResultVo<PageInfo<OpsPdExecPlanManageDO>> listYdPdBatchList(@RequestBody ExecPlanParamVO paramVO) {
        return monthPdService.listExecPlan(paramVO);
    }

    @PostMapping("/updateOrAddPdExecPlan")
    public ResultVo<String> updateOrAddPdExecPlan(@RequestBody OpsPdExecPlanManageDO opsPdExecPlanManageDO) {
        return monthPdService.updateOrAddPdExecPlan(opsPdExecPlanManageDO);
    }

    @GetMapping("/makeExecPlan")
    public ResultVo<String> makeExecPlan() {
        return monthPdService.makeExecPlan();
    }

    @GetMapping("/uiExecYdPd")
    public ResultVo<String> uiExecYdPd(@RequestParam("code") String code) {
        return monthPdService.uiExecYdPd(code);
    }

    // 获取执行步骤清单
    @GetMapping("/getExecStepList")
    public ResultVo<List<OpsPdStepManageDO>> getExecStepList() {
        return monthPdService.getExecStepList();
    }

    @PostMapping("/listPdThreeReportWare")
    public ResultVo<PageInfo<OpsAsPdThreeReportWareDO>> listPdThreeReportWare(@RequestBody OpsAsPdReportWareParams params) {
        return monthPdService.listPdThreeReportWare(params);
    }

    @GetMapping("/getPdBatchNoListFromReportWare")
    public ResultVo<List<OpsAsPdThreeReportWareDO>> getPdBatchNoListFromReportWare(@RequestParam("pdBatchNo") String pdBatchNo) {
       return monthPdService.getPdBatchNoListFromReportWare(pdBatchNo);
    }

    @PostMapping("/listYdPdThreeReportWare")
    public ResultVo<PageInfo<OpsAsPdThreeReportWareDO>> listYdPdThreeReportWare(@RequestBody SearchReportWareParams params) {
        return monthPdService.listYdPdThreeReportWare(params);
    }


    @PostMapping("/exportYdPdReport")
    public void exportYdPdReport(HttpServletResponse response,@RequestBody SearchReportWareParams params) {
        monthPdService.exportYdPdReport(response,params);
    }


    @GetMapping("/findAdjustDoList")
    public ResultVo<List<OpsPdAdjustDO>>  findAdjustDoList(@RequestParam("pdBatchNo") String pdBatchNo) {
        return pdService.findAdjustDoList(pdBatchNo);
    }

    // 月度盘点执行步骤导出
    @GetMapping("/exportYdPdStep")
    public void exportYdPdStep(@RequestParam("methodCode") String methodCode,HttpServletResponse response) {
        monthPdService.exportYdPdStep(response,methodCode);
    }
    @GetMapping("/makePdReport")
    public ResultVo<String> makePdReport() {
        return monthPdService.makePdReport();
    }

}
