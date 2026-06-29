package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * 退货清单查查询
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 12:08
 */
@Data
public class CsReturnApplyRequestDTO extends BaseQuery {
    // add by 伍家闻 2022/11/10 bug 8626
    private  String agentNo;
    private  String stockCode;
    private  Integer applyId;
    private  Integer status;
    private Date fromDate;
    private Date toDate;

}
