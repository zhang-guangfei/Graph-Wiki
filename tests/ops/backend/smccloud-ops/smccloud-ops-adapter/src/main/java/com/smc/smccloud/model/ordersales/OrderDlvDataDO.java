package com.smc.smccloud.model.ordersales;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-01-12 08:43
 * Description:
 */
@Data
@TableName("OrderDlvData")
public class OrderDlvDataDO {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 订单号 (必填)
     */
    @TableField(value = "OrderNo")
    private String orderNo;

    /**
     * 项号
     */
    @TableField(value = "ItemNo")
    private Integer itemNo;

    /**
     * 客户代码
     */
    @TableField("CustomerNo")
    private String customerNo;

    /**
     * 客户名称 (必填)
     */
    @TableField("CstmName")
    private String cstmName;

    /**
     * 完整收货地址 (必填)
     */
    @TableField("DlvAddress")
    private String dlvAddress;

    /**
     * 收货人 (必填)
     */
    @TableField("ContactPsn")
    private String contactPsn;

    /**
     * 收货人联系电话 (必填)
     */
    @TableField("TelNo")
    private String telNo;

    /**
     * 邮政编号
     */
    @TableField("PostCode")
    private String postCode;

    /**
     * 收货方式
     */
//    @TableField("DlvType")
//    private String dlvType;

//    @TableField("DeptNo")
//    private String deptNo;

    /**
     * 收货人身份证号
     */
    @TableField("IDCard")
    private String IDCard;

    /**
     * 到货日期
     */
    // @TableField("ArrivedDate")
    // private Date arrivedDate;

//    @TableField("PreFlag")
//    private String preFlag;

    /**
     * 发货方式-直发客户/直发营业所 (1-直发客户, 2-直发营业所)
     */
    @TableField("DlvFlag")
    private String dlvFlag;

    @TableField(value = "CreateTime", fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    @TableField(value = "UpdateTime", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("CreateUser")
    private String createUser;

    @TableField("UpdateUser")
    private String updateUser;

    /**
     * 省
     */
    @TableField("Province")
    private String province;

    /**
     * 市
     */
    @TableField("City")
    private String city;

    /**
     * 区
     */
    @TableField("District")
    private String district;

    /**
     * 地区编码
     */
//    @TableField("StateCode")
//    private String stateCode;

    /**
     * 承运商Id
     */
    @TableField("CarrierId")
    private String carrierId;

    @TableField("email")
    private String email;

}
