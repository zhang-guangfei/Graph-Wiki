package com.smc.smccloud.model.OrderSales;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderSalesMasterVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 客户代码
     */
    private String customerNo;  

    /**
     * 用户代码
     */
    private String userNo;   

    /**
     * 货齐出货
     */
    private String dlvEntire; 

    /**
     * 运费付担
     */
    private String transFee; 

    /**
     * 国内运输方式
     */
    private String transChannel;

    /**
     * 交货地点
     */
    private String dlvSite; 

//    /**
//     * 交货方式
//     */
//    private String dlvType; 

    /**
     * 合同号
     */
    private String contractNo; 

    /**
     * 报价单号
     */
    private String quotationNo; 

    /**
     * 处理日期 下单日期
     */
    private Date workday; 

    /**
     * 项目代码
     */
    private String prjCode;  

    /**
     * 操作日期
     */
    private Date optDate; 

    /**
     * 外勤代码
     */
    private String empSale; 

    /**
     * 内勤代码
     */
    private String empOffice; 

    /**
     * 营业所代码
     */
    private String deptNo;

    /**
     * 采购单号
     */
    private String purchaseNo; 

    /**
     * 是否有合同
     */
    private String cttFlag; 

    /**
     * 是否价格证
     */
    private Boolean priceChecked; 

    /**
     * 交易主体
     */
    private String tradeCompanyId; 

    private Date createTime; 

    private String updateUser; 

    private String rorderNo;     

    private String endUser;  

    private Date updateTime; 

    private String createUser; 

}
