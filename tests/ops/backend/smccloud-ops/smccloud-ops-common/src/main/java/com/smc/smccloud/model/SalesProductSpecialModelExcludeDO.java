package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sales_product_special_model_exclude")
public class SalesProductSpecialModelExcludeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "model_no", type = IdType.AUTO)
    private String modelNo;


}
