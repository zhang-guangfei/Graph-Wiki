package com.smc.smccloud.model.csstock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 备库清单编辑
 *
 * @author wsf
 * @version 1.0
 * @date 2021/11/4 11:34
 */
@Data
public class CsStockSettingDTO   {

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 库房
     */
    private String stockCode;


    /**
     * 型号
     */
    private String modelNo;
    /**
     * 货位
     */
    private String locationNo;

    /**
     * 备库数
     */
    private Integer initStockQty;
    /**
     * 库存单位
     */
    private Integer initUnitQty;
    /**
     * 备库月份
     */
    private Integer initStockMonth;
    /**
     * 补货类型
     * 0自动周转
     * 1不周转
     */
    private Integer replType;

    private String sponsor;
}
