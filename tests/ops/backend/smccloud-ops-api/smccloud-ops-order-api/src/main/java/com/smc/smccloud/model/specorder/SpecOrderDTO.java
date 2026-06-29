package com.smc.smccloud.model.specorder;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SpecOrderDTO {

    private Integer id;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 下单日期
     */
    private Date orderDate;

    /**
     * 备注
     */
    private String remark;


    /**
     * 客户订单号
     */
    private String corderNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 订单的SMC交易主体
     */

    private String tradeCompanyId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单全号
     */
    private String fullOrderNo;

    private Date updateTime;

    private Date createTime;

    private String createUser;

    private String updateUser;

    private String groupNo;

    /**
     * 出口方式FOB
     */
    private String exportType;

    private String dlvType;//出货方式

    private String dlvEntire;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverName;

    private String receiverCompany;

    private String exportWarehouse;

    private String complaintNo;

    private String province;

    private String city;

    private String district;

    private List<SpecOrderDetailDTO> list;
}
