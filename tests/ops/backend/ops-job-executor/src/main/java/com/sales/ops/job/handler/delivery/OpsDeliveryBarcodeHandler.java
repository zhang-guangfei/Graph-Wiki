package com.sales.ops.job.handler.delivery;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.delivery.DeliveryBarcodeFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author C14717
 * bugid: 11007
 * @date 2023/06/10 16:51
 */
@Component
public class OpsDeliveryBarcodeHandler {

    @Autowired
    private DeliveryBarcodeFeignApi barApi;


    // 未开启
    @XxlJob("deliverBarSave")
    public void getBar() {
        CommonResult<String> commonResult = barApi.getBar();
        XxlJobHelper.log("执行完成");
    }

    // 未开启
    @XxlJob("deliverBarSend")
    public void deliverBarSend() {
        CommonResult<String> commonResult = barApi.sendBarcodeMail();
        XxlJobHelper.log("执行完成");
    }

    /**
     * 根据email字段 分组发邮件
     * bugid:12391 c14717 2023/10/31
     */
    @XxlJob("deliverEmailBarSave")
    public void deliverEmailBarSave() {
        CommonResult<String> commonResult = barApi.getEmailBar();
        XxlJobHelper.log("执行完成");
    }

    /**
     * 根据email字段 分组发邮件
     * bugid:12391 c14717 2023/10/31
     */
    @XxlJob("deliverEmailBarSend")
    public void deliverEmailBarSend() {
        CommonResult<String> commonResult = barApi.sendEmailBarMail();
        XxlJobHelper.log("执行完成");
    }

}
