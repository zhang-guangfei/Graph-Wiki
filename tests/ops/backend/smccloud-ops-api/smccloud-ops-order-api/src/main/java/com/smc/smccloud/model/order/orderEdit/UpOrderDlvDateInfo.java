package com.smc.smccloud.model.order.orderEdit;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/10/20 14:26
 * @Descripton 变更交货期实体
 */
@Data
public class UpOrderDlvDateInfo implements Serializable {

    private static final long serialVersionUID = -1242493206302174690L;

    // 订单号(10位)
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "物流发货日期")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date warehouseSendDate;


    @ApiModelProperty(value = "客户期望发货日期")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date dlvDate;

    // 操作人
    private String loginUserId;
}
