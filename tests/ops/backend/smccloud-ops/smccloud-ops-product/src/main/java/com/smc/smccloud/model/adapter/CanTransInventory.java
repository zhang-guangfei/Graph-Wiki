package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "可调库库存数据")
public class CanTransInventory implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1516916166891041142L;

	// 提示信息
	private String msg;

	// 库房信息：库房代码及库房名称
	private List<StockInfo> stockList;

	// 型号对应的可调库库房信息
	private Map<String, List<Inventory>> transInventory;

}