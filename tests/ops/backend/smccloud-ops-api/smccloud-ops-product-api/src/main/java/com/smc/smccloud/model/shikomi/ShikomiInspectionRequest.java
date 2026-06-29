package com.smc.smccloud.model.shikomi;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: ShikomiInspectionRequest
 * @date 2022/06/09 17:14
 */
@Data
public class ShikomiInspectionRequest extends BaseQuery {

    private String modelNo;

    private String shikomiNo;

    private Integer applyType;

    private String applyNo;

    private String indCode;

    private String customerNo;

    private Integer inspectStatus;

    private String retentionStartDate;

    private String retentionEndDate;
}
