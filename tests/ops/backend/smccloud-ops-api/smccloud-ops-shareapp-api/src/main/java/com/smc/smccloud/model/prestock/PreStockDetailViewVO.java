package com.smc.smccloud.model.prestock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/8/16 10:58
 * @Descripton TODO
 */
@Data
public class PreStockDetailViewVO {
    /**
     * 申请id
     */
    private String applyId;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 申请类型: 1-专备; 2-SHIKOMI; 3-营业情报
     */
    private String applyType;

    /**
     * 1-普通申请 2-申请自动周转 3-自动补库
     */
    private String replType;

    private String deptNo;

    private String customerNo;

    private String userNo;

    /**
     * 申请状态
     */
    private String applyStatus;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyTime;

    private String applyPsn;

    /**
     * 备库仓库
     */
    private String warehouseCode;

    /**
     * shikomi类型
     */
    private String shikomiClass;

    private String applyPsnNo;

    /**
     * 行业
     */
    private String indCode;

    private String passkey;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date approveTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    /* detail */
    /**
     * 客户要求货期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 申请项id
     */
    private Long detailId;

    /**
     * 申请型号
     */
    private String modelNo;

    /**
     * 申请型号数量
     */
    private Integer quantity;

    /**
     * 发注点
     */
    private Integer qtyBin;

    /**
     * shikomi要求日本出厂日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDateJP;

    /**
     * 1-新规,2-继续
     */
    private String newFlag;

    /**
     * 申请项状态:
     * 1-提交中; 2-处理中; 4-驳回; 5-已确认; 6-已处理; 7-异常拦截, 9-取消; 10-暂不处理;
     */
    private String detailStatus;

    /**
     * 供应商代码
     */
    private String supplierCode;

    /**
     * 删除标识 1-删除
     */
    private Integer delFlag;

    /**
     * 0-正常订货; 1-板; 2-组装原件;
     */
    private String specMark;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date confirmTime;

    private String confirmUser;

    private Integer itemNo;

    /**
     * 采购数
     */
    private Integer poQty;

    /**
     * 调库数
     */
    private Integer stockQty;

    /**
     * 处理备注
     */
    private String processText;

    /**
     * 特殊出货标识: 64-ROHS10
     */
    private String specExpType;

    /**
     * 运输方式: 0-海运, 1-空运, 4-快船
     */
    private String transType;

    /**
     * 申请型号
     */
    private String applyModelNo;

    /**
     * detail.stock_type
     */
    private String stockType;

    /**
     * detail.project_no
     */
    private String projectNo;

    /**
     * detail.ppl_no
     */
    private String pplNo;

    /**
     * detail.group_customer_no
     */
    private String groupCustomerNo;

    /**
     * detail.reply_status
     * 回复状态:
     * 1-未同步;
     * 2-已同步;
     */
    private Integer replyStatus;

    /**
     * detail.reply_result
     * 回复结果 (同步失败原因)
     */
    private String replyResult;

    /**
     * notice_task.batch_no
     */
    private String batchNo;

    /**
     * 关联单号
     */
    private String orderNos;
    /**
     * 关联预占订单
     */
    private String prepareOrders;
    /**
     * 关联调库单号
     */
    private String transferNos;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 用户名称
     */
    private String userName;
}
