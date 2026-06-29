package com.smc.smccloud.model.adapter.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/6/30 11:48
 * @Descripton TODO
 */
@Data
public class SplitOrderVO {

    // 发货仓
    private String deliveryWarehouseCode;
    private String deliveryWarehouseCodeName;
    // 拆分单号
    private String orderNo;
    // 型号
    private String modelNo;
    // 数量
    private Integer qty;
    // 在库数
    private Integer qtyIn;
    // 已发数
    private Integer qtyOut;
    // 状态
    private String statusDesc;
    // 备注
    private String remark;
    // 出库区分
    private String stockType;
    private String stockTypeName;
    // 关联单号
    private String associateNo;
    // 承运商id
    private String carrierId;
    // 承运商名称
    private String carrierName;
    // 运单号
    private String expressNo;
    // 出库时间
    private Date handoverTime;
    // 操作时间
    private Date modifyTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectedDeliveryTime;



}
