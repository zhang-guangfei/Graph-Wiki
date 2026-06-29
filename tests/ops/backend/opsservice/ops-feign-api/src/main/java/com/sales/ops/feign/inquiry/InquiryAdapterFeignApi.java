package com.sales.ops.feign.inquiry;


import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.dto.inquiry.*;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 样品出库数据 抽取到sample_bal 表中
 */
@FeignClient(name = "delivery-server", contextId = "inquiryAdapterServer")
public interface InquiryAdapterFeignApi {

    /**
     * 门户调用 采购催促的校验接口
     */
    @RequestMapping(value = "/do/inquiry/handle/purchaseInquiryVerify", method = RequestMethod.POST)
    ResultVo<List<InquiryApplyVerifyReurn>> purchaseInquiryVerify(@RequestBody List<String> rorderFno);

    /**
     * 门户调用 订单催促的校验接口
     */
    @RequestMapping(value = "/do/inquiry/handle/orderInquiryVerify", method = RequestMethod.POST)
    ResultVo<List<InquiryOrderVerifyReurn>> orderInquiryVerify(@RequestBody List<String> rorderFno);

    /**
     * 门户调用 采购催促新增
     * 给门户的
     */
    @RequestMapping(value = "/do/inquiry/handle/salesAddApply", method = RequestMethod.POST)
    ResultVo<InquiryApplyVerifyReurn> addInquiry(@RequestBody InquiryApplyAddParam inquiryApplyAddParam);

    /**
     * 门户调用 催促新增，集合采购和订单
     * 给门户的
     */
    @RequestMapping(value = "/do/inquiry/handle/salesInquiryAddNew", method = RequestMethod.POST)
    ResultVo<InquirySalesApplyReurn> addInquiryApply(@RequestBody InquiryApplyAddParam inquiryApplyAddParam);

    /**
     *  门户调用，采购催促 结果信息获取
     */

    /**
     * 新增是否节假日接口，提供给门户
     * @param info
     * @return
     */
    @RequestMapping(value = "/do/inquiry/handle/isWorkday", method = RequestMethod.POST)
    ResultVo<Boolean> isWorkday(@RequestBody InquiryWorkdayCondition info);

    /**
     * bug14109 提供给门户不用供应商返回查询原因的清单
     * @param suppily
     * @return
     */
    @RequestMapping(value = "/do/inquiry/apply/getReasonBySuppily", method = RequestMethod.POST)
    ResultVo<List<OpsInquiryCodeConfig>> getReasonBySuppily(@RequestBody String suppily);


    /**
     * bug14109 提供给门户不用供应商返回查询原因的清单,批量接口
     * @param suppilyList
     * @return
     */
    @RequestMapping(value = "/do/inquiry/handle/getReasonBySuppilyBatch", method = RequestMethod.POST)
    ResultVo<List<InquiryCodeConfigBySuppily>> getReasonBySuppilyBatch(@RequestBody List<String> suppilyList);


    /**
     * 订单催促，前端选择货期之后，进行校验，返回前端校验结果
     * @return
     */
    @RequestMapping(value = "/do/inquiry/handle/deliveryDataVerify", method = RequestMethod.POST)
    ResultVo<List<InquiryOrderMasterDto>> deliveryDataVerify(@RequestBody List<InquiryApplyAddParam> requestDtos);

    /**
     * 提供给门户 ops 查询接口，其中，去除门户申请的部分
     * @param inquiryApplyRequest
     * @param page
     * @return
     */
//    @RequestMapping(value = "/do/inquiry/apply/findAll", method = RequestMethod.POST)
//    ResultVo<PageInfo<OpsInquiryApply>> getinquiryApply(@RequestBody InquiryApplyRequest inquiryApplyRequest, Page page);
//
//
//    @RequestMapping(value = "/do/inquiry/apply/fetchReplyList", method = RequestMethod.POST)
//    ResultVo<List<OpsInquiryDetail>> getinquiryReply(@RequestBody String applyNo);

}
