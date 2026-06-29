package com.smc.smccloud.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author smc
 * @since 2024-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_delay_data")
public class OrderDelayDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @ExcelIgnore
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品类别
     */
    @ExcelProperty(value = "产品类别")
    @TableField("product_type")
    private String productType;

    /**
     * 提醒类型
     */
    @ExcelProperty(value = "提醒类型")
    @TableField("remind_type")
    private String remindType;

    /**
     * 接单号
     */
    @ExcelProperty(value = "接单号")
    @TableField("rorder_fno")
    private String rorderFno;

    /**
     * 货齐日
     */
    @ExcelProperty(value = "产品入库日")
    @TableField("ready_time")
    private Date readyTime;

    /**
     * 已货齐天数
     */
    @ExcelProperty(value = "已货齐天数")
    @TableField("ready_days")
    private Integer readyDays;

    /**
     * 是否超1个月
     */
    @ExcelProperty(value = "是否超1个月", converter = IsConverter.class)
    @TableField("overtime_month")
    private Boolean overtimeMonth;

    /**
     * 货齐月数
     */
    @ExcelProperty(value = "货齐月数")
    @TableField("ready_months")
    private Integer readyMonths;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号")
    @TableField("modelno")
    private String modelno;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    @TableField("quantity")
    private Integer quantity;

    /**
     * 未税金额
     */
    @ExcelProperty(value = "未税金额")
    @TableField("ntax_amount")
    private BigDecimal ntaxAmount;

    /**
     * 未税单价
     */
    @ExcelProperty(value = "未税单价")
    @TableField("ntax_price")
    private BigDecimal ntaxPrice;

    /**
     * 直代经
     */
    @ExcelProperty(value = "直代经")
    @TableField("customer_type")
    private String customerType;

    /**
     * 客户代码
     */
    @ExcelProperty(value = "客户代码")
    @TableField("customer_no")
    private String customerNo;

    /**
     * 客户名称
     */
    @ExcelProperty(value = "客户名称")
    @TableField("customer_name")
    private String customerName;

    /**
     * 最终用户
     */
    @ExcelProperty(value = "最终用户")
    @TableField("end_user")
    private String endUser;

    /**
     * 最终用户名称
     */
    @ExcelProperty(value = "最终用户名称")
    @TableField("end_user_name")
    private String endUserName;

    /**
     * 营业所代码
     */
    @ExcelProperty(value = "营业所代码")
    @TableField("sales_branch_no")
    private String salesBranchNo;

    /**
     * 营业所
     */
    @ExcelProperty(value = "营业所")
    @TableField("sales_branch_name")
    private String salesBranchName;

    /**
     * 营业部
     */
    @ExcelProperty(value = "营业部")
    @TableField("sales_dept_no")
    private String salesDeptNo;

    /**
     * 所属大区
     */
    @ExcelProperty(value = "所属大区")
    @TableField("region_no")
    private String regionNo;

    /**
     * 货齐数
     */
    @ExcelProperty(value = "货齐数")
    @TableField("ready_qty")
    private Integer readyQty;

    /**
     * 已发数
     */
    @ExcelProperty(value = "已发数")
    @TableField("out_qty")
    private Integer outQty;

    /**
     * 未发数
     */
    @ExcelProperty(value = "未发数")
    @TableField("nout_qty")
    private Integer noutQty;

    /**
     * 开票数
     */
    @ExcelProperty(value = "开票数")
    @TableField("invoiced_qty")
    private Integer invoicedQty;

    /**
     * 已退数
     */
    @ExcelProperty(value = "已退数")
    @TableField("returned_qty")
    private Integer returnedQty;

    /**
     * 订货日期
     */
    @ExcelProperty(value = "订货日期")
    @TableField("order_date")
    private Date orderDate;

    /**
     * 交货日期
     */
    @ExcelProperty(value = "交货日期")
    @TableField("dlv_date")
    private Date dlvDate;

    /**
     * 原客户交货期
     */
    @ExcelProperty(value = "原客户交货期")
    @TableField("cdlv_date")
    private Date cdlvDate;

    /**
     * 指定物流出库日
     */
    @ExcelProperty(value = "指定物流出库日")
    @TableField("wms_dlv_date")
    private Date wmsDlvDate;

    /**
     * 订单状态
     */
    @ExcelProperty(value = "订单状态")
    @TableField("status")
    private String status;

    /**
     * 员工编号
     */
    @ExcelProperty(value = "员工编号")
    @TableField("employee_no")
    private String employeeNo;

    /**
     * HL
     */
    @ExcelProperty(value = "HL")
    @TableField("HL")
    private String hl;

    @ExcelIgnore
    @TableField("create_time")
    private Date createTime;
}
