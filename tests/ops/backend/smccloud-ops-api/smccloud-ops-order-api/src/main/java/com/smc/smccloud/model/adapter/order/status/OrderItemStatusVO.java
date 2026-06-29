package com.smc.smccloud.model.adapter.order.status;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/13 11:01
 * @Descripton TODO
 */
@Data
public class OrderItemStatusVO {

    // 整单号
    private String mainOrderNo;
    // 客户号
    // private String customerNo;
    // 项号
   // private String itemNo;
    // 完整订单号
    private String orderNo;

    // 型号
   //  private String modelNo;
    // 订单状态
    private String orderStatus;
    // 状态编码
    private String orderStatusCode;
    // 出库区分
    private String expInvCode;

    // 是否可更改出货方式
    private Boolean canChangeDeliveryModel;

    // 是否可更改货期
    private Boolean canChangeDeliveryDate;

    // 是否可删单
    private Boolean canDelete;

    // 是否可变更子项特发
    // 订单状态只要是未捡货的 例如 : 对外采购(2), 采购拦截(3),仓间调拨(4),等待出库(5)  且是 拆分型号+随发分批
    private Boolean canUpSpec;

    // 是否装箱
    private Boolean packingFlag;

    // 是否组装波次
    private Boolean chubociFlag;

    // 是否可变更集约
    private Boolean canIntensiveNo;

    private Date latestDeliveryDate;

}
