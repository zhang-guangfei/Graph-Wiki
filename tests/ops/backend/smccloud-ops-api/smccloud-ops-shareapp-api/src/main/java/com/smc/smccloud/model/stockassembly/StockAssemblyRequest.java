package com.smc.smccloud.model.stockassembly;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Author: Denghui
 * Date: 2021-09-28 10:42
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StockAssemblyRequest extends BaseQuery {

    private Long id;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 型号
     */
    private String needModelNo;

    /**
     * 订单号
     */
    private String needForOrderNo;

    /**
     * 申请部门代码
     */
    private String deptNo;

    /**
     * 申请状态： 1-编辑中; 2-待确认; 3-已确认; 4-不通过; 5-组装中; 6-已完成; 9-取消
     */
    private String status;

    /**
     * 申请目的: 1-组装; 2-拆分; 3-调库
     */
    private String assembleType;

    /**
     * 票号
     */
    private String billNo;

    /**
     * 查询时间类型: 1-申请时间; 2-处理时间
     */
    private Integer dateType;

    /**
     * 起始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date fromDate;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date toDate;
    /**
     * true调出，false 调入
     */
    private Boolean isTransOut;

    private List<String> modelNos;
}
