package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsRoBarcode;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2022/4/15 15:22
 */
public class WmRoBarcodeDto implements Serializable {

    private static final long serialVersionUID = -4568007961354500672L;
    private List<OpsRoBarcode> barCodelist;
    private Long wmOrderTaskId;

    public List<OpsRoBarcode> getBarCodelist() {
        return barCodelist;
    }

    public void setBarCodelist(List<OpsRoBarcode> barCodelist) {
        this.barCodelist = barCodelist;
    }

    public Long getWmOrderTaskId() {
        return wmOrderTaskId;
    }

    public void setWmOrderTaskId(Long wmOrderTaskId) {
        this.wmOrderTaskId = wmOrderTaskId;
    }
}
