package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/10 11:03
 */
@Data
@TableName("cs_expdetail")
public class CsExpdetailDO {
    private static final long serialVersionUID = 1L;

    /**
     * 出库订单号
     */
    @TableField("exp_order_no")
    private String expOrderNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 出库数量
     */
    @TableField("exp_qty")
    private Integer expQty;

    /**
     * 出库时间
     */
    @TableField("exp_time")
    private Date expTime;

    /**
     * 入库订单号
     */
    @TableField("in_order_no")
    private String inOrderNo;

    /**
     * 状态 1-待出库  2-已出库  4-出库取消
     */
    @TableField("status")
    private Integer status;

    @TableField("update_time")
    private Date updateTime;

    @TableField("corder_no")
    private String corderNo;

    @TableField("ppl_no")
    private String pplNo;

    @TableField("price_enduser")
    private String priceEnduser;

    @TableField("update_user")
    private String updateUser;

    @TableField("remark")
    private String remark;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("project_no")
    private String projectNo;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("price")
    private BigDecimal price;

    @TableField("create_time")
    private Date createTime;

    @TableField("agent_no")
    private String agentNo;

    @TableField("user_no")
    private String userNo;

    @TableField("create_user")
    private String createUser;

    @TableField("cproduct_no")
    private String cproductNo;

    // 库存分类：顾客在库，战略在库、通用在库
    @TableField("allocateStatus")
    private Integer allocateStatus;

    @TableField("balance_date")
    private Date balanceDate;

    @TableField("inventory_id")
    private Long inventoryId;

    @TableField("inventory_type_code")
    private String inventoryTypeCode;

    @TableField("do_id")
    private String doId;

    @TableField("item_type")
    private Integer itemType;

    @TableField("sales_model_no")
    private String salesModelNo;
}
