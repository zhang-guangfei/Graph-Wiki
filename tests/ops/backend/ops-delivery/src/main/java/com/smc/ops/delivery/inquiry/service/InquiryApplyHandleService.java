package com.smc.ops.delivery.inquiry.service;

import com.sales.ops.dto.inquiry.*;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 催促处理申请方法
 */
public interface InquiryApplyHandleService {

    /**
     * 采购催促的校验
     * 根据完整单号来校验
     * @param orderList
     * @return
     */
    public ResultVo<List<InquiryApplyVerifyReurn>> purchaseInquiryVerify(List<String> orderList,String type)  throws Exception;

    /**
     * 订单催促的校验接口
     * 可以催促时，展示拆分信息列表，不可催促时，前端提示不可催促原因，格式同采购催促校验
     * @param ordernoList
     * @param inquiryType
     * @return
     */
    public ResultVo<List<InquiryOrderVerifyReurn>> orderInquiryVerify(List<String> ordernoList, String inquiryType) throws Exception ;

    /**
     * 订单催促货期，实时校验接口,存在批量导入的校验功能
     */
    public ResultVo<List<InquiryOrderMasterDto>> deliveryDateInquiryVerify(List<InquiryApplyAddParam> requestList) throws Exception;


    /**
     * 订单催促新增接口
     * @param list
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> orderInquiryAdd(List<InquiryApplyAddParam> list) throws Exception;
    /**
     * ops-催促模块前端调用
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> purchaseInquiryAdd(List<InquiryApplyAddParam> list) throws Exception;


    @Transactional(rollbackFor = Exception.class)
    public ResultVo<InquirySalesApplyReurn> salesInquiryAddNew(InquiryApplyAddParam inquiryApplyAddParam) throws Exception;

    /**
     * 门户调用催促模块新增
     * @param inquiryApplyAddParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<InquiryApplyVerifyReurn> salesInquiryAdd(InquiryApplyAddParam inquiryApplyAddParam) throws Exception;

    public  String generateApplyNo(String inquiryType);

    /**
     * 新增是否节假日接口，提供给门户
     * @param info
     * @return
     */
    ResultVo<Boolean> getWorkday(InquiryWorkdayCondition info);

    /**
     * 根据不同供应商返回可选的催促原因配置
     */
    ResultVo<List<InquiryCodeConfigBySuppily>> getReasonBySuppilyBatch(List<String> suppilyList);


}
