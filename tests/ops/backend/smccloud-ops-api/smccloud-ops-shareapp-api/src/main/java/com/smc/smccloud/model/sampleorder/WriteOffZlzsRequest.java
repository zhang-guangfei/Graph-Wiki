package com.smc.smccloud.model.sampleorder;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/2/3 14:28
 * @Descripton TODO
 */
@Data
public class WriteOffZlzsRequest {

    private String rorderNo;  // 订单号

    private String modelNo; // 型号

    private int qty;  // 需要销账数量

    private String remark; // 备注

    private String pcNo; // 盘点批次号

}
