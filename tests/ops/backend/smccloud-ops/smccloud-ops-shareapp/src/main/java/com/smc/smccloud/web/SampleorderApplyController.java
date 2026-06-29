package com.smc.smccloud.web;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.ExcelUtil;
import com.smc.smccloud.model.UploadFileByUIVO;
import com.smc.smccloud.model.sampleorder.*;
import com.smc.smccloud.service.OpsAttachedFileManageService;
import com.smc.smccloud.service.SampleBalService;
import com.smc.smccloud.service.SampleOrderApplyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smc
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/shareapp/sampleorder")
public class SampleorderApplyController {

    @Resource
    private SampleOrderApplyService sampleorderApplyService;

    @Resource
    private SampleBalService sampleBalService;

    @Resource
    private OpsAttachedFileManageService opsAttachedFileManageService;

    @PostMapping("/listSampleOrderData")
    public ResultVo<PageInfo<SampleOrderVO>> listSampleOrderData(@RequestBody SampleOrderRequest request, Page page){
       return sampleorderApplyService.listSampleOrderData(request,page);
    }

    @PostMapping("/exportSampleOrderApplyData")
    public void exportSampleOrderApplyData(@RequestBody SampleOrderRequest request) {
        sampleorderApplyService.exportSampleOrderApplyData(request);
    }

    /**
     * 生成订单
     * @param sampleOrderParams
     * @return
     */
    @PostMapping("/createOrder")
    public ResultVo<String> createOrder(@Valid @RequestBody SampleOrderParams sampleOrderParams) {
       return sampleorderApplyService.createOrderBySampleOrder(sampleOrderParams);
    }

    /**
     * 订单回退
     */
    @PostMapping("/rebackOrder")
    public ResultVo<String> rebackOrder(@Valid @RequestBody SampleOrderParams sampleOrderParams){
        return sampleorderApplyService.rebackOrder(sampleOrderParams);
    }

    /**
     * 样品订单结转
     */
//    @PostMapping("/orderCarryTurn")
//    public ResultVo<String> orderCarryTurn(@Valid @RequestBody SampleOrderParams sampleOrderParams){
//        return sampleorderApplyService.carryTurnOrder(sampleOrderParams);
//    }

    /**
     * 样品订单 免费结转
     * @param orderCarryParams
     * @return
     */
//    @PostMapping("/orderCarryTurn")
//    public ResultVo<String> orderCarryTurn(@Valid @RequestBody OrderCarryParams orderCarryParams) {
//        return sampleorderApplyService.orderCarryTurn(orderCarryParams);
//    }

    /**
     * 导出样品结转数据
     */
    @PostMapping("/exportSampleBalData")
    public void exportSampleBalData(@RequestBody SampleBalRequest request) {
        sampleorderApplyService.exportSampleBalData(request);
    }

    /**
     * 转销售开票
     * @return
     */
    @PostMapping("/invoicingSales")
    public ResultVo<String> invoicingSales(@RequestBody InvoicingSalesParams params) {
        return sampleorderApplyService.toSalesInvoice(params);
    }

    @PostMapping("/listSampleBalData")
    public ResultVo<PageInfo<SamplebalDO>> listSampleBalData(@RequestBody SampleBalRequest request, Page page){
        return sampleorderApplyService.listSampleBal(request,page);
    }

    /**
     * 拆分结转类型
     */
    @PostMapping("/splitCarryType")
    public ResultVo<String> splitCarryType(@RequestBody SplitSampleBalVO splitSampleBalVO) {
       return sampleorderApplyService.splitCarryType(splitSampleBalVO);
    }
    /**
     * 重新结转
     */
    @PostMapping("/againBal")
    public ResultVo<String> againBal(@RequestBody SplitSampleBalVO splitSampleBalVO) {
        return sampleorderApplyService.againBal(splitSampleBalVO);
    }

//    /**
//     * 展览展示品管理界面-列表查询
//     */
//    @PostMapping("/listSampleOrderManage")
//    public ResultVo<PageInfo<SampleOrderManageVO>> listSampleOrderManage(@RequestBody SampleOrderManageQuery request){
//        return sampleorderApplyService.listSampleOrderManage(request,request.getPage());
//    }
//
//    /**
//     * 导出展览展示品数据
//     */
//    @PostMapping("/exportSampleOrderManageData")
//    public void exportSampleOrderManageData(@RequestBody SampleOrderManageQuery request) {
//        sampleorderApplyService.exportSampleOrderManage(request);
//    }

    /**
     * 逾期未结转导出
     */
    @GetMapping("/exportOverdueBalData")
    public ResultVo<String> exportOverdueBalData() {
        return sampleorderApplyService.exportOverdueBalData();
    }

