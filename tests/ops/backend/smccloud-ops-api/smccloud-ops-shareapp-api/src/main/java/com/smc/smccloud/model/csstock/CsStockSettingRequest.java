package com.smc.smccloud.model.csstock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 备库清单设置
 *
 * @author wsf
 * @version 1.0
 * @date 2021/11/4 11:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CsStockSettingRequest extends BaseQuery {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 型号
     */
    private String customerNo;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date fromTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date toTime;

    /**
     * 货位
     */
    private String locationNo;
    /**
     * 库房
     */
    private String stockCode;

    /**
     * 补库类型
     * 0-自动周转,1-不周转
     */
    private Integer replType;


    private String sponsor;
}
