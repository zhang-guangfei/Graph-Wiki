package com.smc.smccloud.model.stockassembly;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-10-24 09:41
 * Description:
 */
@Data
public class TransferResult {

    /**
     * 申请号
     */
    private String no;

    /**
     * 子项号
     */
    private int itemId;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 处理结果：false失败；true成功
     */
    private boolean result;

    /**
     * 处理备注
     */
    private String message;
}
