package com.smc.smccloud.model.stockassembly;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Author: Denghui
 * Date: 2021-09-28 10:36
 * Description: 申请项信息
 */
@Data
public class StockAssemblyItemDTO {

    /**
     * stock_assembly_detail.id
     */
    private Long id;

    /**
     * stock_assembly.id
     */
    private Long applyId;

    /**
     * 型号 (必需)
     */
    @NotBlank(message = "申请项型号不能为空")
    private String modelNo;

    /**
     * 是否调出 (必需)
     * true-调出
     * false-调入
     */
    @NotNull(message = "调动类型不能为空")
    private Boolean isTransOut;

    /**
     * 数量 (必需)
     * 负数为调出
     * 正数为调入
     */
    @NotNull(message = "数量不能为空")
    private Double quantity;

    /**
     * 备注
     */
    private String remark;

    /**
     * 允许转出数量
     */
    private Integer allowOutQty;

    /**
     * 是否预约了库存
     */
    private Integer isPrepareInv;

    /**
     * 处理状态 9-删除
     */
    private Integer optCode;

    /**
     * 数量单位 PC
     */
    private String unit;

    /**
     * 长度 1
     */
    private Integer lenDM;

    /**
     * 库存ID
     */
    private Long inventoryId;

    /**
     * 仓库代码 (必需)
     */
    @NotBlank(message = "仓库代码不能为空")
    private String warehouseCode;

    /**
     * 根据下面的字段按"库存类型~仓库代码~客户代码~客户群号~PPL~项目号"格式拼接
     */
    private String inventoryKeys;

    /**
     * 库存类型 (必需)
     * TY 通用在库,
     * GK-TY 顾客通用在库
     * GK-PPL 顾客在库PPL
     * GK-PJ 顾客在库项目
     */
    @NotBlank(message = "库存类型不能为空")
    private String inventoryType;

    /**
     * 客户代码 (库存类型 GK* 必需)
     */
    private String customerNo;

    /**
     * 客户群号 (库存类型 GK* 选填)
     */
    private String groupCustomerNo;

    /**
     * PPL (库存类型 GK-PPL 必需)
     */
    private String pplNo;

    /**
     * 项目号 (库存类型 GK-PJ 必需)
     */
    private String projectNo;

}
