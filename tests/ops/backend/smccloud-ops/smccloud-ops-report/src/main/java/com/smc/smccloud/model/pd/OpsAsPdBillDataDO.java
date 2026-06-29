package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_pd_bill_data")
public class OpsAsPdBillDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 盘点批次号
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * 仓库代码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 盘点票号
     */
    @TableField("pd_bill_no")
    private String pdBillNo;

    /**
     * 货架号
     */
    @TableField("shelves_no")
    private String shelvesNo;

    /**
     * 货位号
     */
    @TableField("location_no")
    private String locationNo;

    @TableField("order_no")
    private String orderNo;

    /**
     * 发票号(仅到货未入数据填写)
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 拍号(仅到货未入数据填写)
     */
    @TableField("case_no")
    private String caseNo;

    /**
     * 箱号(仅到货未入数据填写)
     */
    @TableField("barcode")
    private String barcode;

    /**
     * 盘点排序
     */
    @TableField("pd_no")
    private String pdNo;

    /**
     * 第一次盘点型号
     */
    @TableField("model_no_1")
    private String modelNo1;

    /**
     * 第二次盘点型号
     */
    @TableField("model_no_2")
    private String modelNo2;

    /**
     * 账簿数
     */
    @TableField("bill_qty")
    private Integer billQty;

    /**
     * 第一次盘点数量
     */
    @TableField("pd_qty_1")
    private Integer pdQty1;

    /**
     * 第二次盘点数量
     */
    @TableField("pd_qty_2")
    private Integer pdQty2;

    /**
     * 第一次录入担当
     */
    @TableField("pd_inputort_1")
    private String pdInputort1;

    /**
     * 第二次录入担当
     */
    @TableField("pd_inputort_2")
    private String pdInputort2;

    /**
     * 第一次录入时间
     */
    @TableField("pd_input_time_1")
    private Date pdInputTime1;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date pdInputTime1UI;

    /**
     * 第二次录入时间
     */
    @TableField("pd_input_time_2")
    private Date pdInputTime2;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date pdInputTime2UI;

    /**
     * 数据类型1 实体货架库存，2 过渡库位库存，3 采购到货未入，4 调拨到货未入，5退货到货未入
     */
    @TableField("pd_data_type")
    private String pdDataType;

    /**
     * 票据类型1：现品票，2数据票，3.清单票 4.现品空白票，5到货未入空白票
     */
    @TableField("pd_bill_type")
    private String pdBillType;

    /**
     * 仓库类型
     */
    @TableField("pd_warehouse_type")
    private String pdWarehouseType;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 0有效1作废
     */
    @TableField("del_flag")
    private Boolean delFlag;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date updateTimeUI;

    @TableField("wms_invoice_no")
    private String wmsInvoiceNo;

    @TableField("pd_sort")
    private String pdSort;

    // 是否型号拆分
    @TableField("is_ass")
    private String isAss;


}
