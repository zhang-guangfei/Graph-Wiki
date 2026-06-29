package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 退货申请
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/10 11:03
 */
@Data
@TableName("cs_return_detail")
public class CsReturnDetailDO {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer applyId;
    private String modelNo;
    private Integer quantity;
    private Integer status;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    @TableField("remark")
    private String remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(value = "create_time",  fill = FieldFill.INSERT)
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(value = "update_time",  fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("itemNo")
    private int itemNo;

    @TableField("returnOrderNo")
    private String returnOrderNo;

    @TableField("apply_type")
    private Integer applyType;
}