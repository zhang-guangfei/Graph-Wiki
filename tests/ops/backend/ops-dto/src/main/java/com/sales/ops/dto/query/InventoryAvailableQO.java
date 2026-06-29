package com.sales.ops.dto.query;

/**
 * @author C12961
 * @date 2022/6/27 9:07
 */
public class InventoryAvailableQO {


    private String dept;
    private String modelno;
    private Integer qty;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomer;

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getGroupCustomer() {
        return groupCustomer;
    }

    public void setGroupCustomer(String groupCustomer) {
        this.groupCustomer = groupCustomer;
    }

}
