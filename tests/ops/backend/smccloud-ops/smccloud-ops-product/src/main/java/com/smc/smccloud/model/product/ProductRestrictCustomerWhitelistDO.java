package com.smc.smccloud.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product_restrict_customer_whitelist")
public class ProductRestrictCustomerWhitelistDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主要客户，1代表主要客户，0代表次要客户，默认为1
     */
    @TableField("MainCustomer")
    private Boolean MainCustomer;

    /**
     * 添加人
     */
    @TableField(exist = false)
    private String CreateId;

    /**
     * 创建时间
     */
    @TableField(exist = false)
    private String CreateTime;

    @TableField(exist = false)
    private String UpdateTime;

    @TableId(value = "CustomerNo")
    private String CustomerNo;

    @TableField("ModelNo")
    private String ModelNo;

    @TableField("is_delete")
    private Boolean isDelete;

    @TableField(exist = false)
    private String UpdateUser;

    @TableField("Remark")
    private String Remark;


}
