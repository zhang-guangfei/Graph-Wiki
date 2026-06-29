package com.smc.smccloud.model.customer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TblGroupcustomerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发货所在地营业所
     */
    private String DlvDeptNo;

    private Integer id;

    private String CompanyId;

    private String UpdateTime;

    private String CustomerNo;

    private String CountryCode;

    private String CustomerName;

    private String deptNo;

    private String province;
    private String city;
    private  String district;


}
