package com.smc.smccloud.model.supplier;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SupplierVo {

    private String id;
    // 供应商名称
    private String name;
    // 公司id
    private String companyId;
    // 国别代码，用于页面下拉框显示排序
    private String countryCode;
    // 标准交货期
    private String stdDeliveryDay;
    // 是否启用标准货期比较进行计算
    private Integer enableStdDeliveryDay;
    // 是否启用在库数量判断
    private Integer enableInventory;
    // 财务成本系统供应商分类id
    private String fcostcode;
    // 最快交货期天数(假设供应商随时可以出库，到自动化需要的天数)
    private Integer fstDeliveryDay;

    private String fstTransTypeId;

    private Integer shipDeliveryDay;

    private Integer stdProductManuDay;

    private Integer nstdProductManuDay;

    private String fullName;

    // 交易币种
    private String TransCurrency;
    // 结算天数
    private Integer paymentDay;
    // 修改时间
    private Date updateTime;
    // 修改人
    private String updator;

    private BigDecimal exchangeRate;

    private Integer isAutoSend;

    private String email;

    private Integer sort;

}
