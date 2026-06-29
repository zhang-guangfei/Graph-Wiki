package com.sales.ops.dto.order;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：转定
 * @date ：Created in 2022/5/6 14:02
 */
public class TranferimenttoVarDto implements Serializable {

    private static final long serialVersionUID = -3209634783960302018L;

    private Integer SplitNo ;
    private Boolean dbFlag = false;

    public Integer getSplitNo() {
        return SplitNo;
    }

    public void setSplitNo(Integer splitNo) {
        SplitNo = splitNo;
    }

    public Boolean getDbFlag() {
        return dbFlag;
    }

    public void setDbFlag(Boolean dbFlag) {
        this.dbFlag = dbFlag;
    }
}
