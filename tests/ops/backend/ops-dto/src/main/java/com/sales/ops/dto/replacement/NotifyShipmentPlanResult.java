package com.sales.ops.dto.replacement;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/10 13:13
 */
public class NotifyShipmentPlanResult implements Serializable {

    private static final long serialVersionUID = 6373531811352754234L;

    private Integer size;

    private List<NotifyShipmentPlanImportErrorDto> errList;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<NotifyShipmentPlanImportErrorDto> getErrList() {
        return errList;
    }

    public void setErrList(List<NotifyShipmentPlanImportErrorDto> errList) {
        this.errList = errList;
    }
}
