package com.sales.ops.dto.ips;

import lombok.Data;

import java.util.Date;

public class PsiDataType {

    public PsiDataType()
    {

    }
    public PsiDataType(String code, String codeName)
    {
        this.code=code;
        this.codeName= codeName;

    }

    private Long id;

    private String code;

    private String codeName;

    private String parentCode;

    private String classCode;

    private int status;

    private String remark;

    private String extNote1;

    private String extNote2;

    private String extNote3;

    private Boolean locked;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private int sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtNote1() {
        return extNote1;
    }

    public void setExtNote1(String extNote1) {
        this.extNote1 = extNote1;
    }

    public String getExtNote2() {
        return extNote2;
    }

    public void setExtNote2(String extNote2) {
        this.extNote2 = extNote2;
    }

    public String getExtNote3() {
        return extNote3;
    }

    public void setExtNote3(String extNote3) {
        this.extNote3 = extNote3;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
