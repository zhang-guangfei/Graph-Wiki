package com.smc.smccloud.model.ips;

import lombok.Data;

/**
 * @description:
 * "enduserVipCode": "",
 * "enduserVipPriority": ""
 * "enduserOrderNo": "",//最终用户订单号
 * "enduserItemNo":"",//最终用户订单项号
 * "enduserModelNo":"",//最终用户型号，如代理店型号
 * "warehouseCode": "",
 * "enduserGateNo": "",//最终用户的入库号，如美国入库号
 * "enduserZoneMark": "", //地域号
 * "enduserSortNo": "", //分类编号
 * "enduserShelfNo "",//最终用户货架号，如代理店货架号
 * "enduserRemarks": ""
 * "customerNs": "",// 南北方客户
 * @author: B91717
 * @time: 2024/12/17 16:38
 */
@Data
public class EnduserInfoDto {

    private String enduserVipCode; // VIP代码
    private String enduserVipPriority; // VIP优先级
    private String enduserOrderNo; // 最终用户订单号
    private String enduserItemNo; // 最终用户订单项号
    private String enduserModelNo; // 最终用户型号，如代理店型号

    private String kogo; // 客户工号(日本工号)

    private String warehouseCode; // 仓库代码
    private String enduserGateNo; // 最终用户的入库号，如美国入库号
    private String enduserZoneMark; // 地域号
    private String enduserSortNo; // 分类编号
    private String enduserShelfNo; // 最终用户货架号，如代理店货架号
    private String enduserRemarks; // 备注
    private String customerNs; // 南北方客户

    private String purchaseEnduserNo;


}
