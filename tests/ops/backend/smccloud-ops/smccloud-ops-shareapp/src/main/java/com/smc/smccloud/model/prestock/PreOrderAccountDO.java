package com.smc.smccloud.model.prestock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuweidong
 * @create 2023/12/7 09:52
 * @description
 */
@Data
@TableName("preorder_account")
public class PreOrderAccountDO
{
    /**ID*/
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id ;

    @TableField("inventory_id")
    private Long inventoryId ;

    @TableField("model_no")
    private String modelNo ;

    // 库房
    @TableField("warehouse_code")
    private String warehouseCode ;

    // 批属性ID
    @TableField("inventory_property_id")
    private Long inventoryPropertyId ;

    // 库存分类：顾客在库，战略在库、通用在库
    @TableField("inventory_type_code")
    private String inventoryTypeCode ;

    // 客户号
    @TableField("customer_no")
    private String customerNo ;

    // PPL代码
    @TableField("ppl")
    private String ppl ;

    // 项目号
    @TableField("project_code")
    private String projectCode ;

    // 客户群代码
    @TableField("group_customer_no")
    private String groupCustomerNo ;


    // 入库数量（2年内)
    @TableField("ro_qty")
    private Integer roQty ;

    // 有效在库数
    @TableField("ava_qty")
    private Integer avaQty ;

    // 推送决算数
    @TableField("push_qty")
    private Integer pushQty ;

    // 延期数量 实际延期数量
    @TableField("delay_qty")
    private Integer delayQty ;
    // 审批中数
    @TableField("approve_qty")
    private Integer approveQty ;
    // 订货频率12个月
    @TableField("frequency12")
    private Integer frequency12 ;
    // 月均12个月
    @TableField("averageof12")
    private Integer averageof12 ;
    // 保有月=有效在库数/月均
    @TableField("retention_month")
    private Integer retentionMonth ;

    // push_qty*eprice
    @TableField("eamount")
    private BigDecimal eAmount ;

    // BIN(物流中心或ALL存在1个BIN设置，就显示“是”)
    @TableField("is_bin")
    private Boolean isBin ;
    // 担当
    @TableField("charger")
    private String charger ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime ;

    @TableField("creator")
    private String creator ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("modify_time")
    private Date modifyTime ;

    @TableField("modifier")
    private String modifier ;

    // 最终调出数量 (清算数)
    @TableField("trans_qty")
    private Integer transQty ;
    // 营所/子行业
    @TableField("dept_no")
    private String deptNo ;

}
