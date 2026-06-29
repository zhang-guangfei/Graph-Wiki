package com.smc.smccloud.model.expdetail;

import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/1/20 15:07
 * @Descripton 发货查询实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExpdetailRequest extends BaseQuery {

    /**
     * 订单类型
     */
    private List<String> orderType;

    /**
     * 发货状态 1-未签收; 2-已签收
     */
    private Integer status;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 出库单号
     */
    private String deliveryNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 完整订单号
     */
    private String orderFno;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 合同订单号
     */
    private String orOrderNo;

    /**
     * 货物条码
     */
    private String barcode;

    /**
     * 客户订单号
     */
    private String corderNo;

    /**
     * 快递单号 （运单号）
     */
    private String expressNo;

    /**
     * 发货仓库 （物流）
     */
    private String warehouseCode;

    /**
     * 写入状态 1-未发货; 2-发货中; 3-发货完成
     */
    private Integer optCode;

    /**
     * 发货时间范围 shipDate from
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    /**
     * 发货时间范围 shipDate to
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    /**
     * 查询权限
     */
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;

    private List<String> deptCodes;

    private String strFromDate;

    private String strToDate;

    private Integer exportNum;

    private String operator;

    // 1 代理店账号登录查询
    private String agentSearch = "0";

    private String modelNo;

    private List<String> warehouseCodes;

    private String cmodelNo;

    private String endUser;

}
