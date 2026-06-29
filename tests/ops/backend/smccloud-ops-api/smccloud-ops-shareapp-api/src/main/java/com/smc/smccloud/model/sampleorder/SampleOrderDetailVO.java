package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SampleOrderDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * samplyOrder_apply.id
     */
    private Long applyId;

    /**
     * 项号 非空
     */
    @NotNull
    private Integer itemNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 型号
     */
    @NotEmpty
    private String modelNo;

    /**
     * 数量
     */
    @NotNull
    private Integer quantity;

    /**
     * 单价
     */
    @NotNull
    private BigDecimal price;

    /**
     * 客户型号
     */
    private String cmodelNo;

    /**
     * 希望货期
     */
    private Date dlvDate;

    /**
     * 备注
     */
    private String remark;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    /**
     * 结转状态
     */
    private Integer costStatus;

    private Date costTime;

    private String invoiceNo;

    private String invoiceType;

    // 客户类型  0 直销 1 代销 2 经销
    private String customerType;

    // 阀与汇流板标识（0:正常订货;1:底板;2:组装原件）
    // 0正常 1是板 2是阀
    private String specMark;

    // 行业id
    private String industryId;
}
