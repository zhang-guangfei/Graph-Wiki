package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Data
public class CsApplyDetailVO implements Serializable {

    private static final long serialVersionUID = 1211639650855255395L;

    private Long id;

    private Long applyId;

    private  Integer itemNo;

    private String modelNo;
    /**
     * 备库数量
     */
    private Integer quantity;
    /**
     * 状态
     * 1编辑中
     * 2已提交申请
     * 9已删除
     *
     * 申请时项状态不用传
     */
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private String answerText;
    /**
     * 原ops计算的补货数量
     */
    private Integer calcQty;

    private String orderNo;

    private String remark;

    /**
     * 变更设置的备库数量
     */
    private  Integer initTotalQty;

    /**
     * 变更设置的单位数量
     */
    private Integer initUnitQty;

    /**
     * 设置的货架号
     */
    private  String locationNo;

    private Boolean rohs10;

    private Integer specExpType;

    private BigDecimal eamount;

    private BigDecimal eprice;

    // bom号
    private String bomNo;

    // 项目号
    private String projectNo;

    private Date hopeDeliveryDate;

    // cproduct物料号
    private String cproductNo;
}
