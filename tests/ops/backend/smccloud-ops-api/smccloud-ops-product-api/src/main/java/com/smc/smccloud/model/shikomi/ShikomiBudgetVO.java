package com.smc.smccloud.model.shikomi;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/3/26 14:34
 * @Descripton TODO
 */
@Data
public class ShikomiBudgetVO {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "决算号", index = 0)
    private String batchNo;
    /**
     * 营业所
     */
    @ExcelProperty(value = "营业所", index = 1)
    private String deptNo;

    /**
     * 备库地点
     */
    @ExcelProperty(value = "备库地点", index = 2)
    private String supplierCode;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号", index = 3)
    private String modelNo;

    /**
     * shikomo号
     */
    @ExcelProperty(value = "shikomo号", index = 4)
    private String shikomiNo;

    /**
     * 申请号
     */
    @ExcelProperty(value = "申请号", index = 5)
    private String applyNo;

    /**
     * 申请时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "申请时间", index = 6)
    private Date applyTime;

    /**
     * 申请数量
     */
    @ExcelProperty(value = "申请数量", index = 7)
    private Integer applyQty;

    /**
     * shikomi残数
     */
    @ExcelProperty(value = "shikomi残数", index = 8)
    private Integer remainQty;

    /**
     * 已预约残数
     */
    @ExcelIgnore
    private Integer qtyOrderPre;

    /**
     * shikomi区分
     */
    @ExcelIgnore
    private String classType;

    /**
     * shikomi客户范围
     */
    @ExcelProperty(value = "shikomi客户范围", index = 9)
    private String classCode;

    /**
     * 有订货权限的客户
     */
    @ExcelProperty(value = "有订货权限的客户", index = 10)
    private String avliableCustomerNo;

    /**
     * 归属客户
     */
    @ExcelProperty(value = "归属客户", index = 11)
    private String belongCustomerNo;

    /**
     * 行业代码
     */
    @ExcelProperty(value = "行业代码", index = 12)
    private String indCode;

    /**
     * 未订货月数
     */
    @ExcelProperty(value = "未订货月数", index = 13)
    private Integer qtyNoord;

    /**
     * 在库数量
     */
    @ExcelIgnore
    private Integer qtyOnhand;

    /**
     * 在途数量
     */
    @ExcelIgnore
    private Integer qtyPo;

    /**
     * 近6个月订货数量
     */
    @ExcelProperty(value = "近6个月订货数量", index = 14)
    private Integer avgOrdQty;

    /**
     * shikomi追加需提前
     */
    @ExcelIgnore
    private Integer partPrepareMonths;

    /**
     * 点检状态
     */
    @ExcelIgnore
    private Integer inspectStatus;

    @ExcelProperty(value = "点检状态", index = 15)
    private String inspectStatusName;

    /**
     * 点检类别
     */
    @ExcelIgnore
    private String inspectType;
    @ExcelProperty(value = "点检类别", index = 16)
    private String inspectTypeName;

    /**
     * 申请担当代码
     */
    @ExcelIgnore
    private String applicantNo;

    /**
     * 申请担当
     */
    @ExcelProperty(value = "申请担当", index = 17)
    private String applicationName;

    /**
     * 营业所点检确认 1.追加2.中止3.继续
     */
    @ExcelIgnore
    private Integer inspectConfirmType;

    @ExcelProperty(value = "营业所点检确认", index = 18)
    private String inspectConfirmTypeName;

    /**
     * 营业所点检回复
     */
    @ExcelIgnore
    private String inspectAnswerText;

    /**
     * 最晚消耗日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "最晚消耗日期", index = 19)
    private Date lastUseDate;

    /**
     * 负责人审批备注
     */
    @ExcelProperty(value = "负责人审批备注", index = 20)
    private String remark;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date updateTime;

    @ExcelIgnore
    private Long id;


    @ExcelIgnore
    private String createUser;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date createTime;

    @ExcelIgnore
    private String updateUser;
}
