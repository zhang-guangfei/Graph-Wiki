package com.smc.smccloud.model.sampleorder;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/8/2 11:42
 * @Descripton TODO
 */
@Data
public class SampleOrderManageVO {

    private static final long serialVersionUID = 1L;

    private String deptNo;

    private String beforeUpDeptNo;

    private String deptName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String orderNo;

    private Integer outQty;

    private Integer impQty;

    // 负责人
    private String manager;

    private String manageName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date shipDate;

    private Integer id;

    private Integer qtyOnhand;

    private String modelNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date outTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer status;

    private String statusName;

    private String remark;

    private String updateUser;

    private String updateUserName;

    private String parentDeptNo;

    private String parentDeptName;
}
