package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tmp_imp_invoice_error")
public class TmpImpInvoiceErrorDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("invoice_id")
    private Long invoiceId;

    @TableField("error_type")
    private Integer errorType;

    @TableField("pack_qty")
    private Integer packQty;

    @TableField("remark")
    private String remark;

    @TableField("qty")
    private Integer qty;

    @TableField("error_text")
    private String errorText;

    @TableField("poItemNo")
    private Integer poItemNo;

    @TableField("po_qty")
    private Integer poQty;

    @TableField("order_no")
    private String orderNo;

    @TableField("po_model_no")
    private String poModelNo;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("poNo")
    private String poNo;

    @TableField("model_no")
    private String modelNo;

    @TableField("po_warehouse_code")
    private String poWarehouseCode;


}
