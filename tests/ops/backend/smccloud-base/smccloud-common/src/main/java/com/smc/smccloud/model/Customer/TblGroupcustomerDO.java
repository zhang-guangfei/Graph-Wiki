package com.smc.smccloud.model.Customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("Tbl_GroupCustomer")
public class TblGroupcustomerDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发货所在地营业所
     */
    @TableField("DlvDeptNo")
    private String DlvDeptNo;

    @TableField("id")
    private Integer id;

    @TableField("CompanyId")
    private String CompanyId;

    @TableField("UpdateTime")
    private String UpdateTime;

    @TableField("CustomerNo")
    private String CustomerNo;

    @TableField("CountryCode")
    private String CountryCode;

    @TableField("CustomerName")
    private String CustomerName;
    @TableField("DeptNo")
    private String deptNo;

    @TableField("province")
    private String province;
    @TableField("city")
    private String city;
    @TableField("district")
    private  String district;

}
