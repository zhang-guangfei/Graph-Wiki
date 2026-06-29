package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bin_order_detail_split")
public class BinOrderDetailSplitDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 生成的单号
     */
    @TableField("order_no")
    private String orderNo;

    @TableField("from_id")
    private Long fromId;

    @TableField("order_type")
    private String orderType;

    @TableField("create_user")
    private String createUser;

    @TableField("model_no")
    private String modelNo;

    @TableField("create_time")
    private Date createTime;

    @TableField("trans_type")
    private String transType;

    @TableField("del_flag")
    private Integer delFlag;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("quantity")
    private Integer confirmQty;

    @TableField("dlv_date")
    private Date dlvDate;

    @TableField("supplier_code")
    private String supplierCode;
    @TableField("status")
    private Integer status;

    @TableField("calc_id")
    private Long calcId;
    @TableField("app_id")
    private Integer appId;
    @TableField("item_no")
    private Integer itemNo;

    @TableField("warehouse_code")
    private String warehouseCode;

}
