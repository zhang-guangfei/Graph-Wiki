package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2022/4/12 16:46
 */
@Data
public class CsImportDataDTO {
    private Integer id;
    private String agentNo;
    private String warehouseCode;
    private String orderNo;
    private Integer status;
    private Date impDate;
    private Date shipDate;
    private String modelNo;
    private Integer quantity;
    private Integer returnQty;
    private Integer expQty;
    private Date createTime;
    private Date updateTime;
    private String updateUser;
    private String createUser;
    private String barcode;
    private String caseNo;
    private String deliveryNo;
    private String pplNo;
    private String projectNo;
    private String receivePsn;
    private Date receiveTime;
    private Integer impType;
    private String userNo;
    private String locationNo;
    private String invoiceNo;
    private Long invoiceId;
    private String roId;

}
