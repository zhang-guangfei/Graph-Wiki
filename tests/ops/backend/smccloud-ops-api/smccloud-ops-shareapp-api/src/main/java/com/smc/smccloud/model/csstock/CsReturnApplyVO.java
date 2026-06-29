package com.smc.smccloud.model.csstock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 退货申请
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 11:53
 */
@Data
public class CsReturnApplyVO {

    private Integer applyId;
    private String agentNo;
    private String warehouseCode;
    private Date applyTime;
    private String orderNo;
    private String deptNo;
    private String toWareouseCode;

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


    private String createUser;


    private String updateUser;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
}
