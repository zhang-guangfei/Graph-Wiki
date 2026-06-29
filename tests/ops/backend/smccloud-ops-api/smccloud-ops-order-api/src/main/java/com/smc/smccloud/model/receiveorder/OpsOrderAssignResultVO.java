package com.smc.smccloud.model.receiveorder;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-06-25
 */
@Data
public class OpsOrderAssignResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单itemno
     */
    private Integer orderItem;

    /**
     * 分配顺序号
     */
    private Integer seqNo;

    /**
     * 型号
     */
    private String modelno;

    /**
     * 分配数量
     */
    private Integer quantity;

    /**
     * N:在库；P:生产在途； T:采购在途；CG：采购
     */
    private String stockType;

    /**
     * 在库 在途为库房代码，采购为供应商
     */
    private String stockCode;

    /**
     * 库存类别
     */
    private String inventoryTypeCode;

    /**
     * 采购/在途为PO号
     */
    private String associateNo;

    /**
     * po_item号
     */
    private Integer associateNoItem;

    /**
     * 在库为出库库房代码；	在途为在途表的SupplierId；	采购为供应商代码；
     */
    private String supplierId;

    private Long id;

    private String createUser;

    private Date updateTime;

    private Date createTime;

    private String updateUser;

    private Integer associateNoSplitNo;


}
