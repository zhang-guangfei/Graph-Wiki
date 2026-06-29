package com.smc.smccloud.model.kd;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/10/23 14:05
 * @Descripton TODO
 */
@Data
public class KdInfoVO {

    private static final long serialVersionUID = 1L;

    /**
     * kd编号
     */
    @ExcelProperty(value = "kd编号", index = 0)
    private String kdNo;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号", index = 1)
    private String modelNo;

    /**
     * 机种
     */
    @ExcelProperty(value = "机种", index = 2)
    private String modelSort;

    /**
     * 预计评价日(中国)
     */
    // @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "预计评价日(中国)", index = 3)
    private Date expectEvalTimeCn;

    /**
     * 是否日本评价;是否日本评价：0-否，1-沟通中，2-是
     */
    @ExcelIgnore
    private String isJpEval;

    @ExcelProperty(value = "是否日本评价", index = 4)
    private String isJpEvalDesc;

    /**
     * 预计量产日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "预计量产日", index = 5)
    private Date expectBatchProdTime;

    /**
     * 变更次数
     */
    @ExcelProperty(value = "变更次数", index = 6)
    private Integer changeNum;

    /**
     * 状态：1-中国评价合格，2-日本评价中，3-开始量产，4-终止
     */
    @ExcelIgnore
    private String state;

    @ExcelProperty(value = "状态", index = 7)
    private String stateDesc;

    /**
     * 生产工厂
     */
    @ExcelProperty(value = "生产工厂", index = 8)
    private String prodFactory;

    /**
     * 生产部门
     */
    @ExcelProperty(value = "生产部门", index = 9)
    private String prodDept;

    /**
     * 负责人
     */
    @ExcelProperty(value = "负责人", index = 10)
    private String manager;

    /**
     * 量产开始日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "量产开始日", index = 11)
    private Date batchProdStartTime;

    /**
     * 是否有效：1-有效，0-无效
     */
    @ExcelIgnore
    private String isActivity;
    @ExcelProperty(value = "是否有效", index = 12)
    private String isActivityDesc;


    /**
     * 源创建人
     */
    @ExcelProperty(value = "创建人", index = 13)
    private String createdBy;

    /**
     * 源更新人
     */
    @ExcelProperty(value = "更新人", index = 14)
    private String updatedBy;

    /**
     * 源更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 15)
    private String sourceUpdatedTime;

    /**
     * 源创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", index = 16)
    private String sourceCreatedTime;

    @ExcelProperty(value = "备注", index = 17)
    private String remark;


    @ExcelIgnore
    private String createUser;

    @ExcelIgnore
    private Date updateTime;

    @ExcelIgnore
    private Long id;

    @ExcelIgnore
    private Date createTime;

    @ExcelIgnore
    private String updateUser;


}
