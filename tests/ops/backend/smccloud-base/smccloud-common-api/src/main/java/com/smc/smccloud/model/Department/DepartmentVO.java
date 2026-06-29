package com.smc.smccloud.model.Department;

import lombok.Data;

@Data
public class DepartmentVO {

    private static final long serialVersionUID = 1L;

    /**
     * 默认出库物流中心
     */
    private String warehouseCode;

    /**
     * 默认出库分仓
     */
    private String subWarehouseCode;

    /**
     * 订单的SMC交易主体
     */
    private String tradeCompanyid;

    private String emailAC;

    private String address;

    private String superIntendent;

    private String deptENName;

    private String parentDeptNo;

    private String postCode;

    private String reportEmail;

    private String oldDeptNo;

    private String teleNo;

    private String insidePsn;

    private String stockCode;

    private String provinceCode;

    private Integer id;

    private Float gPSLng;

    private String faxNo;

    private String deptName;

    private String stateCode;

    private String provinceName;

    private String emailAddr;

    private Float gPSLat;

    private String deptNo;

    private Integer dlvDay;

    private String cityCode;

    private boolean isValid;

    // 客户订单邮箱
    private String emailOrder;

    // 库存订单邮箱
    private String emailStock;

    // 委托在库邮箱
    private String emailCSStock;

    // 分库在库邮箱
    private String emailSubStock;

    // 专备邮箱(先行在库补库)
    private String emailUserStock;

    // 所长邮箱
    private String emailDirector;
}
