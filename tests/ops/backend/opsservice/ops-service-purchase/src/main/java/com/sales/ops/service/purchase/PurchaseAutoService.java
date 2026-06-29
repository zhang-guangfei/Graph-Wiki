package com.sales.ops.service.purchase;

import java.util.List;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;

/***
 * bug11473 WTSR2023000504-po发单自动化
 * 
 * @author SMC892N
 *
 */
public interface PurchaseAutoService {

	// 先将进入自动发单流程的请购单更新状态
	public List<OpsPurchaseStatusToWMDto> requestToReadyJP() throws OpsException;

	public List<OpsPurchaseStatusToWMDto> requestToReadyGZ() throws OpsException;

	public List<OpsPurchaseStatusToWMDto> requestToReadyManufacture() throws OpsException;

	public List<OpsPurchaseStatusToWMDto> requestToReadyOverSea() throws OpsException;

	// bug12330增加早九点海外发单供应商限制
	public List<OpsPurchaseStatusToWMDto> requestToReadyOverSeaIn(String dic) throws OpsException;

	// bug13940 海外发单区分发单时间，非字典参数中供应商的发单
	public List<OpsPurchaseStatusToWMDto> requestToReadyOverSeaNotIn(String dic) throws OpsException;

	// 将ready状态的请购单进行后续采购//bug15148变更函数返回值，在发单最后调用事件，避免缺失事件
	public List<OpsPurchaseStatusToWMDto> readyToPurchaseJP();

	public List<OpsPurchaseStatusToWMDto> readyToPurchaseGZ();

	public List<OpsPurchaseStatusToWMDto> readyToPurchaseManufacture();

	public List<OpsPurchaseStatusToWMDto> readyToPurchaseOverSea();

	public List<OpsPurchaseStatusToWMDto> readyToPurchaseOverSeaIn(String dic);

	public List<OpsPurchaseStatusToWMDto> readyToPurchaseOverSeaNotIn(String dic);

	// 自动发单
	public Integer purchaseAutoJP(boolean isAuto) throws Exception;

	public Integer purchaseAutoGZ(boolean isAuto) throws Exception;

	public Integer purchaseAutoManufacture(boolean isAuto) throws Exception;

	public Integer purchaseAutoOverSea(boolean isAuto) throws Exception;

	public Integer purchaseAutoOverSeaIn(boolean isAuto, String dic) throws Exception;

	public Integer purchaseAutoOverSeaNotIn(boolean isAuto, String dic) throws Exception;

}
