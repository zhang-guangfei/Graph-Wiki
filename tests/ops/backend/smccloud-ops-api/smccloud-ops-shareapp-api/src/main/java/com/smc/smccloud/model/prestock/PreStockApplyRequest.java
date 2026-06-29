package com.smc.smccloud.model.prestock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Author: Denghui
 * Date: 2021-10-25 17:12
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PreStockApplyRequest extends BaseQuery {

    private Long applyId;

    private List<Long> applyIds;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 状态
     */
    private List<String> statuss;

    /**
     * 申请类型
     */
    private String applyType;

    /**
     * 1-普通申请 2-申请自动周转 3-自动补库
     */
    private String replType;

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
     * 备库类型
     */
    private String stockType;
    /**
     * 客户代码
     */
    private String customerNo;
    /**
     * 最终用户
     */
    private String userNo;
    /**
     * 项目号
     */
    private String projectNo;

    private String pplNo;
    /**
     * 集团代码
     */
    private String groupCustomerNo;
    /**
     * 备库仓库
     */
    private String warehouseCode;

    /**
     * 申请人
     */
    private String applyPsn;



}
