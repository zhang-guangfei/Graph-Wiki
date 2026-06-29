package com.smc.smccloud.model.ordermodify;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lyc
 * @Date 2023/12/1 13:40
 * @Descripton TODO
 */
@Data
public class SpecialVO {

    // 二次审批标识
    private Boolean secondApproval = false;

    // 是否入专备标识
    private Boolean transferSpecial = false ;

    // 调入用户专备
    private String endUserForTransferSpecial;

    private Boolean isGt500w = false;

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


}
