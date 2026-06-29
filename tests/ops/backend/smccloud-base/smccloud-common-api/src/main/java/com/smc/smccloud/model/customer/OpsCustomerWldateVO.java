package com.smc.smccloud.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/12/6 16:51
 * @Descripton TODO
 */
@Data
public class OpsCustomerWldateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 客户代码
     */
    private String customerNo;

    private String customerName;

    /**
     * 备案类型
     */
    private Integer isWldate;
    private String wldTypeName;

    /**
     * 是否删除0默认、1已经删除
     */
    private Integer delFlag;

    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyTime;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 增加版本号，防止并发异常更新
     */
    private Integer version;

    // 担当代码
    private String psnSmcId;

    // 担当名称
    private String psnSmcIdName;

    private String deptNo;

    private String hlCode;

    private String hrUnitId;

    private String deptName;



}
