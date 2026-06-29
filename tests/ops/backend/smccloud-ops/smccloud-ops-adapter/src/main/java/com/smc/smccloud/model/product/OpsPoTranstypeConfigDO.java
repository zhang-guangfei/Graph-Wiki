package com.smc.smccloud.model.product;

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
 * @since 2024-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_po_transtype_config")
public class OpsPoTranstypeConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 1模糊匹配，0完全匹配
     */
    @TableField("matchType")
    private Boolean matchType;

    /**
     * 0:优先使用此运输方式;1:必须使用此运输方式
     */
    @TableField("type")
    private String type;

    /**
     * 是否为大于号
     */
    @TableField("compareGreater")
    private Boolean compareGreater;

    /**
     * 比较时限制的数量
     */
    @TableField("compareQuantity")
    private Integer compareQuantity;

    @TableField("transtype")
    private String transtype;

    @TableField("remark")
    private String remark;

    @TableId(value = "modelNo", type = IdType.INPUT)
    private String modelNo;

    @TableField("updateTime")
    private Date updateTime;


}
