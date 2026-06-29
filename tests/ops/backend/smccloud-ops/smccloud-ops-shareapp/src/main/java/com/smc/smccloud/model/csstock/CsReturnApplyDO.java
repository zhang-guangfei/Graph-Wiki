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
@TableName("cs_return_apply")
public class CsReturnApplyDO {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer applyId;
    private String agentNo;
    private String warehouseCode;
    private Date applyTime;
    private String orderNo;
    private String deptNo;

    /**
     * 状态
     * 1编辑中
     * 2提交中（暂无使用）
     * 3通过
     * 4不通过
     * 9取消
     */
    private Integer status;
    /**
     * 型号数量汇总
     */
    private Integer modelQty;
    /**
     * 型号个数（子项个数）
     */
    private Integer totalQty;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    @TableField("apply_person")
    private String applyPerson;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(value = "create_time",  fill = FieldFill.INSERT)
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(value = "update_time",  fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("to_wareouse_code")
    private String toWareouseCode;
}