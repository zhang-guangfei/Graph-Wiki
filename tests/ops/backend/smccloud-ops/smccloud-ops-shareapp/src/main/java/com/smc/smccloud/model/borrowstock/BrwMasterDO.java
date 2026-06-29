package com.smc.smccloud.model.borrowstock;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("brw_master")
public class BrwMasterDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 借货单号
     */
    private String brwNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 出货方式
     */
    private String dlvType;

    /**
     * 借货类别
     */
    private String brwType;

    /**
     * 借货类别名称
     */
    private String brwTypeName;

    /**
     * 收货地址编号
     */
    private String addressNo;

    /**
     * 借货业务员
     */
    private String salesPsn;

    /**
     * 业务员电话
     */
    private String salesPsnTel;

    /**
     * 借货人
     */
    private String brwPsn;

    /**
     * 借货部门
     */
    private String brwPsnDept;

    /**
     * 借货人电话
     */
    private String brwPsnTel;

    /**
     * 借货目的
     */
    private String purpose;

    /**
     * 备注
     */
    private String remark;

    /**
     * 借货内勤
     */
    private String employee;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 通过时间
     */
    private Date passDate;

    /**
     * 运费承担
     */
    private Integer transFee;

    /**
     * 运输方式
     */
    private Integer transType;

    @TableField( value = "create_time" , fill = FieldFill.INSERT)
    private Date createTime;

    @TableField( value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    private String createUser;

    private String updateUser;

    /**
     * 收货人地址
     */
    private String receiverAddress;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货人
     */
    private String receiverName;

    /**
     * 收货公司
     */
    private String receiverCompany;

    /**
     * 预计归还时间
     */
    private Date esReturnDate;
}
