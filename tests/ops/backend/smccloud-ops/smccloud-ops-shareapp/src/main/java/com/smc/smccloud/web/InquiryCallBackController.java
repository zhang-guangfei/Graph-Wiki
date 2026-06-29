package com.smc.smccloud.web;

import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.InquiryCallBackService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author B91717
 * @date 2024/01/29
 * @apiNote
 */
@RestController
@RequestMapping(value = "/shareapp/opsInquiry")
public class InquiryCallBackController {
    @Resource
    private InquiryCallBackService inquiryCallBackService;

    /**
     * 催促结果回调
     */
    @PostMapping("/replyToTask")
    public ResultVo<String> replyToTask(@RequestBody List<InquiryApplyVerifyReurn> list) {
        return inquiryCallBackService.updateToTask(list);
    }

}
