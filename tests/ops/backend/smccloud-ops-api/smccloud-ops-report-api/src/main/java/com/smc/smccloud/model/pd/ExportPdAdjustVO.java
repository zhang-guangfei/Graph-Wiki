package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/8/12 15:32
 * @Descripton TODO
 */
@Data
public class ExportPdAdjustVO {

    @ExcelIgnore
    private Integer id;

    /**
     * 盘点批次号
     */
    @ExcelProperty(value = "盘点批次号")
    private String pdBatchNo;

    @ExcelProperty(value = "仓库")
    private String warehouseCode;

    /**
     * 调整票号
     */
    @ExcelProperty(value = "调整票号")
    private String adjustInvoiceNo;

    /**
     * 调账单号
     */
    @ExcelProperty(value = "调账单号")
    private String adjustNo;

    @ExcelProperty(value = "调账单项号")
    private String adjustItemNo;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号")
    private String modelNo;

    /**
     * 调整数量
     */
    @ExcelProperty(value = "调整数量")
    private Integer adjustQty;

    /**
     * 库存类型
     */
    @ExcelProperty(value = "库存类型")
    private String inventoryType;

    /**
     * 客户代码
     */
    @ExcelProperty(value = "客户代码")
    private String customerNo;

    /**
     * PPL
     */
    @ExcelProperty(value = "PPL")
    private String ppl;

    /**
     * 项目号
     */
    @ExcelProperty(value = "项目号")
    private String projectNo;

    /**
     * 客户群代码
     */
    @ExcelProperty(value = "客户群代码")
    private String groupCustomerNo;

    /**
     * 情报号
     */
    @ExcelProperty(value = "情报号")
    private String salesInfoNo;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String createUser;

    @ExcelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ExcelIgnore
    private int handStatus;
    @ExcelProperty(value = "调账状态")
    private String handStatusName;

    /**
     * 确认人
     */
    @ExcelProperty(value = "确认人")
    private String confirmUser;

    /**
     * 确认时间
     */
    @ExcelProperty(value = "确认时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    @ExcelProperty("备注")
    private String remark;

}
