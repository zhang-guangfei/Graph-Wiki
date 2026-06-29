package com.sales.ops.service.purchase;

import com.sales.ops.dto.util.CommonResult;

import java.util.List;

public interface PurchaseSendEmail {

    /**
     * 日本邮件发送
     * @param path
     * @return
     */
    CommonResult sendMailJP(String path);


    /**
     * 海外邮件发送
     * @param path
     * @return
     */
    List<CommonResult> sendMailOverSea(List<String> path);

    /**
     *
     * @param dealsuppilyid
     * @param message
     * 发单成功后邮件通知业务
     */
    void mailMessage(int dealsuppilyid,String message);

    void PurchaseUnusualMailSend(String msg);

    /**
     * 采购删单异常，邮件提醒
     * @param message
     */
    CommonResult purchaseDelMessage(String message);

    /**
     * 测试邮件发送
     */
    void sendMailTest();
}
