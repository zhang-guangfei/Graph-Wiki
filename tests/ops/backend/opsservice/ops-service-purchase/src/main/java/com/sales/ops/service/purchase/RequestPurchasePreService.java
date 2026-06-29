package com.sales.ops.service.purchase;

import java.util.List;

import com.sales.ops.db.entity.OpsRequestpurchase;

public interface RequestPurchasePreService {
	/***
	 * 更新请购单状态、供应商及指定制造出荷日
	 */
	public Integer updateRequestPurchase();

	/**
	 * 补库订单预处理
	 * 
	 * @return
	 */
	public Integer updateRequestPurchaseBin();

	/**
	 * 计算期望出荷日及运输方式
	 */
	public List<OpsRequestpurchase> calDlvInfo(List<OpsRequestpurchase> list);

    // 拦截判断
    List<OpsRequestpurchase> intercept(List<OpsRequestpurchase> list);

    /**
	 * 补充计算采购仓及SMCCODE
	 * 
	 * @param item
	 * @return
	 */
	public OpsRequestpurchase calWarehouseAndSmccode(OpsRequestpurchase item, boolean isEdit);
}
