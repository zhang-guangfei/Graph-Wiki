package com.sales.ops.dto.order;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $ @description：
 * @date ：Created in 2023/8/17 10:16
 */
public class FinishPoListForDto implements Serializable {
	private static final long serialVersionUID = 8076399199452977819L;

	// 采购单号
	private String pOrderNo;

	// 采购单项号
	private Integer pOrderItem;

	// 采购单拆分号
	private Integer pSplitNo;

	private String pFullNo;

	// 型号
	private String modelNo;

	// 采购数量 订单数量
	private Integer pQty;

	// 已发数量 非P
	private Integer arrQty = 0;

	// 供应商
	private String supplierId;

	// 采购状态
	private String poStatus;

	// 是否完纳 勾选
	private boolean checked;

	private boolean disabled;

	private boolean inputDisabled;

	// 采购单完纳数量
	private Integer finishPoQty;

	private String finishMsg;

	// 备注
	private String msg;

	// 操作人
	private String operator;

	// 是否可删除
	private boolean canDelete = false;

	// 是否可完纳
	private boolean canFinish = false;

	// 执行采购操作 0 不操作 1 删单 2 完纳
	private Integer doStatus = 0;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getpOrderNo() {
		return pOrderNo;
	}

	public void setpOrderNo(String pOrderNo) {
		this.pOrderNo = pOrderNo;
	}

	public Integer getpOrderItem() {
		return pOrderItem;
	}

	public void setpOrderItem(Integer pOrderItem) {
		this.pOrderItem = pOrderItem;
	}

	public Integer getpSplitNo() {
		return pSplitNo;
	}

	public void setpSplitNo(Integer pSplitNo) {
		this.pSplitNo = pSplitNo;
	}

	public String getpFullNo() {
		return pFullNo;
	}

	public void setpFullNo(String pFullNo) {
		this.pFullNo = pFullNo;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public Integer getpQty() {
		return pQty;
	}

	public void setpQty(Integer pQty) {
		this.pQty = pQty;
	}

	public Integer getArrQty() {
		return arrQty;
	}

	public void setArrQty(Integer arrQty) {
		this.arrQty = arrQty;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getFinishPoQty() {
		return finishPoQty;
	}

	public void setFinishPoQty(Integer finishPoQty) {
		this.finishPoQty = finishPoQty;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFinishMsg() {
		return finishMsg;
	}

	public void setFinishMsg(String finishMsg) {
		this.finishMsg = finishMsg;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isInputDisabled() {
		return inputDisabled;
	}

	public void setInputDisabled(boolean inputDisabled) {
		this.inputDisabled = inputDisabled;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public boolean isCanFinish() {
		return canFinish;
	}

	public void setCanFinish(boolean canFinish) {
		this.canFinish = canFinish;
	}

	public Integer getDoStatus() {
		return doStatus;
	}

	public void setDoStatus(Integer doStatus) {
		this.doStatus = doStatus;
	}
}
