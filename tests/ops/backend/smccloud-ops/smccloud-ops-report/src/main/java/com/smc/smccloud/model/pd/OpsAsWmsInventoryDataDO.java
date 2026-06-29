package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_wms_inventory_data")
public class OpsAsWmsInventoryDataDO implements Serializable {

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
     * 货架号
     */
    @TableField("shelves_no")
    private String shelvesNo;

    /**
     * 发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 货位号
     */
    @TableField("location_no")
    private String locationNo;

    /**
     * 拍号
     */
    @TableField("case_no")
    private String caseNo;

    /**
     * 箱号
     */
    @TableField("barcode")
    private String barcode;

    /**
     * 盘点排序
     */
    @TableField("pd_no")
    private String pdNo;

    @TableField("pd_sort")
    private String pdSort;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 账簿数
     */
    @TableField("bill_qty")
    private Integer billQty;

    /**
     * 数据类型1 实体货架库存，2 正式过渡库位库存，3 采购到货未入，4 调拨到货未入，5退货到货未入，6寄售库存 7 集约待交接区
     */
    @TableField("pd_data_type")
    private String pdDataType;

    /**
     * 票据类型1：现品票，2数据票，3清单票 4. 现品空白票 5. 到货未入空白票
     */
    @TableField("pd_bill_type")
    private String pdBillType;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;

    /**
     * 是否型号拆分
     */
    @TableField("is_ass")
    private String isAss;

    /**
     * 完整订单号
     */
    @TableField("order_no")
    private String orderNo;

    @TableField("warehouse_type")
    private String warehouseType;


}
