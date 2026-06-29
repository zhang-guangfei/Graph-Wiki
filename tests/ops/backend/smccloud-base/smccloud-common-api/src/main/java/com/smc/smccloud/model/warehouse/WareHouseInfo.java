package com.smc.smccloud.model.warehouse;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/8/10 8:44
 * @Descripton TODO
 */
@Data
public class WareHouseInfo {

    private String wareHouseCode; // 编码

    private String wareHouseType; // 类型

    private String wareHouseName; // 名称

    private String wareHouseAddress; // 地址

    private String priority; // 优先级

    private String linkMan; // 联系人

    private String linkMail; // 联系人邮箱

    private String linkPhone; // 联系人电话

    private String linkMobile; // 联系人手机号

    private String rcvLinkMan; // 退货收货联系人

    private String rcvLinkTel; // 退货收货人电话

    private String rcvLinkEmail; // 退货联系邮箱

}
