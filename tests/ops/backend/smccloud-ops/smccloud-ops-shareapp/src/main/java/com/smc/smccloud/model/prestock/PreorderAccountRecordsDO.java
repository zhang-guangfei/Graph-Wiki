package com.smc.smccloud.model.prestock;

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
 * @since 2024-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("preorder_account_records")
public class PreorderAccountRecordsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("inventory_log_id")
    private Long inventoryLogId;

    @TableField("account_qty")
    private int accountQty;

    @TableField("create_time")
    private Date createTime;

    @TableField("order_no")
    private String orderNo;


}
