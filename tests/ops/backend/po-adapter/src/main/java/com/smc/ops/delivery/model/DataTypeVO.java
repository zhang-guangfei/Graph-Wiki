package com.smc.ops.delivery.model;

import lombok.Data;

import java.util.Date;

@Data
public class DataTypeVO {

    public DataTypeVO()
    {

    }
    public DataTypeVO(String code, String codeName)
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
}
