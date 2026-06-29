package com.smc.smccloud.service;

import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * @author B91717
 * @date 2024/01/29
 * @apiNote 门户催促回调接口
 */
public interface InquiryCallBackService {

    /**
     * 回写催促回调信息到task表
     *
     * @return
     */
    ResultVo<String> updateToTask(List<InquiryApplyVerifyReurn> list);


}
