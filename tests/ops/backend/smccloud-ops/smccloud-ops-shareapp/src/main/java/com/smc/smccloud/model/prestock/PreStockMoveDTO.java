package com.smc.smccloud.model.prestock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wuweidong
 * @create 2023/11/20 16:57
 * @description
 */
@Data
public class PreStockMoveDTO {
    /**
     * 关联单号
     */
   private String associateNo;
   private Integer  associateNoItem;
   private Integer associateNoSplit;
    /**
     * inventory_move 的inventory_id
     */
   private Long inventoryID;
    /**
     * 仓库代码
     */
   private String warehouseCode;
    /**
     * 库存状态
     */
   private String inventoryStatus;
    private String  modelNo;
   private Integer quantity;
    /**
     * 预占数量
     */
   private  Integer prepareQty;
    /**
     * 可用数量
     */
   private Integer availableQty;
    /**
     * 批属性ID
     */
   private Long inventoryPropertyId;

    /**
     * 下单日期
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private  Date  dlvDate;
    /**
     * 预计到货时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private  Date  esArrivalDate;

    /**
     * 预计到货时间-先行客户需求时间
     */
    private Integer diffDay;

    /**
     * 仓库补库序次
     */
    private  Integer warehouseIdx;

}
