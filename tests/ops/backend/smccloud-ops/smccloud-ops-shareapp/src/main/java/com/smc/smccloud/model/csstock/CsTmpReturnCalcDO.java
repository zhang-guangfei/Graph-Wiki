package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "TMP_CsReturnCalc")
public class CsTmpReturnCalcDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("agent_no")
    private  String agentNo;
    @TableField("warehouse_code")
    private  String warehouseCode;
    @TableField("model_no")
    private  String modelNo;

    @TableField("return_qty")
    private  Integer returnQty;
    @TableField("overTime_qty")
    private  Integer overTimeQty;
    @TableField("overCtt_qty")
    private  Integer overCttQty;
    @TableField("over_day")
    private Integer overDay;
    @TableField("init_stock_qty")
    private Integer initStockQty;
    @TableField("qtyOnhand")
    private Integer qtyOnhand;
    @TableField("returning_qty")
    private Integer returningQty;
    @TableField("qtyprepare")
    private Integer qtyprepare;

    @TableField("update_Time")
    private Date updateTime;

    @TableField("EPrice")
    private BigDecimal ePrice;

    @TableField("IsBinModel")
    private Integer isBinModel;

    @TableField("LastImpDate")
    private Date lastImpDate;

    @TableField("customers")
    private Integer customers;

    @TableField("orders")
    private Integer orders;

    @TableField("applyType")
    private Integer applyType;

    @TableField("DeptNo")
    private String deptNo;

    @TableField("MinShipDate")
    private Date minShipDate;
}
