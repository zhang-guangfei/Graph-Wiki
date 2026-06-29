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
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/12/8 15:11
 * @description
 */
@Data
public class PreOrderAccountDTO {

    private Long id ;
    // 库存id
    private Long inventoryId ;
    // 型号
    private String modelNo ;
    // 仓库代码
    private String warehouseCode ;
    // 库存属性id
    private Long inventoryPropertyId ;
    // 库存类型 eg: GK-TY,GK-PJ,GK-PPL,ZL-PJ,ZL-JT,ZL-HY,ZL-CP
    private String inventoryTypeCode ;
    // 客户
    private String customerNo ;
    // ppl代码
    private String ppl ;
    // 项目号
    private String projectCode ;
    private String groupCustomerNo ;
    // 营业情报号
    private String salesInfoNo ;
    // 两年范围内累计入库数量
    private Integer roQty ;
    // 有效在库数
    private Integer avaQty ;
    // 推送决算数
    private Integer pushQty ;
    // 延期数量
    private Integer delayQty ;
    // 审批中数
    private Integer approveQty ;
    // 清算数
    private Integer clearQty ;
    // 订货频率12个月
    private Integer frequency12 ;
    // 月均12个月
    private Integer averageof12 ;
    // 保有月=有效在库数/月均
    private Integer retentionMonth ;

    // push_qty*eprice
    private BigDecimal eAmount ;
    // BIN(物流中心或ALL存在1个BIN设置，就显示“是”)
    private Boolean isBin ;

    private String binStr;
    // 担当
    private String charger ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime ;
    private String creator ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime ;
    private String modifier ;
    /**
     *1待决算
     *2待审批
     *3待清算
     *4延期中
     *5已清算
     */
    private int status ;
    // 最终调出数量
    private Integer transQty ;
    private String companyNo ;
    private String companyName ;
    private String parentNo ;
    private String parentName ;
    private String salesNo ;
    private String salesName ;
    // 营所/子行业
    private String deptNo ;
    private String deptName ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date requestDate ;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private List<PreOrderAccountDetailDO> detail;


}
