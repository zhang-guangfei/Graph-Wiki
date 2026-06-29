package com.smc.smccloud.model.csstock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 寄售管理查询
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CsStockApplyRequest extends BaseQuery {

    /**
     * 申请号
     */
    private String applyId;

    private String capplyNo;
    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 型号
     */
    private String modelNo;

    /**
     *查询类型
     * 1创建时间
     * 2申请时间
     */
    private Integer qryDateType;
    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date toDate;

    /**
     * 货位
     */
    private String locationNo;
    /**
     * 库房
     */
    private String stockCode;
}
