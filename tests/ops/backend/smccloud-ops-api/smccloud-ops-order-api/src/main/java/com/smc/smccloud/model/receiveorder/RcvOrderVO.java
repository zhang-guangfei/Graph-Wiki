package com.smc.smccloud.model.receiveorder;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2021/12/21 9:50
 * @Descripton TODO
 */
@Data
public class RcvOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 接单号
     */
    @NotEmpty
    private String rorderNo;

    /**
     * 客户代码
     */
    @NotEmpty
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
    @NotEmpty
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
    private String transChannel;

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
     * 是否有合同标识
     */
    private String cttFlag;

    /**
     * HL编码
     */
    private String hlCode;

    /**
     * 订单的SMC交易主体
     */
    @NotEmpty
    private String tradeCompanyId;

    private String createUser;

    private Date updateTime;

    private Boolean priceChecked;

    private Date createTime;

    private String updateUser;

    @Valid
    @NotEmpty
    private List<RcvDetailVO> item;
}
