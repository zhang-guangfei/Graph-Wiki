package com.sales.ops.dto.prepareOrder;

import java.util.List;

public class CanUsePrepareOrderParam {
    private String endUserNo;
    private String ppl;
    private String pj;
    private String groupCustomerNo;

    private List<String> listGroupCustomerNos;

    private String modelNo;

    private int quantity;

    public List<String> getListGroupCustomerNos() {
        return listGroupCustomerNos;
    }

    public void setListGroupCustomerNos(List<String> listGroupCustomerNos) {
        this.listGroupCustomerNos = listGroupCustomerNos;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getEndUserNo() {
        return endUserNo;
    }

    public void setEndUserNo(String endUserNo) {
        this.endUserNo = endUserNo;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPj() {
        return pj;
    }

    public void setPj(String pj) {
        this.pj = pj;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }
}
