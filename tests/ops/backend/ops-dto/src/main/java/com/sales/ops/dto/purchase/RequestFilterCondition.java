package com.sales.ops.dto.purchase;

import java.util.List;

/**
 * 请购单合并采购页面，筛选所用到的实体
 */
public class RequestFilterCondition {

	/**
	 * 需要筛选的供应商清单
	 */
	private List<String> filtersuppilyid;

	/**
	 * 筛选完成的数据
	 */
	private  List<OpsRequestpurchaseDto>  filterdata;

	/**
	 * 未筛选的完整数据
	 */
	private List<OpsRequestpurchaseDto> onedata;

	/**
	 * 后端筛选的供应商列表
	 */
	private List<String> suppilylist;

	private String orderby;

	private  String column;

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public List<String> getFiltersuppilyid() {
		return filtersuppilyid;
	}

	public void setFiltersuppilyid(List<String> filtersuppilyid) {
		this.filtersuppilyid = filtersuppilyid;
	}

	public List<OpsRequestpurchaseDto> getFilterdata() {
		return filterdata;
	}

	public void setFilterdata(List<OpsRequestpurchaseDto> filterdata) {
		this.filterdata = filterdata;
	}

	public List<OpsRequestpurchaseDto> getOnedata() {
		return onedata;
	}

	public void setOnedata(List<OpsRequestpurchaseDto> onedata) {
		this.onedata = onedata;
	}

	public List<String> getSuppilylist() {
		return suppilylist;
	}

	public void setSuppilylist(List<String> suppilylist) {
		this.suppilylist = suppilylist;
	}
}
