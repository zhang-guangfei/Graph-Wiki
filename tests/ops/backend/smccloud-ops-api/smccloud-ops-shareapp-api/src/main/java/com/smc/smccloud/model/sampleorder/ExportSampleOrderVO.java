package com.smc.smccloud.model.sampleorder;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/7/18 10:28
 * @Descripton TODO
 */
@Data
public class ExportSampleOrderVO {

    /**
     * 申请号
     */
    @ExcelProperty(value = "申请号",index = 0)
    private String applyNo;

    /**
     * 接单订单号
     */
    @ExcelProperty(value = "接单订单号",index = 1)
    private String orderNo;

    /**
     * 项号
     */
    @ExcelProperty(value = "项号",index = 2)
    private Integer itemNo;


    /**
     * 状态
     */
    @ExcelIgnore
    private Integer status;
    @ExcelProperty(value = "状态",index = 3)
    private String statusName;

    /**
     * 客户单号
     */
    @ExcelProperty(value = "客户单号",index = 4)
    private String corderNo;

    /**
     * 客户代码
     */
    @ExcelProperty(value = "客户代码",index = 5)
    private String customerNo;

    /**
     * 用户代码
     */
    @ExcelProperty(value = "用户代码",index = 6)
    private String userNo;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量",index = 7)
    private Integer quantity;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号",index = 8)
    private String modelNo;

    /**
     * 客户型号
     */
    @ExcelProperty(value = "客户型号",index = 9)
    private String cmodelNo;


    /**
     * 申请部门
     */
    @ExcelProperty(value = "申请部门",index = 10)
    private String applyDeptNo;

    /**
     * 申请类型
     */
    @ExcelIgnore
    private String applyType;

    /**
     *  申请类别名称
     */
    @ExcelProperty(value = "申请类别",index = 11)
    private String applyTypeName;

    /**
     * 成本结算类型
     */
    @ExcelIgnore
    private String costType;

    /**
     * 结算类型名称
     */
    @ExcelProperty(value = "结算类型",index = 12)
    private String costTypeName;

    /**
     * 申请目的
     */
    @ExcelProperty(value = "申请目的",index = 13)
    private String purpose;


    /**
     * 费用承担部门
     */
    @ExcelProperty(value = "费用承担部门",index = 14)
    private String deptNo;

    /**
     * 单价
     */
    @ExcelProperty(value = "单价",index = 15)
    private BigDecimal price;


    /**
     * 出货方式
     */
    @ExcelProperty(value = "出货方式",index = 16)
    private String dlvEntire;

    /**
     * 出货类别1
     */
    @ExcelProperty(value = "出货类别1",index = 17)
    private String dlvType1;

    /**
     * 出货类别2
     */
    @ExcelProperty(value = "出货类别2",index = 18)
    private String dlvType2;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注",index = 19)
    private String remark;

    /**
     * 收货地址类别
     */
    @ExcelProperty(value = "收货地址类别",index = 20)
    private Integer addressType;

    /**
     * 地址编号
     */
    @ExcelProperty(value = "地址编号",index = 21)
    private String addressNo;

    /**
     * 申请时间
     */
    @ExcelIgnore
    private Date applyTime;
    @ExcelProperty(value = "申请时间",index = 22)
    private String applyTimeStr;

    /**
     * 生成订单时间
     */
    @ExcelIgnore
    private Date orderDate;
    @ExcelProperty(value = "生成订单时间",index = 23)
    private String orderDateStr;

    /**
     * 希望货期
     */
    @ExcelIgnore
    private Date dlvDate;
    @ExcelProperty(value = "希望货期",index = 24)
    private String dlvDateStr;


    /**
     * 处理人
     */
    @ExcelProperty(value = "处理人",index = 25)
    private String answerPsn;


    /**
     * 处理时间
     */
    @ExcelIgnore
    private Date answerTime;
    @ExcelProperty(value = "处理时间",index = 26)
    private String answerTimeStr;

    /**
     * 回复说明
     */
    @ExcelProperty(value = "回复说明",index = 27)
    private String answerText;


    /**
     * 申请人
     */
    @ExcelProperty(value = "申请人",index = 28)
    private String applyPsn;




}
