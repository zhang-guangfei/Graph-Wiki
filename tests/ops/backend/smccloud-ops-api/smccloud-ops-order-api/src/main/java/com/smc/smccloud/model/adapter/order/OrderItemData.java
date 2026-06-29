package com.smc.smccloud.model.adapter.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "订单子项实体")
public class OrderItemData implements Serializable {

    private static final long serialVersionUID = -1242493306302174690L;

    @ApiModelProperty(value = "订单号")
    private String mainOrderNo;

    @ApiModelProperty(value = "客户代码")
    private String customerNo;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "用户代码")
    private String userNo;

    @ApiModelProperty(value = "项号")
    private String itemNo;

    @ApiModelProperty(value = "十位子订单号")
    private String orderNo;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "客户物料号")
    private String customerProductNo;

    @ApiModelProperty(value = "数量")
    private int quantity;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "库房")
    private String stockCode;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "订单状态代码")
    private String orderStatusCode;

    // RcvDetail OptCode
    @ApiModelProperty(value = "订单状态bjCode")
    private String bjCode;

    @ApiModelProperty(value = "订单状态optState")
    private String optState;

    @ApiModelProperty(value = "shikomi")
    private String shikomi;

    @ApiModelProperty(value = "特价号")
    private String specOfferNo;

    @ApiModelProperty(value = "预占库存号")
    private String preSaleOrderNo;

    // endinvoice_sh OptCode
    @ApiModelProperty(value = "订单状态shCode")
    private String shCode;

    @ApiModelProperty(value = "E率")
    private BigDecimal eRate;

    @ApiModelProperty(value = "最终用户价格")
    private BigDecimal endUserPrice;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "客户希望交货日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date dlvDate;

    @ApiModelProperty(value = "物流发货日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date warehouseSendDate;

    @ApiModelProperty(value = "交货期")
    private int dlvDay;

    @ApiModelProperty(value = "发票号")
    private String invoiceNo;

    @ApiModelProperty(value = "优先级")
    private String priorityLevel;

    @ApiModelProperty(value = "日本运输方式")
    private String ordTransType;

    @ApiModelProperty(value = "日本运输方式")
    private String ordTransTypeDesc;

    // 拆分信息
    @ApiModelProperty(value = "拆分子表")
    private List<OrderItemData> splitList;

    // 子项发票流程
  /*  @ApiModelProperty(value = "发票流程")
    private List<FlowInfo> invoiceFlow;
*/
    // 发票流当前节点
    @ApiModelProperty(value = "发票流程当前节点")
    private int activeInvoiceIndex;

    // 子项货物流程
    @ApiModelProperty(value = "货物流程")
    private List<FlowInfo> goodsFlow;

    // 货物流当前节点
    @ApiModelProperty(value = "货物流程当前节点")
    private int activeGoodsIndex;

    // 是否可更改出货方式
    @ApiModelProperty(value = "是否可更改出货方式")
    private boolean canChangeDeliveryModel = false;

    // 是否可更改货期
    @ApiModelProperty(value = "是否可更改货期")
    private boolean canChangeDeliveryDate = false;

    // 是否可催促
    @ApiModelProperty(value = "是否可催促")
    private boolean canPress = true;

    @ApiModelProperty(value = "不可催促时返回信息")
    private String pressMsg = "";

    // 是否可删单
    @ApiModelProperty(value = "是否可删单")
    private boolean canDelete = true;

    @ApiModelProperty(value = "出库区分")
    private String expInvCode;

  /*  @ApiModelProperty(value = "流程图节点")
    private List<OrderNode> orderNode;

    @ApiModelProperty(value = "流程图连线")
    private List<OrderLine> orderLine;

    @ApiModelProperty(value = "物流信息")
    private OrderDeliveryInfo orderDeliveryInfo;
*/
    @ApiModelProperty(value = "删单请求状态")
    private String deleteOrderStatus;

    @ApiModelProperty(value = "客户PO")
    private  String corderNo;

}
