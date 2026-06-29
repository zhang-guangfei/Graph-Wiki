package com.sales.ops.feign.delivery;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * bugid: 11007
 * @author ：C14717
 * @version: 1.0$
 * @description：Opswm
 * @date ：Created in 2023/06/10 13:05
 */
@FeignClient(name = "delivery-server", contextId = "deliveryFeignServer")
public interface DeliveryBarcodeFeignApi {


    /**
     * 获取发货箱码信息
     * @return
     */
    @RequestMapping(value = "/do/read/ops/bar", method = RequestMethod.GET)
    CommonResult<String> getBar();

    /**
     * 发送项码信息邮件
     * @return
     */
    @RequestMapping(value = "/do/send/barcode/save/file/send/mail", method = RequestMethod.GET)
    CommonResult<String> sendBarcodeMail();


    /**
     * bugid:12391 c14717 2023/10/31
     * 根据email字段 分组发邮件
     * 获取发货箱码信息
     * @return
     */
    @RequestMapping(value = "/do/read/ops/emailBar", method = RequestMethod.GET)
    CommonResult<String> getEmailBar();

    /**
     * bugid:12391 c14717 2023/10/31
     * 根据email字段 分组发邮件
     * 发送箱码码信息邮件
     * @return
     */
    @RequestMapping(value = "/do/send/barcode/saveFileByEmail/sendMail", method = RequestMethod.GET)
    CommonResult<String> sendEmailBarMail();



}
