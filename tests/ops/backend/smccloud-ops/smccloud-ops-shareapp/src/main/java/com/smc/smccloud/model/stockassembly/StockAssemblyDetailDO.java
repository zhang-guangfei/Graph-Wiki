package com.smc.smccloud.model.stockassembly;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: B90034
 * Date: 2021-09-27 12:40
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_assembly_detail")
public class StockAssemblyDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * stock_assembly.id
     */
    @TableField("ApplyId")
    private Long applyId;

    @TableField("ModelNo")
    private String modelNo;

    @TableField("branch_flag")
    private Integer branchFlag;

    /**
     * 是否转出数量
     */
    @TableField("IsTransOut")
    private Boolean isTransOut;

    /**
     * 数量,入为正数,出为负数
     */
    @TableField("Quantity")
    private Double quantity;

    /**
     * 备注
     */
    @TableField("Remark")
    private String remark;

    /**
     * 允许转出数量
     */
    @TableField("AllowOutQty")
    private Integer allowOutQty;

    /**
     * 是否预约了库存
     */
    @TableField("IsPrepareInv")
    private Boolean isPrepareInv;

    /**
     * （处理状态 1-执行处理成功; 2-已同步票号信息; "9"-删除;）
     * 改-处理状态 0-编辑中; 1-审核中; 2-待处理; 4-退回; 5-组换中; 6-已完成; 7-不能组换; 8-已计入成本; 9-已取消;
     */
    @TableField("OptCode")
    private Integer optCode;

    /**
     * 数量单位
     */
    @TableField("Unit")
    private String unit;

    /**
     * 长度
     */
    @TableField("LenDM")
    private Integer lenDM;

    /**
     * 库存ID
     */
    @TableField("Inventory_Id")
    private Long inventoryId;

    /**
     * 按库存类型~仓库代码~客户代码~客户群号~PPL~项目号拼接
     */
    @TableField("inventory_keys")
    private String inventoryKeys;

    /**
     * 仓库代码
     */
    @TableField("warehouseCode")
    private String warehouseCode;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    /**
     * 票号
     */
    @TableField("InvoiceNo")
    private String InvoiceNo;

    /**
     * 执行时间
     */
    @TableField("TransTime")
    private Date TransTime;
}
