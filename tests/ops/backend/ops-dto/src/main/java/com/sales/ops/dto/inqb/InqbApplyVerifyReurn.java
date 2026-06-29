package com.sales.ops.dto.inqb;

import java.io.Serializable;

/**
 * INQB催促校验，返回是否可催促，及催促不可催促原因
 *
 * @author B91717
 */
public class InqbApplyVerifyReurn implements Serializable {
    /**
     * 是否可催促
     */
    private Boolean canPress;

    /**
     * 返回不可催促原因
     */
    private String checkFailureMsg;


    private String modelNo; // 申请型号

    private Integer quantity; // 申请数量

    private String endUser; // 最终用户

    public Boolean getCanPress() {
        return canPress;
    }

    public void setCanPress(Boolean canPress) {
        this.canPress = canPress;
    }

    public String getCheckFailureMsg() {
        return checkFailureMsg;
    }

    public void setCheckFailureMsg(String checkFailureMsg) {
        this.checkFailureMsg = checkFailureMsg;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

}
