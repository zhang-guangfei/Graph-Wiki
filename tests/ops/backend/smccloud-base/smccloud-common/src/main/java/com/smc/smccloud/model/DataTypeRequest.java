package com.smc.smccloud.model;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class DataTypeRequest extends BaseQuery {

    private Long id;

    private String code;

    private String classCode;

    private String codeName;

    private String parentCode;

    private int status;

    private String remark;

    private String extNote1;

    private String extNote2;

    private String extNote3;

    private String extNote4;

    private Boolean locked;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private int sort;
}
