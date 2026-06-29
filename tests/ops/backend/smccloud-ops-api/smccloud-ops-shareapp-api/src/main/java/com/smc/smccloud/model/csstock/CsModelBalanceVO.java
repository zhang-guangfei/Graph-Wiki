package com.smc.smccloud.model.csstock;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *  型号的月结出入数量
 */
@Data
public class CsModelBalanceVO {
    private Long id;
    private String agentNo;
    private String warehouseCode;

    // 客户名称
    private String agentName;
    // 客户类型
    private String customerTypeName;
    // 仓库名称
    private String wareHourseName;

    private String modelNo;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date monthDate;
    private Integer inQty;
    private Integer outQty;
    private Integer returnQty;
    private Integer openingQty;
    private Integer balanceQty;
    private Integer onhandQty;
    private String remark;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    private Integer qtyInvoice;
    private Integer qtyShip;
    private Integer qtyBalanceCost;
    private Integer qtyOpeningCost;

    private String confirmUser;

}
