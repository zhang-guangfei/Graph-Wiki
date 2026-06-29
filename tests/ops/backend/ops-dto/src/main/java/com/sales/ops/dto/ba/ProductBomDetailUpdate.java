package com.sales.ops.dto.ba;

import java.io.Serializable;
import java.util.List;

import com.sales.ops.db.entity.ProductBomDetail;

//更新拆分数据信息
public class ProductBomDetailUpdate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2409574266756163657L;

	private boolean isvaild;

	private List<List<ProductBomDetail>> list;

	public boolean isIsvaild() {
		return isvaild;
	}

	public void setIsvaild(boolean isvaild) {
		this.isvaild = isvaild;
	}

	public List<List<ProductBomDetail>> getList() {
		return list;
	}

	public void setList(List<List<ProductBomDetail>> list) {
		this.list = list;
	}

}
