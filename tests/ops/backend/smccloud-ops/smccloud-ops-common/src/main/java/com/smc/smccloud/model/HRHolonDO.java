package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-04-13 09:38
 * Description:
 */
@Data
@TableName("hr_holon")
public class HRHolonDO {

    @TableField("COMPANY")
    private String COMPANY;

    @TableField("COMPANYCODE")
    private String COMPANYCODE;

    @TableField("Benbu")
    private String Benbu;

    @TableField("dept_erjibu")
    private String dept_erjibu;

    @TableField("dept_erjibuCode")
    private String dept_erjibuCode;

    @TableField("FNUMBER")
    private String FNUMBER;

    @TableField("Daqu")
    private String Daqu;

    @TableField("FDEPTNAME")
    private String FDEPTNAME;

    @TableField("FLONGNUMBER")
    private String FLONGNUMBER;

    @TableField("FFULLNAME")
    private String FFULLNAME;

    @TableField("PARENTFNUMBER")
    private String PARENTFNUMBER;

    @TableField("PARENTFNAME")
    private String PARENTFNAME;

    @TableId(value = "DEPTCODE", type = IdType.INPUT)
    private String DEPTCODE;

    @TableField("UNITLEVEL")
    private String UNITLEVEL;

    @TableField("UNITLEVELNAME")
    private String UNITLEVELNAME;

    @TableField("PSNSMCID")
    private String PSNSMCID;

    @TableField("PSNNAME")
    private String PSNNAME;

    @TableField("FUZERENPOSITION")
    private String FUZERENPOSITION;

    @TableField("POSITIONNAME")
    private String POSITIONNAME;

    @TableField("ISSALEHOLON")
    private String ISSALEHOLON;

    @TableField("SALEDEPTFNUMBER")
    private String SALEDEPTFNUMBER;

    @TableField("SALEDEPTFNAME")
    private String SALEDEPTFNAME;
}
