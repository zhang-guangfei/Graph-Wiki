package com.smc.smccloud.model.csstock;
import lombok.Data;

import java.util.Date;

@Data
public class CsImpdataVO {

    private Integer id;

    private String agentNo;

    private String warehouseCode;

    private String orderNo;

    private Integer status;

    private Date impDate;

    private Date shipDate;

    private String modelNo;

    /**
     * 入库数量
     */
    private Integer quantity;

    /**
     * 退货数量
     */
    private Integer returnQty;

    /**
     * 可出库数量
     */
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

    private Integer applyType;

    private Date balanceDate;

    private Date balanceCostDate;

    /**
     * 剩余数量
     */
    private Integer leftQty;

    private String roId;

    private Date signTime;

    private String signPsn;
}
