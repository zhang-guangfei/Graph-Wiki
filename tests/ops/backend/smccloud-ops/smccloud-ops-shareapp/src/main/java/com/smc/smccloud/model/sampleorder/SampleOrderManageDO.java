package com.smc.smccloud.model.sampleorder;

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
 * @since 2022-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SampleOrderManage")
public class SampleOrderManageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("deptNo")
    private String deptNo;

    @TableField("createTime")
    private Date createTime;

    @TableField("orderNo")
    private String orderNo;

    @TableField("outQty")
    private Integer outQty;

    @TableField("impQty")
    private Integer impQty;

    @TableField("manager")
    private String manager;

    @TableField("shipDate")
    private Date shipDate;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("qtyOnhand")
    private Integer qtyOnhand;

    @TableField("model_no")
    private String modelNo;

    @TableField("outTime")
    private Date outTime;

    @TableField("updateTime")
    private Date updateTime;

    @TableField("status")
    private Integer status;

    @TableField("remark")
    private String remark;

    @TableField("updateUser")
    private String updateUser;

    @TableField("parentDeptNo")
    private String parentDeptNo;


}
