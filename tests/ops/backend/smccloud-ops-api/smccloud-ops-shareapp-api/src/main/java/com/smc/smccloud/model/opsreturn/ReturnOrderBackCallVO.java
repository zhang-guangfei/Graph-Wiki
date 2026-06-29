package com.smc.smccloud.model.opsreturn;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/14 17:00
 * @Descripton TODO
 */
@Data
public class ReturnOrderBackCallVO {

    // 申请号
    private String applyNo;
    // 项号
    private Integer itemNo;
    private String orderNo;
    // 可退数量
    private Integer allowReturnQuantity;
    // 不可退数量
    private Integer notAllowReturnQuantity;

    // 货位号
    private String shelves;

    // 退货物流签收日
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date signDate;
    // 业务处理日期
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dealDate;

    private Integer status;

    private String requestWareHouseCode;
}
