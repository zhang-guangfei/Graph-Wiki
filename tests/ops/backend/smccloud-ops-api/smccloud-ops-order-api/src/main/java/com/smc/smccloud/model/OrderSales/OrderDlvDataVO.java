package com.smc.smccloud.model.OrderSales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class OrderDlvDataVO {

    private Long id;

    /**
     * 订单号 (必填)
     */
    // @NotEmpty
    private String orderNo;

    // @NotNull
    private Integer itemNo;

    /**
     * 客户代码 (必填)
     */
    //@NotEmpty
    private String customerNo;

    /**
     * 客户名称 (必填)
     */
    //@NotEmpty
    private String cstmName;

    /**
     * 完整收货地址 (必填)
     */
    //@NotEmpty
    private String dlvAddress;

    /**
     * 收货人 (必填)
     */
    //@NotEmpty
    private String contactPsn;

    /**
     * 收货人联系电话 (必填)
     */
    //@NotEmpty
    private String telNo;

    /**
     * 邮政编号
     */
    private String postCode;

    /**
     * 收货方式
     */
    // 废弃掉
    // private String dlvType;

    // 废弃掉
    // private String deptNo;

    /**
     * 收货人身份证号
     */
    private String IDCard;

    /**
     * 到货日期
     */
    // 废弃掉
   //  private Date arrivedDate;

    private String preFlag;

    /**
     * 发货方式-直发客户/直发营业所 (1-直发客户, 2-直发营业所 3自提)
     */
    private String dlvFlag;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    /**
     * 省编码
     */
    private String province;

    /**
     * 市编码
     */
    private String city;

    /**
     * 区编码
     */
    private String district;

    /*
     * 状态
     */
    // 废弃掉
    // private String stateCode;

    /**
     * 承运商ID
     */
    private String carrierId;

    // 2023-10-27 新增
    private String email;

}
