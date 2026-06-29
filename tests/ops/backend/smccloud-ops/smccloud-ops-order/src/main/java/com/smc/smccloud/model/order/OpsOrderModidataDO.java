package com.smc.smccloud.model.order;

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
 * @since 2022-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_order_modidata")
public class OpsOrderModidataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 订单itemno
     */
    @TableField("order_item")
    private Integer orderItem;

    /**
     * 整单号
     */
    @TableField("rorder_fno")
    private String rorderFno;

    /**
     * 日志类别：	C：取消订单；	R：退货；	U: 变更；
     */
    @TableField("type_code")
    private String typeCode;

    /**
     * 原型号
     */
    @TableField("o_modelNo")
    private String oModelno;

    /**
     * 原数量
     */
    @TableField("o_quantity")
    private Integer oQuantity;

    /**
     * 原价格
     */
    @TableField("o_price")
    private Double oPrice;

    /**
     * 原客户货期
     */
    @TableField("o_dlvDate")
    private String oDlvdate;

    /**
     * 变更后型号
     */
    @TableField("n_modelNo")
    private String nModelno;

    /**
     * 变更后数量
     */
    @TableField("n_quantity")
    private Integer nQuantity;

    /**
     * 变更后价格
     */
    @TableField("n_price")
    private Double nPrice;

    /**
     * 变更后客户货期
     */
    @TableField("n_dlvDate")
    private String nDlvdate;

    /**
     * 备注信息
     */
    @TableField("remark")
    private String remark;

    /**
     * 责任人
     */
    @TableField("duty_name")
    private String dutyName;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;


}
