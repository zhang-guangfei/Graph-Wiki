package com.smc.smccloud.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizationRelationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开票担当
     */
    private String EmpInvoice;

    private String Remark;

    private String ParentId;

    private String HRUnitId;

    private String CNUnitId;

    private String FormalId;

    private Integer OrganizationType;

    private String Id;

    private String UpdateTime;


}
