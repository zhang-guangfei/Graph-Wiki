package com.sales.ops.feign.inquiry;

import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author B91717
 * @date 2024-01-29
 * @apiNote 催促回调结果，回写task
 */
@FeignClient(name = "shareapp-service", contextId = "inquiryTaskReply")
public interface InquiryTaskFeignApi {

    /**
     * 催促回调结果，回写task
     */
    @RequestMapping(value = "/shareapp/opsInquiry/replyToTask", method = RequestMethod.POST)
    ResultVo<String> replyToTask(@RequestBody List<InquiryApplyVerifyReurn> list);

}
