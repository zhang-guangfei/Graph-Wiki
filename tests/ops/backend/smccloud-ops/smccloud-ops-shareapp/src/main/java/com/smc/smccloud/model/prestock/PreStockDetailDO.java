package com.smc.smccloud.model.prestock;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author B90034
 * @since 2021-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("prestock_detail")
public class PreStockDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * prestock_apply.id
     */
    @TableField("apply_id")
    private Long applyId;

    /**
     * 申请项号
     */
    @TableField("item_no")
    private Integer itemNo;

    /**
     * 拆分型号项号
     */
    @TableField("split_item")
    private Integer splitItem;

    /**
     * 货期
     */
    @TableField("dlv_date")
    private Date dlvDate;

    /**
     * 申请型号
     */
    @TableField("apply_model_no")
    private String applyModelNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 希望出货类型:
     * 0-系统自动; 1-预约在库; 2-采购; 3-异仓调拨;
     */
    @TableField("exp_type")
    private String expType;

    @TableField("order_no")
    private String orderNo;

    @TableField("order_qty")
    private Integer orderQty;

    /**
     * shikomi要求日本出厂日期
     */
    @TableField("dlv_date_jp")
    private Date dlvDateJp;

    /**
     * 1-新申请型号,2-增加旧有备库数量
     */
    @TableField("new_flag")
    private String newFlag;

    /**
     * 1-提交中; 2-处理中; 4-驳回; 5-已确认; 6-已处理; 7-异常拦截, 9-取消; 10-暂不处理;
     */
    @TableField("status")
    private String status;

    /**
     * 供应商代码
     */
    @TableField("supplier_code")
    private String supplierCode;

    /**
     * 删除标识 1-删除
     */
    @TableField("delFlag")
    private Integer delFlag;

    /**
     * 特殊标识: 0-正常订货; 1-板; 2-组装原件;
     */
    @TableField("specMark")
    private String specMark;

    /**
     * 是否拆分型号
     */
    @TableField("splitModel")
    private Integer splitModel;

    @TableField("confirm_time")
    private Date confirmTime;

    @TableField("confirm_user")
    private String confirmUser;

    /**
     * 采购数
     */
    @TableField("po_qty")
    private Integer poQty;

    /**
     * 调库数
     */
    @TableField("stock_qty")
    private Integer stockQty;

    /**
     * 预占数量
     */
    @TableField("prepare_qty")
    private Integer prepareQty;


    /**
     * 发注点
     */
    @TableField("qtybin")
    private Integer qtybin;

    /**
     * 默认值=2
     */
    @TableField("bincell")
    private Integer bincell;

    /**
     * 拦截规则id
     */
    @TableField("intercept_id")
    private Integer interceptId;

    /**
     * 处理备注
     */
    @TableField("process_text")
    private String processText;

    /**
     * 特殊出货标识: 64-ROHS10
     */
    @TableField("spec_exp_type")
    private Integer specExpType;

    /**
     * 运输方式: 0-海运, 1-空运, 4-快船
     */
    @TableField("transType")
    private String transType;

    /**
     * PPL号
     */
    @TableField("ppl_no")
    private String pplNo;

    /**
     * 项目号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 用户设备名
     */
    @TableField("equipment")
    private String equipment;

    /**
     * 设备数量
     */
    @TableField("equipment_qty")
    private Integer equipmentQty;

    /**
     * 用途
     */
    @TableField("purpose")
    private String purpose;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 产品名称
     */
    @TableField("model_name")
    private String modelName;

    /**
     * 特价号
     */
    @TableField("spr_answer_no")
    private String sprAnswerNo;

    /**
     * 含税单价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * E价格
     */
    @TableField("eprice")
    private BigDecimal eprice;

    /**
     * E率
     */
    @TableField("ediscount")
    private Double ediscount;

    /**
     * 关联采购单号
     */
    @TableField("order_nos")
    private String orderNos;

    /**
     * 备库类型
     */
    @TableField("stock_type")
    private String stockType;

    /**
     * 集团号
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    /**
     * 回复状态:
     * 1-未同步;
     * 2-已同步;
     */
    @TableField("reply_status")
    private Integer replyStatus;

    /**
     * 回复结果 (同步失败原因)
     */
    @TableField("reply_result")
    private String replyResult;

    /**
     * 库存属性ID
     */
    @TableField("inventory_property_id")
    private Long inventoryPropertyId;

    /**
     * notice_task.batch_no
     */
    @TableField("batch_no")
    private String batchNo;

    @TableField(value = "create_Time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_Time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    @TableField("prepare_orders")
    private String prepareOrders;

    // cproduct物料号
    @TableField("cproduct_no")
    private String cproductNo;

}
