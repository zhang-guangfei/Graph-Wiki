package com.smc.smccloud.model.prestock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: B90034
 * Date: 2021-11-03 16:14
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreStockDetailDTO {

    private Long id;

    /**
     * prestock_apply.id
     */
    private Long applyId;

    /**
     * 申请项号
     */
    private Integer itemNo;

    /**
     * 拆分型号项号
     */
    private Integer splitItem;

    /**
     * 货期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 申请型号
     */
    private String applyModelNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 希望出货类型:
     * 0-系统自动; 1-预约在库; 1-采购; 3-异仓调拨;
     */
    private String expType;

    private String orderNo;

    private Integer orderQty;

    /**
     * shikomi要求日本出厂日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDateJp;

    /**
     * 1-新申请型号; 2-增加旧有备库数量
     */
    private String newFlag;

    /**
     * 1-提交中; 2-处理中; 4-驳回; 5-已确认; 6-已处理; 7-异常拦截, 9-取消; 10-暂不处理;
     */
    private String status;

    /**
     * 供应商
     */
    private String supplierCode;

    /**
     * 发注点
     */
    private Integer qtybin;

    /**
     * 默认值=2
     */
    private Integer bincell;

    /**
     * 拦截规则id
     */
    private Integer interceptId;

    /**
     * 处理备注
     */
    private String processText;

    /**
     * true-rohs10
     */
    private Boolean rohs10;

    /**
     * 整单组装
     * 0-正常;
     * 1-板组装;
     * 2-阀组装;
     */
    private String specMark;

    /**
     * 运输方式: 0-海运, 1-空运, 4-快船
     */
    private String transType;

    /**
     * PPL号
     */
    private String pplNo;

    /**
     * 项目号
     */
    private String projectNo;

    /**
     * 用户装置名称
     */
    private String equipment;

    /**
     * 配置数量
     */
    private Integer equipmentQty;

    /**
     * 用途
     */
    private String purpose;

    /**
     * 备注
     */
    private String remark;

    /**
     * 产品名称
     */
    private String modelName;

    /**
     * 特价号
     */
    private String sprAnswerNo;

    /**
     * 含税单价
     */
    private BigDecimal price;

    /**
     * E价格
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal eprice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal eAmount;

    /**
     * E率
     */
    private Double ediscount;

    /**
     * 备库类型
     */
    private String stockType;

    /**
     * 集团号
     */
    private String groupCustomerNo;

    /**
     * 采购数
     */
    private Integer poQty;

    /**
     * 调库数
     */
    private Integer stockQty;

    /**
     * 回复状态:
     * 1-未同步;
     * 2-已同步;
     */
    private Integer replyStatus;

    /**
     * 回复结果 (同步失败原因)
     */
    private String replyResult;
    /**
     * 在库数
     */
    private  int qtyOnHand;
    /**
     * 在途数
     */
    private  int ordingQty;

    /**
     * Bindata binQty
     */
    private int binQty;
    /**
     * 月用量数
     */
    private  int monthAvgQty;

    /**
     * 可用月数
     */
    private  BigDecimal canUseMonths;

    /**
     * notice_task.batch_no
     */
    private String batchNo;

    /**
     * 建立时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    /**
     * ops自行申请审核确认的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date confirmTime;



    // cproduct物料号
    private String cproductNo;
}
