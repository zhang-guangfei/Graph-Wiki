package com.smc.smccloud.model.csstock;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 入库明细清单
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 11:50
 */
@Data
public class CsImportDetailVO {
    private Integer id;
    private String agentNo;
    private String warehouseCode;
    // 仓库名称
    private String wareHourseName;
    private String orderNo;
    private Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date impDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date shipDate;
    private String modelNo;
    //总数量
    private Integer quantity;
    //已退货数量
    private Integer returnQty;
    //已出库数量
    private Integer expQty;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String updateUser;
    private String createUser;
    private String barcode;
    private String caseNo;
    private String deliveryNo;
    private String projectNo;
    private String userNo;
    private  String pplNo;
    /**
     * 1-入库，2退货
     */
    private  Integer impType;

    private String locationNo;

    private String receivePsn;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    private String invoiceNo;

    private String remark;

    private Integer applyType;
    // 客户名称
    private String agentName;
    // 客户类型
    private String customerTypeName;

    private Date balanceDate;

    private String signStatusName;

    private Date signTime;

    private String signPsn;
}
