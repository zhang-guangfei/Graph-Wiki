package com.smc.smccloud.model.Purchase;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smc.smccloud.core.utils.PublicUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_purchaseInvoice")
public class OpsPurchaseInvoiceDO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 采购单号
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 日本PO号
	 */
	@TableField("poNo")
	private String poNo;

	/**
	 * 一般为1，组装时主单+子项号为采购单号、linitem为子项号，集装阀为主单号为采购单号、linitem为子项号
	 */
	@TableField("lineItem")
	private Integer lineItem;

	/**
	 * 请购单号
	 */
	@TableField("orderNo")
	private String orderNo;

	/**
	 * 型号
	 */
	@TableField("modelNo")
	private String modelNo;

	/**
	 * 数量
	 */
	@TableField("quantity")
	private Integer quantity;

	/**
	 * 标准价
	 */
	@TableField("stdPrice")
	private Double stdPrice;

	/**
	 * 运输方式
	 */
	@TableField("transType")
	private String transType;

	/**
	 * 采购日期
	 */
	@TableField("purchaseDate")
	private String purchaseDate;

	/**
	 * 期望货期
	 */
	@TableField("hopeDeliveryDate")
	private String hopeDeliveryDate;

	/**
	 * 供应商代码
	 */
	@TableField("supplierId")
	private String supplierId;

	/**
	 * 状态
	 */
	@TableField("stateCode")
	private String stateCode;

	/**
	 * 阀汇流板标识
	 */
	@TableField("specMark")
	private String specMark;

	/**
	 * 收货库房
	 */
	@TableField("receiveWarehouseId")
	private String receiveWarehouseId;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 指定制造出荷日
	 */
	@TableField("hopeExportDate")
	private String hopeExportDate;

	/**
	 * 交货期问询号
	 */
	@TableField("inqNo")
	private String inqNo;

	/**
	 * SHIKOMI批复号
	 */
	@TableField("shikomiAnswerNo")
	private String shikomiAnswerNo;

	/**
	 * 部门代码
	 */
	@TableField("deptNo")
	private String deptNo;

	/**
	 * 订单区分1一时通过品,2 库存
	 */
	@TableField("deliveryFlag")
	private String deliveryFlag;

	/**
	 * 国别代码
	 */
	@TableField("smcCode")
	private String smcCode;

	/**
	 * 海关申报号
	 */
	@TableField("hsCode")
	private String hsCode;

	/**
	 * 绿标标识
	 */
	@TableField("greenCode")
	private String greenCode;

	/**
	 * 客户代码
	 */
	@TableField("customerNo")
	private String customerNo;

	/**
	 * 用户代码
	 */
	@TableField("userNo")
	private String userNo;

	/**
	 * 货架号
	 */
	@TableField("locationNo")
	private String locationNo;

	/**
	 * 营业情报号
	 */
	@TableField("salesInfoNo")
	private String salesInfoNo;

	/**
	 * 供应商型号
	 */
	@TableField("supplierPartNo")
	private String supplierPartNo;

	/**
	 * 进口FOB价（原币种）
	 */
	@TableField("importFobPriceOriginal")
	private Double importFobPriceOriginal;

	/**
	 * 币种代码
	 */
	@TableField("importCurrencyId")
	private String importCurrencyId;

	/**
	 * 日本不接受整型号，拆分后传输标记
	 */
	@TableField("jpSplitVT")
	private Integer jpSplitVT;

	/**
	 * 采购操作者
	 */
	@TableField("operatorId")
	private String operatorId;

	/**
	 * 北京工厂CN号
	 */
	@TableField("cnNo")
	private String cnNo;

	/**
	 * 发票号
	 */
	@TableField("invoiceNo")
	private String invoiceNo;

	/**
	 * 供应商订单号（手配号）
	 */
	@TableField("replyOrderNo")
	private String replyOrderNo;

	/**
	 * 接单时间
	 */
	@TableField("replyOrderDate")
	private String replyOrderDate;

	/**
	 * 供应商回复货期
	 */
	@TableField("replyExportDate")
	private String replyExportDate;

	/**
	 * 在途数量
	 */
	@TableField("qtyTrans")
	private Integer qtyTrans;

	/**
	 * 到货数量
	 */
	@TableField("qtyReceive")
	private Integer qtyReceive;

	/**
	 * 入库数量
	 */
	@TableField("qtyImport")
	private Integer qtyImport;

	/**
	 * 入库日期
	 */
	@TableField("impdate")
	private String impdate;

	/**
	 * 变更交货期1
	 */
	@TableField("dlvModDate1")
	private String dlvModDate1;

	/**
	 * 变更交货期2
	 */
	@TableField("dlvModDate2")
	private String dlvModDate2;

	/**
	 * 变更交货期3
	 */
	@TableField("dlvModDate3")
	private String dlvModDate3;

	/**
	 * 变更交货期1的时间
	 */
	@TableField("dlvModDate1Time")
	private String dlvModDate1Time;

	/**
	 * 变更交货期2的时间
	 */
	@TableField("dlvModDate2Time")
	private String dlvModDate2Time;

	/**
	 * 变更交货期3的时间
	 */
	@TableField("dlvModDate3Time")
	private String dlvModDate3Time;

	/**
	 * 交货期变更原因
	 */
	@TableField("reasonRemark")
	private String reasonRemark;

	/**
	 * 变更后的运输方式
	 */
	@TableField("transTypeMod")
	private String transTypeMod;

	/**
	 * 催促号1
	 */
	@TableField("dlvAnswerNo1")
	private String dlvAnswerNo1;

	/**
	 * 催促号2
	 */
	@TableField("dlvAnswerNo2")
	private String dlvAnswerNo2;

	/**
	 * 生产工厂
	 */
	@TableField("produceFactory")
	private String produceFactory;

	/**
	 * 生产holon
	 */
	@TableField("produceHolon")
	private String produceHolon;

	/**
	 * 错误说明
	 */
	@TableField("errdescription")
	private String errdescription;

	/**
	 * 理论入库日
	 */
	@TableField("imDateTheory")
	private String imDateTheory;

	/**
	 * 实际入库时间
	 */
	@TableField("imDateInFact")
	private String imDateInFact;

	/**
	 * 开始生产时间
	 */
	@TableField("beginProduceDate")
	private String beginProduceDate;

	/**
	 * 写入日期
	 */
	@TableField("sendTime")
	private String sendTime;

	/**
	 * 更新日期
	 */
	@TableField("updateTime")
	private String updateTime;

	/**
	 * 2022-1-22 新增，业务人员备注信息
	 */
	@TableField("serverremark")
	private String serverremark;

	@TableField("invoiceId")
	private Long invoiceId;

	@TableField("caseNo")
	private String caseNo;

	@TableField("barCode")
	private String barCode;

	@TableField("vipCode")
	private String vipCode;

	@TableField("splitItemNo")
	private Integer splitItemNo;

	@TableField("itemNo")
	private Integer itemNo;

	@TableField("purchaseType")
	private String purchaseType;

	// bug14066 增加对ordertype字段赋值
	@TableField("ordType")
	private String ordType;

	@TableField(exist = false)
	private String fullOrderNo;

	public void setFullOrderNo(String fullOrderNo) {
		this.fullOrderNo = this.orderNo;
		if (PublicUtil.isNotEmpty(this.itemNo)) {
			this.fullOrderNo = this.orderNo + "-" + this.itemNo;
			if (PublicUtil.isNotEmpty(this.splitItemNo)) {
				this.fullOrderNo = this.orderNo + "-" + this.itemNo + "-" + this.splitItemNo;
			}
		}
	}

	public String getFullOrderNo() {
		String fullOrderNo = this.orderNo;
		if (PublicUtil.isNotEmpty(this.itemNo)) {
			fullOrderNo = this.orderNo + "-" + this.itemNo;
			if (PublicUtil.isNotEmpty(this.splitItemNo)) {
				fullOrderNo = this.orderNo + "-" + this.itemNo + "-" + this.splitItemNo;
			}
		}
		return fullOrderNo;
	}

}
