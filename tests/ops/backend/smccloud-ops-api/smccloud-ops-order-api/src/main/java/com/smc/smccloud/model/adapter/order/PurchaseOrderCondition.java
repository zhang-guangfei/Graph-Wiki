package com.smc.smccloud.model.adapter.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/3/17 11:46
 * @Descripton TODO
 */
@Data
public class PurchaseOrderCondition {
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 用户编码
     */
    private String userNo;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 订单类别
     */
    private String orderType;

    /**
     * 单据状态
     */
    private String billStatus;

    private List<String> statusList;


    private List<String> chinaSupplierList;

    private List<String> notChinasupplierList;

    /**
     * 下单日期 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 下单日期 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;


    private String startOrderDate;
    private String endOrderState;

    /**
     * 到货发票号
     */
    private String invoiceNo;

    /**
     * 操作人/登录人
     */
    private String operator;

    /**
     * 需要查询的部门条件  画面-所属部门
     */
    private String deptCode;

    /**
     * 用户的查询权限(客户、部门、行业、用户)
     */
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;

    /**
     * 通用可查询的部门
     */
    private List<String> commonDeptList;
    /**
     * 查询条件：采购号
     */
    private String purchaseNo;
    /**
     * 查询条件：客户物料号
     */
    private String cProductNo;


    // 查询条件：海外或制造
    private String purchaseType;

    /**
     * 需要查询的部门条件  画面-选择部门
     */
    private List<String> deptCodes;

    /**
     * 制单担当
     */
    private String createId;

    // 排序属性
    private String property = "orderNo";

    // 排序规则
    private String order = "asc";

    // 手配号
    private String supplierOrderNo;

    private Integer preFlag;

    // 1 代理店账号登录查询
    private String agentSearch = "0";

    private Integer exportNum;

    // 是否精准匹配
    private Boolean exactSearchFlag = false;

}
