package com.smc.smccloud.model.trans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TransOrderVO {

    private Long id;

    private String transNo;

    /**
     * 1-调库 2组换
     */
    private Integer transType;

    private Integer itemNo;

    private String modelNo;

    private Integer quantity;

    private Integer status;

    /**
     * 来源单号
     */
    private String fromNo;

    /**
     * 来源id
     */
    private Long fromId;

    /**
     * 来源类型: 1-bin补库; 2-调库申请; 3-先行在库申请; 4-委托在库;
     */
    private Integer fromType;

    private Long fromInventoryPropertyId;

    /**
     * 仓库代码
     */
    private String fromWarehouseCode;

    /**
     * 库存类型
     */
    private String fromInventoryTypeCode;

    /**
     * PPL代码
     */
    private String fromPpl;

    /**
     * 项目号
     */
    private String fromProjectCode;

    /**
     * 客户群代码
     */
    private String fromGroupCustomerNo;

    /**
     * 营业情报号
     */
    private String fromSalesInfoNo;

    /**
     * 客户代码
     */
    private String fromCustomerNo;

    private Long toInventoryPropertyId;

    private String toWarehouseCode;

    private String toInventoryTypeCode;

    private String toPpl;

    private String toProjectCode;

    private String toGroupCustomerNo;

    private String toSalesInfoNo;

    private String toCustomerNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private Date wmsDlvDate;//指定物流交货期

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    private String invoiceNo;

    private Integer shipQty;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shipDate;

    private Long fromInventoryId;

    private String corderNo;
    private String cproductNo;
}
