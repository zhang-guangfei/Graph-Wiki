package com.smc.smccloud.model.borrowstock;

import lombok.Data;

import java.util.Date;

@Data
public class BrwDetailDTO {

    private Long id;

    /**
     * 借货申请id
     */
    private Integer brwId;

    /**
     * 项号
     */
    private Integer itemId;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 型号名称
     */
    private String modelName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 库存id
     */
    private Long inventoryId;

    /**
     * 库存key
     */
    private String inventoryKeys;

    /**
     * 归还类型
     */
    private String returnType;

    private Integer returnQty;

    private Integer optStatus;

    private Integer expQty;

    /**
     * 晚归还原因
     */
    private String lateReturnReason;

    /**
     * 晚归还日期
     */
    private Date lateReplyDate;

    /**
     * 晚归还回复人
     */
    private String lateReplyPsn;

    private String warehouseDode;

    private Date shipDate;
}
