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
 * @since 2022-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product_eos_type")
public class ProductEosTypeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("UpdateTime")
    private String UpdateTime;

    @TableId(value = "Id", type = IdType.AUTO)
    private String Id;

    @TableField("Name")
    private String Name;


}
