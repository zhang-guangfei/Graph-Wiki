package com.sales.ops.web.controller;

import java.util.List;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.PurchaseAutoService;

@CrossOrigin
@RestController
@RequestMapping("/autoSend")
public class AutoSendController {

	private static Logger log = LoggerFactory.getLogger(AutoSendController.class);

	@Autowired
	private PurchaseAutoService purchaseAutoService;

	@RequestMapping(value = "/requestToReadyJP")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyJP() {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.requestToReadyJP();
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("requestToReadyJP--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/requestToReadyGZ")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyGZ() {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.requestToReadyGZ();
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("requestToReadyGZ--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/requestToReadyManufacture")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyManufacture() {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.requestToReadyManufacture();
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("requestToReadyManufacture--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/requestToReadyOverSea")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyOverSea() {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.requestToReadyOverSea();
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("requestToReadyOverSea--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	// bug12330增加早九点海外发单供应商限制
	@RequestMapping(value = "/requestToReadyOverSeaIn")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyOverSeaIn(@RequestParam("dic") String dic) {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.requestToReadyOverSeaIn(dic);
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("requestToReadyOverSeaIn--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	// bug13940 使用字典参数控制发单的供应商
	@RequestMapping(value = "/requestToReadyOverSeaNotIn")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> requestToReadyOverSeaNotIn(@RequestParam("dic") String dic) {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.requestToReadyOverSeaNotIn(dic);
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("requestToReadyOverSeaNotIn--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	// 将ready状态的请购单进行后续采购
	@RequestMapping(value = "/readyToPurchaseJP")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseJP() {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.readyToPurchaseJP();
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("readyToPurchaseJP--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/readyToPurchaseGZ")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseGZ() {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.readyToPurchaseGZ();
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("readyToPurchaseGZ--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/readyToPurchaseManufacture")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseManufacture() {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.readyToPurchaseManufacture();
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("readyToPurchaseManufacture--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/readyToPurchaseOverSea")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseOverSea() {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.readyToPurchaseOverSea();
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("readyToPurchaseOverSea--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/readyToPurchaseOverSeaIn")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseOverSeaIn(@RequestParam("dic") String dic) {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.readyToPurchaseOverSeaIn(dic);
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("readyToPurchaseOverSeaIn--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/readyToPurchaseOverSeaNotIn")
	public ResultVo<List<OpsPurchaseStatusToWMDto>> readyToPurchaseOverSeaNotIn(@RequestParam("dic") String dic) {
		try {
			List<OpsPurchaseStatusToWMDto> opsPurchaseStatusToWMDtos = purchaseAutoService.readyToPurchaseOverSeaNotIn(dic);
			if(CollectionUtils.isNotEmpty(opsPurchaseStatusToWMDtos)){
				return ResultVo.success(opsPurchaseStatusToWMDtos);
			}else {
				return ResultVo.failure("无数据");
			}
		} catch (Exception e) {
			log.error("readyToPurchaseOverSeaNotIn--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	// 自动发单
	@RequestMapping(value = "/purchaseAutoJP")
	public ResultVo<Integer> purchaseAutoJP() {
		try {
			Integer num = purchaseAutoService.purchaseAutoJP(true);
			return ResultVo.success(num);
		} catch (Exception e) {
			log.error("purchaseAutoJP--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/purchaseAutoGZ")
	public ResultVo<Integer> purchaseAutoGZ() {
		try {
			Integer num = purchaseAutoService.purchaseAutoGZ(true);
			return ResultVo.success(num);
		} catch (Exception e) {
			log.error("purchaseAutoGZ--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/purchaseAutoManufacture")
	public ResultVo<Integer> purchaseAutoManufacture() {
		try {
			Integer num = purchaseAutoService.purchaseAutoManufacture(true);
			return ResultVo.success(num);
		} catch (Exception e) {
			log.error("purchaseAutoManufacture--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/purchaseAutoOverSea")
	public ResultVo<Integer> purchaseAutoOverSea() {
		try {
			Integer num = purchaseAutoService.purchaseAutoOverSea(true);
			return ResultVo.success(num);
		} catch (Exception e) {
			log.error("purchaseAutoOverSea--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/purchaseAutoOverSeaIn")
	public ResultVo<Integer> purchaseAutoOverSeaIn(@RequestParam("dic") String dic) {
		try {
			Integer num = purchaseAutoService.purchaseAutoOverSeaIn(true, dic);
			return ResultVo.success(num);
		} catch (Exception e) {
			log.error("purchaseAutoOverSeaMorning--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/purchaseAutoOverSeaNotIn")
	public ResultVo<Integer> purchaseAutoOverSeaNotIn(@RequestParam("dic") String dic) {
		try {
			Integer num = purchaseAutoService.purchaseAutoOverSeaNotIn(true, dic);
			return ResultVo.success(num);
		} catch (Exception e) {
			log.error("purchaseAutoOverSeaMorning--", e);
			return ResultVo.failure(e.getMessage());
		}
	}

}
