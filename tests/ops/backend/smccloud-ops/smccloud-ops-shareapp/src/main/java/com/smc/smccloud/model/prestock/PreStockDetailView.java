package com.smc.smccloud.model.prestock;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-02-06 15:10
 * Description: PreStockApply & PreStockDetail 视图
 */

@Data
@TableName("prestock_detail_view")
public class PreStockDetailView {

    /**
     * 申请id
     */
    @TableField("apply_id")
    private String applyId;

    /**
     * 申请号
     */
    @TableField("apply_no")
    private String applyNo;

    /**
     * 申请类型: 1-专备; 2-SHIKOMI; 3-营业情报
     */
    @TableField("apply_type")
    private String applyType;

    /**
     * 1-普通申请 2-申请自动周转 3-自动补库
     */
    @TableField("repl_type")
    private String replType;

    @TableField("dept_no")
    private String deptNo;

    @TableField("customer_no")
    private String customerNo;

    @TableField("user_no")
    private String userNo;

    /**
     * 申请状态
     */
    @TableField("apply_status")
    private String applyStatus;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("apply_time")
    private Date applyTime;

    @TableField("apply_psn")
    private String applyPsn;

    /**
     * 备库仓库
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * shikomi类型
     */
    @TableField("shikomi_class")
    private String shikomiClass;

//    @TableField("approver_name")
//    private String approverName;

//    @TableField("approver_no")
//    private String approverNo;

    @TableField("apply_psn_no")
    private String applyPsnNo;

    /**
     * 行业
     */
    @TableField("ind_Code")
    private String indCode;

    @TableField("passkey")
    private String passkey;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @TableField("approve_time")
    private Date approveTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @TableField("create_Time")
    private Date createTime;

    /* detail */
    /**
     * 客户要求货期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("dlv_date")
    private Date dlvDate;

    /**
     * 申请项id
     */
    @TableField("detail_id")
    private Long detailId;

    /**
     * 申请型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 申请型号数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 发注点
     */
    @TableField("qtybin")
    private Integer qtyBin;

    /**
     * shikomi要求日本出厂日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("dlv_date_jp")
    private Date dlvDateJP;

    /**
     * 1-新规,2-继续
     */
    @TableField("new_flag")
    private String newFlag;

    /**
     * 申请项状态:
     * 1-提交中; 2-处理中; 4-驳回; 5-已确认; 6-已处理; 7-异常拦截, 9-取消; 10-暂不处理;
     */
    @TableField("detail_status")
    private String detailStatus;

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
     * 0-正常订货; 1-板; 2-组装原件;
     */
    @TableField("specMark")
    private String specMark;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @TableField("confirm_time")
    private Date confirmTime;

    @TableField("confirm_user")
    private String confirmUser;

    @TableField("item_no")
    private Integer itemNo;

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
     * 处理备注
     */
    @TableField("process_text")
    private String processText;

    /**
     * 特殊出货标识: 64-ROHS10
     */
    @TableField("spec_exp_type")
    private String specExpType;

    /**
     * 运输方式: 0-海运, 1-空运, 4-快船
     */
    @TableField("transType")
    private String transType;

    /**
     * 申请型号
     */
    @TableField("apply_model_no")
    private String applyModelNo;

    /**
     * detail.stock_type
     */
    @TableField("stock_type")
    private String stockType;

    /**
     * detail.project_no
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * detail.ppl_no
     */
    @TableField("ppl_no")
    private String pplNo;

    /**
     * detail.group_customer_no
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    /**
     * detail.reply_status
     * 回复状态:
     * 1-未同步;
     * 2-已同步;
     */
    @TableField("reply_status")
    private Integer replyStatus;

    /**
     * detail.reply_result
     * 回复结果 (同步失败原因)
     */
    @TableField("reply_result")
    private String replyResult;

    /**
     * notice_task.batch_no
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 关联单号
     */
    @TableField("order_nos")
    private String orderNos;
    /**
     * 关联预占订单
     */
    @TableField("prepare_orders")
    private String prepareOrders;

    @TableField("eprice")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal eprice;

    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal eAmount;

    /**
     * 关联调库单号
     */
    @TableField(exist = false)
    private String transferNos;

    // 准备单号
    @TableField(exist = false)
    private String zbNo;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 客户名称
     */
    @TableField(exist = false)
    private String customerName;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

}
