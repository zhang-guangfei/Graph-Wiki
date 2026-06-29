package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2021/10/2 19:22
 * @description：加工类型枚举
 */
public enum MailConfigEnum {

//    JP("order_export@smcjpn.co.jp", "日本订单发送邮箱"),
//    AP("deliveryoverseas@smc.com.cn", "AP订单发送邮箱"),
//    OVERSEA("deliveryoverseas@smc.com.cn","海外其它订单发送邮件");
    //先注释，改成本地邮箱测试发送
    JP("smccnorder@smc.com.cn", "日本订单发送邮箱"),
    AP("smccnorder@smc.com.cn", "AP订单发送邮箱"),
    GN("smccnorder@smc.com.cn","GN订单邮件发送"),
    OVERSEA("smccnorder@smc.com.cn","海外其它订单发送邮件");
    private String type;
    private String desc;

    MailConfigEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
}
