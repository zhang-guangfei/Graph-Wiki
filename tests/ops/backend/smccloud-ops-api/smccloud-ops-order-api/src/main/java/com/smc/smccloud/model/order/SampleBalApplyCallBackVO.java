package com.smc.smccloud.model.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/11/7 10:56
 * @Descripton TODO
 */
@Data
public class SampleBalApplyCallBackVO {

    private Long id;

    // 样品结转申请号
    private String sampleBalApplyNo;

    // 样品订单号
    private String orderNo;

    // 型号
    private String modelNo;

    // 申请结转数量
    private int applyBalQty;

    // 申请单状态 0未受理 1已受理 2已结转 3退回申请
    private String handStatus;
    private String handStatusName;

    // 结转类型
    private String balType;

    // 结转日期
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date balDate;

    // 签收物流中心
    private String backWarehource;
    private String backWarehourceName;


    // 签收数量
    private int signQty;

    // 签收时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date signTime;

    // 申请单说明
    private String handRemark;

}
