package com.smc.smccloud.model.Customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 纳期客户清单表
 * </p>
 *
 * @author smc
 * @since 2022-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_customer_wldate")
public class OpsCustomerWldateDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户代码
     */
    @TableId(value = "customer_no")
    private String customerNo;

    /**
     * 是否按物流日期发货
     */
    @TableField("is_wldate")
    private Integer isWldate;

    /**
     * 是否删除0默认、1已经删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 创建日期
     */
    @TableField("cre_time")
    private Date creTime;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 更新日期
     */
    @TableField("modify_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyTime;

    /**
     * 更新人
     */
    @TableField("modifier")
    private String modifier;

    /**
     * 增加版本号，防止并发异常更新
     */
    @TableField("version")
    private Integer version;


}
