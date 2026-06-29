package com.smc.ops.delivery.inquiry.controller;

import com.sales.ops.dto.inquiry.*;
import com.smc.ops.delivery.inquiry.service.InquiryApplyHandleService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.log.annotation.SysLog;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: B91717
 * @time: 2023/12/20 20:15
 */

@RestController
@CrossOrigin
@RequestMapping("/inquiry/handle")
public class InquiryHandleCotroller {

    @Resource
    private InquiryApplyHandleService inquiryApplyHandleService;

    /**
     * 催促模块查询及返回验证信息
     * @param rorderFno
     * @return
     */
    @RequestMapping(value = "/purchaseInquiryVerify",method = RequestMethod.POST)
    public  ResultVo<List<InquiryApplyVerifyReurn>>  inquiryVerify(@RequestBody List<String> rorderFno){
        try {
            return inquiryApplyHandleService.purchaseInquiryVerify(rorderFno,"0");
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    @RequestMapping(value = "/orderInquiryVerify",method = RequestMethod.POST)
    public  ResultVo<List<InquiryOrderVerifyReurn>>  orderInquiryVerify(@RequestBody List<String> rorderFno){
        try {
            return inquiryApplyHandleService.orderInquiryVerify(rorderFno,"1");
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 门户催促新增调用接口
     * @param inquiryApplyAddParam
     * @return
     */
    @RequestMapping(value = "/salesAddApply",method = RequestMethod.POST)
    @SysLog("INQA门户新增：")
    public ResultVo<InquiryApplyVerifyReurn> salesAddApply(@RequestBody InquiryApplyAddParam inquiryApplyAddParam){
        try {
            return inquiryApplyHandleService.salesInquiryAdd(inquiryApplyAddParam);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 含订单催促和采购催的新增接口
     * @param inquiryApplyAddParam
     * @return
     */
    @RequestMapping(value = "/salesInquiryAddNew",method = RequestMethod.POST)
//    @SysLog("INQA门户新增：")
    public ResultVo<InquirySalesApplyReurn> salesInquiryAddNew(@RequestBody InquiryApplyAddParam inquiryApplyAddParam){
        try {
            return inquiryApplyHandleService.salesInquiryAddNew(inquiryApplyAddParam);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }
    @RequestMapping(value = "/isWorkday",method = RequestMethod.POST)
    public ResultVo<Boolean> getWorkday(@RequestBody InquiryWorkdayCondition info){
        return inquiryApplyHandleService.getWorkday(info);
    }

    /**
     * 根据不同供应商返回可选的催促原因配置
     * @param
     * @return
     */
    @RequestMapping(value = "/getReasonBySuppilyBatch",method = RequestMethod.POST)
    public ResultVo<List<InquiryCodeConfigBySuppily>> getReasonBySuppilyBatch(@RequestBody List<String> suppilyList){
        try {
            return inquiryApplyHandleService.getReasonBySuppilyBatch(suppilyList);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 订单催促货期，实时校验接口
     * 订单催促，前端选择货期之后，进行校验，返回前端校验结果
     */
    @RequestMapping(value = "/deliveryDataVerify",method = RequestMethod.POST)
    public ResultVo<List<InquiryOrderMasterDto>> deliveryDataVerify(@RequestBody List<InquiryApplyAddParam> requestDtos){
        try {
            return inquiryApplyHandleService.deliveryDateInquiryVerify(requestDtos);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

}
