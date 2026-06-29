package com.smc.smccloud.model.sampleorder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SampleOrderVO {
    /**
     * id
     */
    private Long id;

    private Long sdId;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 接单订单号
     */
    private String orderNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 客户单号
     */
    private String corderNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 申请部门
     */
    private String applyDeptNo;

    /**
     * 申请类型
     */
    private String applyType;

    /**
     * 成本结算类型
     */
    private String costType;

    /**
     *  申请类别名称
     */
    private String applyTypeName;

    /**
     * 结算类型名称
     */
    private String costTypeName;

    /**
     * 申请目的
     */
    private String purpose;

    /**
     * 部门
     */
    private String deptNo;

    /**
     * 出货方式
     */
    private String dlvEntire;

    /**
     * 出货类别1
     */
    private String dlvType1;

    /**
     * 出货类别2
     */
    private String dlvType2;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收货地址类别
     */
    private Integer addressType;

    /**
     * 地址编号
     */
    private String addressNo;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 生成订单时间
     */
    private Date orderDate;

    /**
     * 回复说明
     */
    private String answerText;

    /**
     * 处理人
     */
    private String answerPsn;

    /**
     * 处理时间
     */
    private Date answerTime;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    /**
     * 申请人
     */
    private String applyPsn;


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

    private String tradeCompanyId;



    private Long applyId;

    /**
     * 项号
     */
    private Integer itemNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 客户型号
     */
    private String cmodelNo;

    /**
     * 希望货期
     */
    private Date dlvDate;


    private Integer costStatus; // 结算状态

    private Date costTime; // 结转时间

    private String invoiceType; // 发票类型（前缀）

    private String invoiceNo; // 发票号

}
