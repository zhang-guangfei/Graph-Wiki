package com.smc.smccloud.model.prestock;

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
 * @since 2022-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product_bom")
public class ProductBomDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    @TableField(value = "bomId")
    private Long bomId;

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String ModelNo;

    /**
     * 优先整型号采购
     */
    @TableField("Priority_Complete")
    private Boolean priorityComplete;

    /**
     * 优先级别，越小级别越高，默认1
     */
    @TableField("Priority_level")
    private Integer priorityLevel;

    /**
     * 是否下发wms  0：初始,1：已下发，2：失败
     */
    @TableField("is_wms")
    private Integer isWms;

    /**
     * 是否有效
     */
    @TableField("IsValid")
    private Boolean IsValid;

    @TableField(exist = false)
    private String UpdateUser;

    @TableField(exist = false)
    private String CreateUser;

    @TableField(exist = false)
    private String UpdateTime;

    @TableField(exist = false)
    private String CreateTime;


}
