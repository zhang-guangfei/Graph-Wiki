package com.sales.ops.dto.inqb;

/**
 * @author B91717
 * INQB-订单分配模块的请求实体信息
 */
public class OpsInqbOrderAllotRequest {
    private String modelNo; // 申请型号

    private Integer quantity; // 申请数量

    private String endUser; // 最终用户

    private String deptNo; // 最终用户所属部门，最小单位到所/驻在所

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

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
}
