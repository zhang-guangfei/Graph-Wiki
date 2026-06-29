package com.smc.smccloud.model.login;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePosition implements Serializable
{
    private static final long serialVersionUID = 2574905194793703746L;
    private String psnsmcId;
    private String emId;
    private String psnName;
    private Byte status;
    private String positionId;
    private String positionName;
    private Boolean isPrimary;
    private Integer isFuZeRen;
    private String unitId;
    private String unitLevel;
    private String unitName;
    /*
    * @Deprecated ： 若某类或某方法加上该注解之后，表示此方法或类不再建议使用，
    * 调用时也会出现删除线，但并不代表不能用，只是说，不推荐使用，因为还有更好的方法可以调用。*/
    @Deprecated
    private String deptCode;
    private String unitCode;
    private String fullName;
    private String companyCode;
    private String companyName;
}
