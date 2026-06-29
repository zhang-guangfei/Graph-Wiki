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
@TableName("product_error")
public class ProductErrorDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 产品型号
     */
    @TableField("ModelNo")
    private String ModelNo;

    @TableField("source")
    private String source;

    /**
     * 产品来源（国别代码）
     */
    @TableField(exist = false)
    private String recommendModel;

    /**
     * 是否逻辑删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;

    /**
     * 登陆日期
     */
    @TableField(exist = false)
    private String createdDate;

    /**
     * 创建者
     */
    @TableField(exist = false)
    private String createdUser;

    /**
     * 更新日期
     */
    @TableField(exist = false)
    private String updatedDate;

    /**
     * 更新者
     */
    @TableField(exist = false)
    private String updatedUser;

    @TableField(exist = false)
    private String desc;



    @TableId(value = "id", type = IdType.AUTO)
    private String id;


}
