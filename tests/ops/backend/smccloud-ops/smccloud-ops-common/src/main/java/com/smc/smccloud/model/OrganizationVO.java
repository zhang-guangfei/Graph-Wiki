package com.smc.smccloud.model;

import lombok.Data;

/**
 * @author wuweidong
 * @create 2023/12/11 09:15
 * @description
 */
@Data
public class OrganizationVO {

    //营业所 或驻在所
    private String deptNo;
    private String deptName;

    //营业所
    private String salesNo;
    private String salesName;

    //营业
    private String parentNo;
    private String parentName;
    //分公司
    private String companyNo;
    private String companyName;
}
