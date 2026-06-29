package com.sales.ops.dto.query;

import java.util.Date;
import java.util.List;

/**
 * @author B91717
 * @date 2023/8/24 16:19
 * @apiNote 采购自动发单配置备注
 */
public class PoAutoSendRemarkQO {

    private List<String> deptNos;

    private String modelno;

    private String remark;

    private String supplierClass;
    private String customerNo;

    private Date insertTime;

    private String insertUser;

    private Date updateTime;

    private String updateUser;

    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<String> getDeptNos() {
        return deptNos;
    }

    public void setDeptNos(List<String> deptNos) {
        this.deptNos = deptNos;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    public String getSupplierClass() {
        return supplierClass;
    }

    public void setSupplierClass(String supplierClass) {
        this.supplierClass = supplierClass;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
