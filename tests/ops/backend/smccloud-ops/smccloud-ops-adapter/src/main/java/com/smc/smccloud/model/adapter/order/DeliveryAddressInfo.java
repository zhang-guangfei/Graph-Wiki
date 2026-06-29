package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "发货地址实体")
public class DeliveryAddressInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2610597422801003531L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "地址ID")
    private String addressId;

    @ApiModelProperty(value = "地址")
    private String dlvAddress;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "邮政编码")
    private String postCode;

    @ApiModelProperty(value = "身份证号，可以为空")
    private String personId;

    @ApiModelProperty(value = "发货方式-直发客户/直发营业所（1：直发客户；2：直发营业所;3:自提)")
    private String dlvFlag;

    /**
     * 新建时不需要传值，修改时传值 0表示修改现有订单地址，1表示使用现有订单地址
     */
    @ApiModelProperty(value = "是否为现在订单地址")
    private String isDefault;

    @ApiModelProperty(value = "营业所代码")
    private String deptNo;

    @ApiModelProperty(value = "客户代码")
    private String customerNo;

    @ApiModelProperty(value = "操作人代码")
    private String employeeNo;

    @ApiModelProperty(value = "ERP订单号7位，适配器这边自用字段，不需要传")
    private List<String> orderNo;

    @ApiModelProperty(value = "集约方式代码")
    private String intensiveNo;

    @ApiModelProperty(value = "省编码")
    private String province;

    @ApiModelProperty(value = "市编码")
    private String city;

    @ApiModelProperty(value = "区编码")
    private String region;

    @ApiModelProperty(value = "邮编")
    private String postcode;

    @ApiModelProperty(value = "承运商: 物流自选, 顺丰特快")
    private String carrier;

    // 2023-10-27 新增
    @ApiModelProperty(value = "邮箱")
    private String email;

}
