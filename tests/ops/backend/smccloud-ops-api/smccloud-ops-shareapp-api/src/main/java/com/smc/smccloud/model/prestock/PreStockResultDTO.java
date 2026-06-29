package com.smc.smccloud.model.prestock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2021-11-20 17:00
 * Description:
 */
@Data
public class PreStockResultDTO {

    private Long id;

    /**
     * prestock_apply.id
     */
    private Long applyId;

    private String modelNo;

    /**
     * 1-采购(生成采购订单);
     * 2-BIN采购(生成采购订单);
     * 3-预约BIN在途(预约到在途订单);
     * 4-调库;
     * 5-异仓调拨;
     * 9-不备库;
     */
    private String processType;

    private String orderNo;
    /**
     * 1）调库数量
     * 2）预占数量
     * 3）采购数量
     */
    private Integer orderQty;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDateJp;

    /**
     * prestock_detail.id
     */
    private Long fromId;

    /**
     * prostock_detaild对应的申请型号
     */
    private String applyModelNo;

    /**
     * 执行时间
     */
    private String execTime;

    /**
     * 1)取数：处理状态: 1-待处理; 2-已处理; 4-取消处理;
     * 2）预占：ops_inventory_move.inventory_status
     */
    private String optStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项号
     */
    private Integer itemNo;

    /**
     * 1）采购： 运输方式 (采购时默认为 0-海运)
     *2）调拨：是否异仓调拨，1为异仓调拨，0：只能同仓库调拨，不可异仓调拨
     */
    private String transType;

    /**
     * 调入备库仓库
     */
    private String toWarehouseCode;
    /**
     * 调入库存类型
     */
    private String toInventoryTypeCode;
    /**
     * 调入客户代码
     */
    private String toCustomerNo;
    /**
     * 调入ppl
     */
    private String toPplNo;
    /**
     * 调入项目号
     */
    private String toProjectNo;
    /**
     * 调入客户群代码
     */
    private String toGroupCustomerNo;
    /**
     * 调入营业情报号
     */
    private String toSalesInfoNo;

    /**
     * 1）调出 订向库房,
     * 2）调拨/调库为仓库代码
     * 3) 预占在途备库仓库代码
     * 4）采购为供应商代码
     */
    private String orderStock;


    /**
     * 0-正常, 1-主板, 2-组装原件
     */
    private String specMark;

    /**
     * 0-rohs10; 9-其他
     */
    private String productTag;

    /**
     * 1)调出 ops_inventory.inventory_id
     * 2)预点 ops_inventory_move.inventory_id
     */
    private Long fromInventoryId;
    /**
     * 调出库存属性ID
     */
    private Long fromPropertyId;
    /**
     * 调出库存类型
     */
    private String fromInventoryTypeCode;
    /**
     * 调出客户代码
     */
    private String fromCustomerNo;
    /**
     * 调出ppl
     */
    private String fromPplNo;
    /**
     * 调出项目号
     */
    private String fromProjectNo;
    /**
     * 调出客户群代码
     */
    private String fromGroupCustomerNo;
    /**
     * 调出营业情报号
     */
    private String fromSalesInfoNo;
    /**
     * 预约关联单号
     */
    private String associateNo;
    private Integer associateNoItem;
    private Integer associateNoSplit;

    private Boolean prepareOrderFlag = false;

}
