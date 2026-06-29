package com.smc.smccloud.model.prestock;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuweidong
 * @create 2023/12/28 13:23
 * @description
 */
@Data
public class PreOrderAccountDetailDTO    {

    private String companyNo ;
    private String companyName;
    private String parentNo ;
    private String parentName ;
    private String salesNo ;
    private String salesName ;
    private String deptNo ;
    private String deptName ;

    private String warehouseCode;
    private String  inventoryTypeCode;
    private String customerNo;
    private String ppl ;
    private String projectCode ;
    private String groupCustomerNo ;
    private String salesInfoNo ;

    private Long id ;

    private Long inventoryId ;

    private String orderNo ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date roDate ;

    private String modelNo ;

    private Integer roQty ;

    private Integer avaQty ;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date requirementDate ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date countDate ;

    private int status ;

    private String statusName;

    private Integer pushQty ;

    private Integer approveCountQty ;

    private Integer approveDelayQty ;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date delayDate ;

    private Integer clearQty ;

    private Integer delayQty ;

    private String transNo ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transTime ;

    private Integer transQty ;

    private BigDecimal ePrice ;

    private BigDecimal eAmount ;

    private Boolean isBin ;

    private String binStr ;

    private String charger ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date createTime ;

    private String creator ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date modifyTime ;

    private String modifier ;

    private String applyNo;
    // 申请项号
    private String applyItemNo;

    // 决算说明
    private String accountDesc;

    // 延期说明
    private String delayDesc;

    // 实际延期数量
    private int factDelayQty;

    // 确认决算数
    private Integer sureAccountQty ;

    private String accountApplyNo;

    private int approveQty;
}
