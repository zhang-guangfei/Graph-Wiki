package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2025-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bin_order_detail_ording_info")
public class BinOrderDetailOrdingInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("calc_id")
    private Long calcId;

    @TableField("bin_detail_id")
    private Long binDetailId;

    @TableField("order_type")
    private String orderType;

    @TableField("order_no")
    private String orderNo;

    @TableField("item_no")
    private Integer itemNo;

    @TableField("split_no")
    private Integer splitNo;

    @TableField("modelno")
    private String modelno;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("quantity")
    private Integer quantity;

    @TableField("orderDate")
    private Date orderDate;

    @TableField("expDate")
    private Date expDate;

    @TableField("supplier")
    private String supplier;

    @TableField("trans_type")
    private String transType;

    @TableField("inQty")
    private Integer inQty;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;


    public String getOrderFno(){
        if (splitNo != null && splitNo != 0) {
            return String.join("-", orderNo, itemNo.toString(), splitNo.toString());
        } else if (itemNo != null && itemNo != 0) {
            return String.join("-", orderNo, itemNo.toString());
        }else {
            return orderNo;
        }
    }
}
