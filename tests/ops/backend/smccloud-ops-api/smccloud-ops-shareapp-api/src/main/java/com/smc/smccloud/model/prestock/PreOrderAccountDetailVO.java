package com.smc.smccloud.model.prestock;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/7/16 10:33
 * @Descripton TODO
 */
@Data
public class PreOrderAccountDetailVO {
    private static final long serialVersionUID = 1L;
    private Long id ;

    private Long inventoryId ;

    private Integer inventoryIdItem;

    // 订单号
    private String orderNo ;
    // 入库日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date roDate ;
    // 订单型号
    private String modelNo ;
    // 入库数
    private Integer roQty ;
    // 可用在库
    private Integer avaQty ;

    // 需求日期
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date requirementDate ;

    // 决算日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date countDate ;

    // 决算状态
    private Integer status ;

    // 推送决算数
    private Integer pushQty ;

    // 审批中数
    private Integer approveCountQty ;

    // 确认申请后的延期日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date delayDate ;

    // 确认决算数
    private Integer sureAccountQty ;

    // 延期数
    private Integer delayQty ;

    // 调库号
    private String transNo ;

    // 调库时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transTime ;
    // 调库数量(清算数)
    private Integer transQty ;
    // 显示该条订单申请时的LOT E价格
    private BigDecimal ePrice ;
    // E价格*决算数
    private BigDecimal eAmount ;
    // 物流中心为Bin
    private Boolean isBin ;

    private String charger ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime ;

    private String creator ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime ;

    private String modifier ;

    // 库存日志ID
    private String inventotyLogId ;

    private String batchNo;

    // 最大延期日
    private Date maxDelayDate;

    // 申请号
    private String applyNo;
    // 申请项号
    private String applyItemNo;

    // 决算说明
    private String accountDesc;

    // 延期说明
    private String delayDesc;

    // 实际延期数量
    private int factDelayQty;

    private int handStatus;

    private String accountApplyNo;

    private String wareHouseCode;

    private String warehouseName;

    private int approveQty;
}
