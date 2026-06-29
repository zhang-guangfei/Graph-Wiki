package com.smc.smccloud.model.customer;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/24 11:31
 * @Descripton TODO
 */
@Data
public class CstoAndUserInfoVO {

    // 客户名称
    private String customerName;
    // 客户担当名称
    private String empSaleName;
    // 用户名称
    private String userName;
    // 最终用户名称
    private String endUserName;
    // 发票类型
    private String invoiceTypeName;
    // 客户类型
    private String cstmType;

}
