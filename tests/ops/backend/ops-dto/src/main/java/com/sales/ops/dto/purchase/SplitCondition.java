package com.sales.ops.dto.purchase;

import java.util.List;

import com.sales.ops.db.entity.OpsRequestpurchase;

public class SplitCondition {

	private String uuid;

	private List<OpsRequestpurchaseDto> tabledatas;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<OpsRequestpurchaseDto> getTabledatas() {
		return tabledatas;
	}

	public void setTabledatas(List<OpsRequestpurchaseDto> tabledatas) {
		this.tabledatas = tabledatas;
	}

}
