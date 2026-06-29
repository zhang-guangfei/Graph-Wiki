package com.smc.smccloud.model.order.orderdel;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.model.adapter.order.OrderDeleteItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/11/28 11:47
 * @Descripton TODO
 */
@Data
public class SalesErpOrderDeleteTaskBean {

    private OrderDeleteItem orderDeleteItem;

    @ApiModelProperty(value = "申请人代码")
    private String applyPersonNo;

    // 申请号
    private String applyNo;

    @ApiModelProperty(value = "申请人姓名")
    private String applyPersonName;

    @ApiModelProperty(value = "申请理由")
    private String applyReason;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    // 申请部门
    private String applyDeptNo;

}
