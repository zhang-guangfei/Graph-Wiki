package com.smc.smccloud.model.stock;

import lombok.Data;

import java.util.Map;

/**
 * Author: B90034
 * Date: 2022-07-12 17:46
 * Description:
 */
@Data
public class AdapterPreStockDTO {

    private String no;

    private String ERPno;

    /**
     * 0-主单; 1-子单;
     */
    private String dataType;
    /**
     * 0-待处理; 1-已处理; 2-已拆分; 3-取消;
     */
    private Map<Integer, String> itemListStatus;
}
