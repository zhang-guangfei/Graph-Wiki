package com.smc.smccloud.model.inventory;

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
 * 在库属性表
 * </p>
 *
 * @author smc
 * @since 2021-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_inventory_property")
public class OpsInventoryPropertyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "inventory_property_id", type = IdType.AUTO)
    private Long inventoryPropertyId;

    /**
     * 库存分类：顾客在库，战略在库、通用在库
     */
    @TableField("inventory_type_code")
    private String inventoryTypeCode;

    /**
     * 客户号
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * PPL代码
     */
    @TableField("ppl")
    private String ppl;

    /**
     * 项目号
     */
    @TableField("project_code")
    private String projectCode;

    /**
     * 客户群代码
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    /**
     * 增加版本号更新库存，防止并发数量异常更新
     */
    @TableField("version")
    private Long version;

    /**
     * 删除标识：0未删除,1删除
     */
    @TableField("delflag")
    private Integer delflag;

    /**
     * 创建时间
     */
    @TableField("cre_time")
    private Date creTime;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;

    /**
     * 修改人
     */
    @TableField("modifier")
    private String modifier;

    /**
     * 营业情报号
     */
    @TableField("sales_info_no")
    private String salesInfoNo;

    /**
     * 营业情报号过期日期
     */
    @TableField("exp_date")
    private Date expDate;

    /**
     * 必填
     */
    @TableField("uid")
    private String uid;
}
