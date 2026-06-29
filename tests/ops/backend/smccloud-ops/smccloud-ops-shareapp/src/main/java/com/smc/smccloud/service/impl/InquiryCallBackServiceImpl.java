package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.sales.ops.dto.inquiry.SalesInquiryReturnEnum;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.OpsOrderResultBean;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.service.InquiryCallBackService;
import com.smc.smccloud.service.OrderModifyServiceFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author B91717
 * @date 2022/5/11
 * @apiNote
 */
@Service
public class InquiryCallBackServiceImpl implements InquiryCallBackService {

    @Resource
    private OrderModifyServiceFeignApi orderModifyServiceFeignApi;

    /**
     * 回写催促回调信息到task表
     *
     * @param list
     * @return
     */
    @Override
    public ResultVo<String> updateToTask(List<InquiryApplyVerifyReurn> list) {
        ResultVo<String> oneResult;
        int successNum = 0;
        int failNum = 0;
        StringBuilder errMsg = new StringBuilder();
        for (InquiryApplyVerifyReurn inquiryApplyVerifyReurn : list) {
            if (StringUtils.isNotBlank(inquiryApplyVerifyReurn.getBatchNo()) && StringUtils.isNotBlank(inquiryApplyVerifyReurn.getStatus())) {
                if (inquiryApplyVerifyReurn.getStatus().equals(SalesInquiryReturnEnum.REPLYSUCCESS.getType())) {
                    oneResult = sendMsgToOrderTask(inquiryApplyVerifyReurn, false, null, inquiryApplyVerifyReurn.getStatusDescription());
                } else {
                    oneResult = sendMsgToOrderTask(inquiryApplyVerifyReurn, true, inquiryApplyVerifyReurn.getStatusDescription(), null);
                }
                if (oneResult.isSuccess()) {
                    successNum++;
                } else {
                    failNum++;
                    errMsg.append(oneResult.getMessage());
                }
            }
        }
        return ResultVo.success("催促信息回调成功，共计回写成功" + successNum + "条；回写失败" + failNum +"条，错误信息 " +errMsg.toString());
    }

    private ResultVo<String> sendMsgToOrderTask(InquiryApplyVerifyReurn inquiryReturn, Boolean iserror, String errorMsg, String text) {
        UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        upTaskInfoVO.setBatchNo(inquiryReturn.getBatchNo());
        upTaskInfoVO.setOptUserNo(inquiryReturn.getReplyUser());
        if (iserror) {
            // 异常状态
            upTaskInfoVO.setHandleStatus("9");
            // 异常信息
            upTaskInfoVO.setErrorMsg(errorMsg);
            dealReturnOpsParamVO = conventCallBackParam(0, inquiryReturn.getInquiryApplyNo(), 10, inquiryReturn.getOrderNo() + errorMsg);
        } else {
            // 处理成功状态
            upTaskInfoVO.setHandleStatus("1");
            dealReturnOpsParamVO = conventCallBackParam(1, inquiryReturn.getInquiryApplyNo(), 10, inquiryReturn.getOrderNo() + text);
        }
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
        vo.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(vo));
        upTaskInfoVO.setReturnStatus("0");
        return orderModifyServiceFeignApi.upTaskInfoByBatchNo(upTaskInfoVO);
    }

    public DealReturnOpsParamVO conventCallBackParam(int isSuccess, String applyNo, int applyType, String desc) {
        // 最外层实体
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(applyType);

        OpsOrderResultBean bean = new OpsOrderResultBean();
        bean.setApplyNo(applyNo);
        bean.setResultDescription(desc);
        if (isSuccess == 1) {
            bean.setResult("成功");
        } else if (isSuccess == 0) {
            bean.setResult("失败");
        } else if (isSuccess == 2) {
            bean.setResult("确认中");
            bean.setResultDescription("暂不处理");
        }
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
        vo.setData(bean);
        DealReturnOpsParam param = new DealReturnOpsParam();
        param.setOpsSalesCommonParamVo(vo);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        return dealReturnOpsParamVO;
    }
}
