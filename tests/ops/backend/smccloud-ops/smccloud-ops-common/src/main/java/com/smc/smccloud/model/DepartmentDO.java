package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "department")
public class DepartmentDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认出库物流中心
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 默认出库分仓
     */
    @TableField("sub_warehouse_code")
    private String subWarehouseCode;

    /**
     * 订单的SMC交易主体
     */
    @TableField("trade_companyId")
    private String tradeCompanyid;

    @TableField("EmailAC")
    private String emailAC;

    @TableField("Address")
    private String address;

    @TableField("SuperIntendent")
    private String superIntendent;

    @TableField("DeptENName")
    private String deptENName;

    @TableField("ParentDeptNo")
    private String parentDeptNo;

    @TableField("PostCode")
    private String postCode;

    @TableField("ReportEmail")
    private String reportEmail;

    @TableField("OldDeptNo")
    private String oldDeptNo;

    @TableField("TeleNo")
    private String teleNo;

    @TableField("InsidePsn")
    private String insidePsn;

    @TableField("StockCode")
    private String stockCode;

    @TableField("ProvinceCode")
    private String provinceCode;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("GPSLng")
    private Float gPSLng;

    @TableField("FaxNo")
    private String faxNo;

    @TableField("DeptName")
    private String deptName;

    @TableField("StateCode")
    private String stateCode;

    @TableField("ProvinceName")
    private String provinceName;

    @TableField("EmailAddr")
    private String emailAddr;

    @TableField("GPSLat")
    private Float gPSLat;

    @TableField("DeptNo")
    private String deptNo;

    @TableField("DlvDay")
    private Integer dlvDay;

    @TableField("CityCode")
    private String cityCode;

    @TableField("IsValid")
    private Boolean isValid;

    // 客户订单邮箱
    @TableField("EmailOrder")
    private String emailOrder;

    // 库存订单邮箱
    @TableField("EmailStock")
    private String emailStock;

    // 委托在库邮箱
    @TableField("EmailCSStock")
    private String emailCSStock;

    // 分库在库邮箱
    @TableField("EmailSubStock")
    private String emailSubStock;

    // 专备邮箱(先行在库补库)
    @TableField("EmailUserStock")
    private String emailUserStock;

    // 所长邮箱
    @TableField("EmailDirector")
    private String emailDirector;

}
