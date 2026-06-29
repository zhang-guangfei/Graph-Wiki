package com.smc.smccloud.model.OrderSales;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderSalesDTO implements Serializable {

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
     * 货齐出货: 0-随到随发; 1-货齐-单仓; 2-随发-分批; 3-货齐-多仓;
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

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 订单类别
     */
    @NotNull
    private Integer typeCode;

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
     * Holon代码
     */
    private String HLCode;

    /**
     * 采购单号
     */
    private String purchaseNo;

    /**
     * 是否有合同
     */
    private String cttFlag;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 是否价格证
     */
    private Boolean priceChecked;

    /**
     * 交易主体
     */
    @NotNull
    private String tradeCompanyId;

    private Date createTime;

    private String updateUser;

    private String rorderNo;

    private String endUser;

    private Date updateTime;

    private String createUser;

    @Valid
    @NotEmpty
    private List<OrderSalesDetailVO> items;

    /**
     * 收货信息
     */
    @Valid
    @NotNull
    private OrderDlvDataVO addressInfo;


    /**
     * 1-门户队列 2-中国制造 3 -广州制造
     */
    private int fromType;

    private String deliveryDeptNo;

    /**
     * 客户群号（如果要多个以","分隔）
     */
    private String groupCustomerNo;

    /**
     * 赊销表示
     */
    private String creditSaleClientFlag;

}
