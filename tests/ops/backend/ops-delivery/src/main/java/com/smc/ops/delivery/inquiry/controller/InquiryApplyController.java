package com.smc.ops.delivery.inquiry.controller;


import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInquiryApply;
import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.db.entity.OpsInquiryReasonParamConfig;
import com.sales.ops.dto.inquiry.*;
import com.smc.ops.delivery.inquiry.service.InquiryApplyHandleService;
import com.smc.ops.delivery.inquiry.service.InquiryApplyService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author smc
 * @since 2023-10-24
 */
@RestController
@CrossOrigin
@RequestMapping("/inquiry/apply")
public class InquiryApplyController {
    @Resource
    private InquiryApplyService inquiryApplyService;

    @Resource
    private InquiryApplyHandleService inquiryApplyHandleService;

    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public ResultVo<PageInfo<OpsInquiryApply>> findAll(@RequestBody InquiryApplyRequest inquiryApplyRequest, Page page){
        return inquiryApplyService.findAll(inquiryApplyRequest,page);
    }

    @RequestMapping(value = "/exportExcel",method = RequestMethod.POST)
    public void exportExcelData(@RequestBody InquiryApplyRequest inquiryApplyRequest, HttpServletResponse response){
         inquiryApplyService.exportExcelData(inquiryApplyRequest,response);
    }

    /**
     * 前端 查询子项回复信息
     */
    @RequestMapping(value = "/fetchReplyList",method = RequestMethod.POST)
    public ResultVo<List<InquiryOrderDetailReturnDto>> findInquiryDetail(@RequestBody OpsInquiryApply opsInquiryApply){
        return inquiryApplyService.findDetail(opsInquiryApply);
    }

    /**
     * 前端输入单号，或者批量导入时的查询
     * @return
     */
    @RequestMapping(value = "/getOrderData",method = RequestMethod.POST)
    public ResultVo<List<InquiryApplyVerifyReurn>> getOrderData(@RequestBody List<String> rorderFno){
        return inquiryApplyService.getOrderData(rorderFno);
    }


    /**
     * 前端输入单号
     * @return
     */
    @RequestMapping(value = "/orderInquiryVerify",method = RequestMethod.POST)
    public ResultVo<List<InquiryOrderVerifyReurn>> getOrderInquiryData(@RequestBody List<String> rorderFno){
        return inquiryApplyService.getOrderInquiryData(rorderFno);
    }
    /**
     * 前端写入申请表中
     * @param list
     * @return
     */
    @RequestMapping(value = "/addInquiry",method = RequestMethod.POST)
    public ResultVo<String> addInquiry(@RequestBody List<InquiryApplyAddParam> list){
        try {
            return inquiryApplyService.addInquiryData(list);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 前端写入申请表中
     * @param list
     * @return
     */
    @RequestMapping(value = "/orderAddInquiry",method = RequestMethod.POST)
    public ResultVo<String> orderAddInquiry(@RequestBody List<InquiryApplyAddParam> list){
        try {
            return inquiryApplyHandleService.orderInquiryAdd(list);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 批量导入
     * @param file
     * @return
     */
    @RequestMapping(value = "/importBatchData", method = RequestMethod.POST)
    public ResultVo<List<InquiryApplyVerifyReurn>> importBatchData(@RequestParam("file") MultipartFile file) {
        try {
            return inquiryApplyService.importFile(file);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 订单催促批量导入
     * @param file
     * @return
     */
    @RequestMapping(value = "/importOrderBatchData", method = RequestMethod.POST)
    public ResultVo<List<InquiryOrderVerifyReurn>> importOrderBatchData(@RequestParam("file") MultipartFile file) {
        try {
            return inquiryApplyService.importOrderFile(file);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    @RequestMapping(value = "/getApplyNo", method = RequestMethod.POST)
    public ResultVo<String> importBatchData(@RequestBody String inquiryType) {
        String applynoMax =  inquiryApplyHandleService.generateApplyNo(inquiryType);
        if (StringUtils.isBlank(applynoMax)) {
            return ResultVo.failure("获取申请号失败");
        }
        return ResultVo.success(applynoMax);
    }

    @RequestMapping(value = "/getReasonBySuppily", method = RequestMethod.POST)
    public ResultVo<List<OpsInquiryCodeConfig>> getReasonBySuppily(@RequestBody String suppily) {
        return inquiryApplyService.getReasonBySuppily(suppily);
    }

    /**
     *
     * 获取配置的催促原因，带参数
     * @return
     */
    @RequestMapping(value = "/getAllReason", method = RequestMethod.POST)
    public ResultVo<List<OpsInquiryCodeConfig>> getReasonWithParams() {
        return inquiryApplyService.getAllReason();
    }


    /**
     *
     * 获取配置的催促原因，带参数
     * @return
     */
    @RequestMapping(value = "/getAllParamConfig", method = RequestMethod.POST)
    public ResultVo<List<OpsInquiryReasonParamConfig>> getAllParamConfig() {
        return inquiryApplyService.getAllParamConfig();
    }






}
