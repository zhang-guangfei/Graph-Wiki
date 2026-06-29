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
@TableName("product_eos")
public class ProductEosDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ModelNo", type = IdType.AUTO)
    private String ModelNo;
    /**
     * 数据来源（JP:日本,CN:制造,YW:业务,ZL:战略）
     */
    @TableField("dataSource")
    private String dataSource;

    @TableField("eos_type")
    private String eosType;

    /**
     * 开始警告日期
     */
    @TableField("warningDate")
    private String warningDate;

    /**
     * 收敛开始日期，停止接单日期
     */
    @TableField("eos_date")
    private String eosDate;

    /**
     * 推荐型号
     */
    @TableField("modelNo_recommend")
    private String modelnoRecommend;

    /**
     * 优先级
     */
    @TableField("priority_level")
    private String priorityLevel;

    @TableField("describe")
    private String describe;


    @TableField("stopDate")
    private String stopDate;


    @TableField(exist = false)
    private String updateTime;

    @TableField("is_deleted")
    private Boolean isDeleted;

}
