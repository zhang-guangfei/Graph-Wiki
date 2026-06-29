package com.sales.ops.dto.prepareOrder;

import java.util.List;


public class PrepareOrderQueryParam {

    /**
     * queryForm: {
     *         orderFno: '',
     *         manuFactSupplierCode: '',
     *         modelNo: '',
     *         status: 2,
     *         customerNo: '',
     *         applyNo: '',
     *         applyDeptNo: '',
     *         startApplyDate: '',
     *         endApplyDate: '',
     *         pageNum: 1,
     *         pageSize: 20
     *       },
     */
    private String orderFno;

    private String manuFactSupplierCode;

    private String modelNo;

    private Integer status;

    private List<String> statusList;

    private String customerNo;

    private String applyNo;

    private List<String> applyDeptNos;

    private String startApplyDate;

    private String endApplyDate;

    private String manuOrderNo;

    private String ppl;

    private String projectCode;

    private String groupCustomerNo;

    private Integer pageNum = 1;

    private Integer pageSize = 20;

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getManuFactSupplierCode() {
        return manuFactSupplierCode;
    }

    public void setManuFactSupplierCode(String manuFactSupplierCode) {
        this.manuFactSupplierCode = manuFactSupplierCode;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
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

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public List<String> getApplyDeptNos() {
        return applyDeptNos;
    }

    public void setApplyDeptNos(List<String> applyDeptNos) {
        this.applyDeptNos = applyDeptNos;
    }

    public String getStartApplyDate() {
        return startApplyDate;
    }

    public void setStartApplyDate(String startApplyDate) {
        this.startApplyDate = startApplyDate;
    }

    public String getEndApplyDate() {
        return endApplyDate;
    }

    public void setEndApplyDate(String endApplyDate) {
        this.endApplyDate = endApplyDate;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getManuOrderNo() {
        return manuOrderNo;
    }

    public void setManuOrderNo(String manuOrderNo) {
        this.manuOrderNo = manuOrderNo;
    }
}
