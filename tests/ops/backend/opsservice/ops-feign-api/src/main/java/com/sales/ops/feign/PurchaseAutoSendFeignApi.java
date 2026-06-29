package com.sales.ops.feign;

import java.util.List;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.util.CommonResult;

@FeignClient(name = "po-service", contextId = "AutoSend")
public interface PurchaseAutoSendFeignApi {

	@RequestMapping(value = "/autoSend/requestToReadyJP")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyJP();

	@RequestMapping(value = "/autoSend/requestToReadyGZ")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyGZ();

	@RequestMapping(value = "/autoSend/requestToReadyManufacture")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyManufacture();

	@RequestMapping(value = "/autoSend/requestToReadyOverSea")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyOverSea();

	// bug12330增加早九点海外发单供应商限制// bug13940 使用字典参数控制发单的供应商
	@RequestMapping(value = "/autoSend/requestToReadyOverSeaIn")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyOverSeaIn(@RequestParam("dic") String dic);

	@RequestMapping(value = "/autoSend/requestToReadyOverSeaNotIn")
	public ResultVo<List<OpsPurchaseStatusToWMDto>>  requestToReadyOverSeaNotIn(@RequestParam("dic") String dic);

	// 将ready状态的请购单进行后续采购
	@RequestMapping(value = "/autoSend/readyToPurchaseJP")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseJP();

	@RequestMapping(value = "/autoSend/readyToPurchaseGZ")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseGZ();

	@RequestMapping(value = "/autoSend/readyToPurchaseManufacture")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseManufacture();

	@RequestMapping(value = "/autoSend/readyToPurchaseOverSea")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseOverSea();

	@RequestMapping(value = "/autoSend/readyToPurchaseOverSeaIn")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseOverSeaIn(@RequestParam("dic") String dic);

	@RequestMapping(value = "/autoSend/readyToPurchaseOverSeaNotIn")
	public ResultVo<List<OpsPurchaseStatusToWMDto>>  readyToPurchaseOverSeaNotIn(@RequestParam("dic") String dic);

	// 自动发单
	@RequestMapping(value = "/autoSend/purchaseAutoJP")
	public ResultVo<Integer> purchaseAutoJP();

	@RequestMapping(value = "/autoSend/purchaseAutoGZ")
	public ResultVo<Integer> purchaseAutoGZ();

	@RequestMapping(value = "/autoSend/purchaseAutoManufacture")
	public ResultVo<Integer> purchaseAutoManufacture();

	@RequestMapping(value = "/autoSend/purchaseAutoOverSea")
	public ResultVo<Integer> purchaseAutoOverSea();

	@RequestMapping(value = "/autoSend/purchaseAutoOverSeaIn")
	public ResultVo<Integer> purchaseAutoOverSeaIn(@RequestParam("dic") String dic);

	@RequestMapping(value = "/autoSend/purchaseAutoOverSeaNotIn")
	public ResultVo<Integer> purchaseAutoOverSeaNotIn(@RequestParam("dic") String dic);
}
