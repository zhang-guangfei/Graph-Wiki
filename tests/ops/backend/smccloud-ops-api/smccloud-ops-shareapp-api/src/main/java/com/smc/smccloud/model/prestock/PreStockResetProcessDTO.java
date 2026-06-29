package com.smc.smccloud.model.prestock;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-06-15 11:35
 * Description: 先行在库申请重置处理DTO
 */
@Data
public class PreStockResetProcessDTO {

    /**
     * 重置类型: 1-修改; 2-删除; 3-增加
     */
    private String resetType;

    /**
     * 申请类型
     */
    private String applyType;

    /**
     * 申请id
     */
    private Long applyId;

    /**
     * 申请项id
     */
    private List<Long> detailIds;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * Shikomi要求日本货期
     */
    private Date dlvDateJP;
}
