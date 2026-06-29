package com.smc.smccloud.model.adapter.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "存储删单信息子项实体")
public class OrderDeleteItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1273163240737175453L;

	@ApiModelProperty(value = "直销门户出库单号")
	private String no;

	@ApiModelProperty(value = "10位ERP订单号")
	private String orderNo;

	@ApiModelProperty(value = "子项号")
	private String itemNo;

    /**
     * 处理备注
     */
    @ApiModelProperty("订单状态")
    private String orderStatus;

    /**
     * 处理备注
     */
    @ApiModelProperty("客户编码")
    private String customerNo;

    /**
     * 客户名称
     */
    @ApiModelProperty("客户名称")
    private String customerName;

    /**
     * 用户编码
     */
    @ApiModelProperty("用户编码")
    private String userNo;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 型号
     */
    @ApiModelProperty("型号")
    private String modelNo;

    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer quantity;

    // 客户担当所属部门
    private String deptNo;

    // 二次审批标识
    private Boolean secondApproval;

    // 是否入专备标识
    private Boolean transferSpecial;

    /**
     * 责任方
     */
    private String responsibleParty;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 手续费率
     */
    private BigDecimal processingFeeRate;

    /**
     * 手续费
     */
    private BigDecimal processingFee;

    /**
     * 备注
     */
    private String remark;

    private String endUserForTransferSpecial;
    //删单对策
    private String cancelStrategy;
}
