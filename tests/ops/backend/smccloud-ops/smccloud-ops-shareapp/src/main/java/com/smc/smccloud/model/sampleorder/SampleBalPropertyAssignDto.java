package com.smc.smccloud.model.sampleorder;

import lombok.Data;

/**
 * lyc
 *
 * @date 2025/{MONTH}/09
 * /
 * /**
 * @Author lyc
 * @Date 2025/1/9 13:08
 * @Descripton TODO
 */
@Data
public class SampleBalPropertyAssignDto {
    private String companyId;
    private int qty;
    private Boolean remailFlag = true; //该资产方是否有剩余
    private int remailQty; // 该资产方未分配数量
    private int version;
    private int proportion;

    private String idstr;
}