    /**
     * 下载指定月份逾期未结转文件
     */
    @GetMapping("/downLoadNoBalFileByYearMonth")
    public void downLoadNoBalFileByYearMonth(@RequestParam("yearMonth") String yearMonth) {
        sampleorderApplyService.downLoadNoBalFileByYearMonth(yearMonth);
    }


    /**
     * add by LiYingChao from bugId 8534 in 20221104
     * 取消转销售开票
     */
    @PostMapping("/cancelTurnSalesInvoice")
    public ResultVo<String> cancelTurnSalesInvoice(@RequestBody SplitSampleBalVO sampleBalVO) {
        return sampleorderApplyService.cancelTurnSalesInvoice(sampleBalVO);
    }

    /**
     * 展览展示品盘点表导出
     */
    @PostMapping("/exportZlzsOrderBalance")
    public void exportSampleBalData(@RequestBody ZlzsExportRequest request) {
        sampleBalService.exportZlzsOrderBalance(request);
    }

    /**
     * 发布展览展示品盘点票给营业所
     */
    @PostMapping("/pushZlzsOrderBalance")
    public ResultVo<String> pushZlzsOrderBalance(@RequestBody ZlzsExportRequest request) {
        return sampleBalService.pushZlzsOrderBalanceForPdf(request);
    }

    /**
     * 获取展览展示品导出日期
     */
    @GetMapping("/getZLZSExportTime")
    public ResultVo<String> getZLZSExportTime() {
        return sampleBalService.getZLZSExportTime();
    }


    /**
     * 展览展示品销账
     */
    @PostMapping("/writeoffForZlzsOrder")
    public ResultVo<String> writeoffForZlzsOrder(@RequestBody WriteOffZlzsRequest request) {
        return sampleBalService.writeoffForZlzsOrder(request);
    }

    /**
     * 通过文件导入销账
     * @param file
     * @param loginUser
     * @return
     */
    @PostMapping("/importWriteOffData")
    public ResultVo<String> importWriteOffData(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        return sampleBalService.importWriteOffData(file,loginUser);
    }
    /**
     * 下载销账文件模板
     */
    @GetMapping("/downExcelForWriteOff")
    public void downExcelForWriteOff() {
        sampleBalService.downExcelForWriteOff();
    }

    /**
     * 变更展示品实物所在部门
     */
    @PostMapping("/upZlzsRcvDeptNo")
    public ResultVo<String> upZlzsRcvDeptNo(@RequestBody UpZlzsRcvDeptNoParams params) {
        return sampleBalService.upZlzsRcvDeptNo(params);
    }

    /**
     * 下载变更展示品实物所在部门模板
     */
    @GetMapping("/downExcelForUpRcvDeptNo")
    public void downExcelForUpRcvDeptNo() {
        sampleBalService.downExcelForUpRcvDeptNo();
    }

    /**
     * 通过文件导入变更展示品实物所在部门
     * @param file
     * @param loginUser
     * @return
     */
    @PostMapping("/batchUpRcvDeptNo")
    public ResultVo<String> batchUpRcvDeptNo(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        return sampleBalService.batchUpRcvDeptNo(file,loginUser);
    }


    @PostMapping("/updateSampleById")
    public int updateSampleById(@RequestBody UpSamplelBalVO upSamplelBalVO){
        return sampleBalService.updateSampleById(upSamplelBalVO);
    }

    @PostMapping("/insertIntoSampleOrderManage")
    public ResultVo<String> insertIntoSampleOrderManage(@RequestBody ZlzsExportRequest request) {
        return sampleBalService.insertIntoSampleOrderManage(request);
    }
    @PostMapping("/findSampleBalApplyInfoList")
    public ResultVo<PageInfo<SampleBalApplyVO>> findSampleBalApplyInfoList(@RequestBody QuerySampleBalApplyParam info){
        return sampleBalService.findSampleBalApplyInfoList(info);
    }
    @PostMapping("/sureApplySampleBal")
    public ResultVo<String> sureApplySampleBal(@RequestBody List<String> ids) {
        return sampleBalService.sureApplySampleBal(ids);
    }
    // 前端上传附件信息
    @PostMapping("/uploadFileAttacheFileManageInfoToServer")
    public ResultVo<String> uploadFileAttacheFileManageInfoToServer(@RequestParam("fileList")MultipartFile[] fileList,
                                                                    @RequestParam("keyValue") String keyValue,@RequestParam("createUser") String createUser) {
        return opsAttachedFileManageService.uploadFileAttacheFileManageInfoToServer(fileList, keyValue, createUser);
    }

    // 修正可结转清单数据
    @GetMapping("/updateErrorSampleBalData")
    public ResultVo<String> updateErrorSampleBalData() {
        return sampleBalService.updateErrorSampleBalData();
    }

}
