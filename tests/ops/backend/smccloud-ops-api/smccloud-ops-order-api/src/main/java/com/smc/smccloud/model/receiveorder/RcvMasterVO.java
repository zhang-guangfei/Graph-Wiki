package com.smc.smccloud.model.receiveorder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 *
 * </p>
 *
 * @author smc
 * @since 2021-11-02
 */
@Data
public class RcvMasterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接单号
     */
    @NotEmpty
    private String rorderNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 最终用户
     */
    @NotEmpty
    private String endUser;

    /**
     * 接单日期
     */
    private Date rordDate;

    /**
     * 货齐出货
     */
    private String dlvEntire;

    /**
     * 交货地点
     */
    private String dlvSite;

    /**
     * 运费负担
     */
    private String transFee;

    /**
     * 国内运输方式
     */
    // private String transChannel;

    /**
     * 项目代码
     */
    private String prjCode;

    /**
     * 原接单号
     */
    private String orOrderNo;

    /**
     * 出货方式
     */
    private String dlvType;

    /**
     * 外勤
     */
    private String employee;

    /**
     * 内勤
     */
    private String employeeNo;

    /**
     * 处理时间
     */
    private Date optTime;

    /**
     * 操作担当
     */
    private String operator;

    /**
     * 营业所代码
     */
    private String deptNo;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 报价单号
     */
    private String quotationNo;

    /**
     * 客户采购单号
     */
    private String purchaseNo;

    /**
     * HL编码
     */
    private String hlCode;

    // 收货地所在营业所代码
    private String deliveryDeptNo;

    /**
     * 订单的SMC交易主体
     */
    @NotEmpty
    private String tradeCompanyId;

    private String createUser;

    // private String corderNo;

    private Long id;

    private Date updateTime;

    private Boolean priceChecked;

    private Date createTime;

    private String updateUser;

}
