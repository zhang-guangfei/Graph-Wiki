package com.sales.ops.dto.units;

import java.io.Serializable;

public class OpsUnitsTree implements Serializable  {

    private String Id;

    private String ParentId;

    private String Name;

    private String K3Code;

    private String FullName;

    private String Level;

    private String Status;

    private Boolean SalesCustomized;

    private String CompanyCode;

    private String CompanyName;

    private String unitCode;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getK3Code() {
        return K3Code;
    }

    public void setK3Code(String k3Code) {
        K3Code = k3Code;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Boolean getSalesCustomized() {
        return SalesCustomized;
    }

    public void setSalesCustomized(Boolean salesCustomized) {
        SalesCustomized = salesCustomized;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Override
    public String toString() {
        return "OpsUnitsTree{" +
                "Id='" + Id + '\'' +
                ", ParentId='" + ParentId + '\'' +
                ", Name='" + Name + '\'' +
                ", K3Code='" + K3Code + '\'' +
                ", FullName='" + FullName + '\'' +
                ", Level='" + Level + '\'' +
                ", Status='" + Status + '\'' +
                ", SalesCustomized=" + SalesCustomized +
                ", CompanyCode='" + CompanyCode + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", unitCode='" + unitCode + '\'' +
                '}';
    }
}
