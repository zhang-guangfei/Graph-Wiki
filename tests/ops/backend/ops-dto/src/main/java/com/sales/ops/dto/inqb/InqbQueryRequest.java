package com.sales.ops.dto.inqb;

import java.util.Date;
import java.util.List;

/**
 * @author B91717
 */
public class InqbQueryRequest {
    private String inqbApplyNo; // INQB申请号
    private String sourcesApplyNo; // 原始INQB申请号
    private String modelNo; // 型号
    private String endUser; // 最终用户
    private String inqbDept; // 用户所属营业所
    private Integer inqbStatus;
    private String inqbUser;
    private Date startDate; //开始时间
    private Date endDate; // 结束时间
    private String inqbValidity;

    private String userStatus;// 使用状态

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getInqbValidity() {
        return inqbValidity;
    }

    public void setInqbValidity(String inqbValidity) {
        this.inqbValidity = inqbValidity;
    }

    private List<String> inqbDeptList;

    private String supplierCode; // 发送的供应商代码
    private List<String> supplierCodeList; // 发送的供应商代码


    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public List<String> getSupplierCodeList() {
        return supplierCodeList;
    }

    public void setSupplierCodeList(List<String> supplierCodeList) {
        this.supplierCodeList = supplierCodeList;
    }

    public List<String> getInqbDeptList() {
        return inqbDeptList;
    }

    public void setInqbDeptList(List<String> inqbDeptList) {
        this.inqbDeptList = inqbDeptList;
    }

    public String getInqbDept() {
        return inqbDept;
    }

    public void setInqbDept(String inqbDept) {
        this.inqbDept = inqbDept;
    }

    public Integer getInqbStatus() {
        return inqbStatus;
    }

    public void setInqbStatus(Integer inqbStatus) {
        this.inqbStatus = inqbStatus;
    }

    public String getInqbUser() {
        return inqbUser;
    }

    public void setInqbUser(String inqbUser) {
        this.inqbUser = inqbUser;
    }

    public String getInqbApplyNo() {
        return inqbApplyNo;
    }

    public void setInqbApplyNo(String inqbApplyNo) {
        this.inqbApplyNo = inqbApplyNo;
    }

    public String getSourcesApplyNo() {
        return sourcesApplyNo;
    }

    public void setSourcesApplyNo(String sourcesApplyNo) {
        this.sourcesApplyNo = sourcesApplyNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
