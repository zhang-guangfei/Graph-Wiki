package com.smc.smccloud.model.ordermodify;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/11/5 15:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderModifyRequest extends BaseQuery {

    private List<String> orderNos;

    private String status;

    private String modifyType;

    private String applyNo;

    private String deptNo;

    private String modelNo;

    private String dateType;

    private Date fromDate;

    private Date toDate;

    private String tradecompanyId;

    private List<String> deptNoList;

    private String stockCode;

    private String fromDateStr;

    private String toDateStr;

    private String changeType;

    private String remark;

    private Boolean isGt500w;

    private Boolean sendProcess;

    // 0 小于500w 1 大于500W
    private String isGt500wFlag;

    // 0 不是 1 是
    private String sendProcessFlag;

    private String cancelStrategy;
}
