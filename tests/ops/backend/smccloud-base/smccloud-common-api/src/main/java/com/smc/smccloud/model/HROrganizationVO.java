package com.smc.smccloud.model;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/5/23 15:52
 * @Descripton TODO
 */
@Data
public class HROrganizationVO {

    private String id;

    private String parentId;

    private String name;

    private String k3Code;

    private String fullName;

    private String level;

    private String status;

    private String salesCustomized;

    private String companyCode;

    private String companyName;

    private String unitCode;

    private String empInvoice;

}
