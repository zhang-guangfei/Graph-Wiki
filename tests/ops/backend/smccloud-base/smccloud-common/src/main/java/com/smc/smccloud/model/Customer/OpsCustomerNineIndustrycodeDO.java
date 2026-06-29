package com.smc.smccloud.model.Customer;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_customer_nine_industryCode")
public class OpsCustomerNineIndustrycodeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户代码
     */
    @TableField("CustomerNo")
    private String CustomerNo;

    /**
     * 序号
     */
    @TableField("OrderNumber")
    private Integer OrderNumber;

    /**
     * 三位行业代码
     */
    @TableField("IndustryMediamCode")
    private String IndustryMediamCode;

    /**
     * 四位行业代码
     */
    @TableField("Industry4")
    private String Industry4;

    /**
     * 五六位行业代码
     */
    @TableField("Industry56")
    private String Industry56;

    /**
     * 权重占比
     */
    @TableField("IndustryPercent")
    private Integer IndustryPercent;

    @TableField("CreatedUser")
    private String CreatedUser;

    @TableField("UpdatedTime")
    private Date UpdatedTime;

    @TableField("CreatedTime")
    private Date CreatedTime;

    @TableField("UpdatedUser")
    private String UpdatedUser;


}
