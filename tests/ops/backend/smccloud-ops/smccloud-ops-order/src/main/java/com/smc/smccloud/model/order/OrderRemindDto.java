package com.smc.smccloud.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/11 13:05
 */
@Data
public class OrderRemindDto {

    private String rorderFno;

    private String deptNo;

    private String orderNo;

    private String orderItem;

    private String modelNo;

    private String quantity;

    private String customerNo;

    private String endUser;
    // 接单日期
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date allotTime;
    // 客户交货期
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date dlvDate;
    // 订单类型
    private String orderType;
    // 特殊标识
    private String expDlvType;
    // 提醒类型
    private String remindType;
    // 提醒日期
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date remindDate;
    // 提醒备注
    private String remindNote;

    // 查询条件 提醒日期
    private Date startRemindDate;
    // 查询条件 提醒日期
    private Date endRemindDate;

    public Date[] remindDateArray;

    private List<String> deptNoArray;

}
