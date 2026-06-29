package com.sales.ops.dto.inqb;

import java.io.Serializable;

/**
 * INQB催促校验，返回是否可催促，及催促不可催促原因
 *
 * @author B91717
 */
public class InqbInventoryVerifyDto implements Serializable {

    private String modelno;

    private Integer avaQty; // 可用数量

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getAvaQty() {
        return avaQty;
    }

    public void setAvaQty(Integer avaQty) {
        this.avaQty = avaQty;
    }
}
