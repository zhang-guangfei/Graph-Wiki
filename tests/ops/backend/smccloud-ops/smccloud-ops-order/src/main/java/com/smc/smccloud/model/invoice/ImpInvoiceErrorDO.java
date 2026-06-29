package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "imp_invoice_error")
public class ImpInvoiceErrorDO {

    private static final long serialVersionUID = 1L;

    /**
     * 发票入库数量
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 发票数量
     */
    @TableField("qty")
    private Integer qty;

    /**
     * 采购数量
     */
    @TableField("po_model_no")
    private String poModelNo;

    /**
     * 采购剩余数量
     */
    @TableField("po_qty")
    private Integer poQty;

    /**
     * 分包数量
     */
    @TableField("pack_qty")
    private Integer packQty;

    @TableField("error_text")
    private String errorText;

    @TableField("remark")
    private String remark;

    @TableField("ignore_error")
    private Boolean ignoreError;

    @TableField("ignore_reason")
    private String ignoreReason;

    @TableField("update_time")
    private Date updateTime;

    @TableField("invoice_id")
    private Long invoiceId;

    @TableField("poItemNo")
    private Integer poItemNo;

    @TableField("po_warehouse_code")
    private String poWarehouseCode;

    @TableField("ignore_time")
    private Date ignoreTime;

    @TableField("status")
    private Integer status;

    @TableField("ignore_psn")
    private String ignorePsn;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 1 数量差异 2 型号差异 3 两者都存在
    @TableField("error_type")
    private Integer errorType;

    @TableField("poNo")
    private String poNo;

    @TableField("order_no")
    private String orderNo;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("create_time")
    private Date createTime;


    public static void main(String[] args) {
        int a = 1;
        int b = ++a;
        // int b = a++;
       // int c = ++a;

        System.out.println("a = " + b);
       //  System.out.println("c = " + c);
    }
    
}
