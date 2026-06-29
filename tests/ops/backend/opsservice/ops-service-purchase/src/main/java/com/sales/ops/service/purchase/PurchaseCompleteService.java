package com.sales.ops.service.purchase;

import java.util.List;

import com.sales.ops.dto.order.FinishPoDto;
import com.sales.ops.dto.order.FinishPoListForDto;

/**
 * 采购完纳
 */
public interface PurchaseCompleteService {

	/**
	 * 根据采购单号，查询完纳信息
	 * 
	 * @param info
	 * @return
	 */
	public List<FinishPoListForDto> getPoListPoNo(FinishPoDto info);

	/**
	 * 执行采购完纳
	 */
	public String doFinishPo(FinishPoListForDto finishPoListForDto) throws Exception;
}
