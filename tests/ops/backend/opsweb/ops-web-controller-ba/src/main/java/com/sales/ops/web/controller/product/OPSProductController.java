package com.sales.ops.web.controller.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sales.ops.dto.inventory.InventorySupplierDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.entity.InventorySupplier;
import com.sales.ops.db.entity.Product;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductBomDetail;
import com.sales.ops.db.entity.ProductDelivery;
import com.sales.ops.db.entity.ProductEos;
import com.sales.ops.db.entity.ProductError;
import com.sales.ops.db.entity.ProductPhysics;
import com.sales.ops.db.entity.ProductPrice;
import com.sales.ops.db.entity.ProductRestrict;
import com.sales.ops.db.entity.ProductSourceType;
import com.sales.ops.dto.product.ProductBeanDTO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.service.product.OPSProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops产品 相关查询缓存操作
 * @date ：Created in 2021/10/7 13:24
 */
@CrossOrigin
@RestController
@RequestMapping("/ops/product")
@Slf4j
public class OPSProductController {

	@Autowired
	private OPSProductService opsProductService;
	@Autowired
	private OpsCallWmsFeignApi opsCallWmsFeignApi;
	@Autowired
	private OPSRedisUtils opsRedisUtils;

	/**
	 * 通过型号模糊查询产品信息列表前十条 前端查询页面用
	 * 
	 * @param modelNo
	 * @return
	 */
	@RequestMapping("/findByModelNoLike")
	public CommonResult<List<Product>> findByModelNoLike(String modelNo) {
		try {
			List<Product> list = opsProductService.findByModelNoLike(modelNo);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	@RequestMapping("/findProductDetailByModelNo")
	public CommonResult<List<ProductBeanDTO>> findProductDetailByModelNo(@RequestBody List<String> modelNoList) {
		try {
			List<ProductBeanDTO> list = opsProductService.findProductDetailByModelNo(modelNoList);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * 通过型号查询产品来源
	 * 
	 * @param modelNo
	 * @return
	 */
	@GetMapping("/findSourceByModelNo")
	public CommonResult<ProductSourceType> findSourceByModelNo(String modelNo) {
		try {
			ProductSourceType result = opsProductService.findSourceByModelNo(modelNo);
			return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 *
	 * findByModelNo:(通过型号查询交付信息). <br/>
	 *
	 * @author B74718
	 * @param modelNo
	 * @return
	 */
	@GetMapping("/findProductDeliveryByModelNo")
	public CommonResult<List<ProductDelivery>> findProductDeliveryByModelNo(String modelNo) {
		try {
			List<ProductDelivery> result = opsProductService.findProductDeliveryByModelNo(modelNo);
			return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	// 通过CompetitivenessID查询竞争系数
	@GetMapping("/findProductCompetitivenessType")
	public CommonResult<String> findProductCompetitivenessTypeName(String id) {
		try {
			String result = opsProductService.findProductCompetitivenessType(id);
			return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 *
	 * findByModelNo:(通过型号查询产品物理信息). <br/>
	 *
	 * @param modelNo
	 * @return
	 */
	@GetMapping("/findProductPhysicsByModelNo")
	public CommonResult<ProductPhysics> findProductPhysicsByModelNo(String modelNo) {
		try {
			ProductPhysics result = opsProductService.findProductPhysicsByModelNo(modelNo);
			return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	// /**
	// *
	// * findByModelNo:(通过型号查询产品供应商信息). <br/>
	// *
	// * @param modelNo
	// * @return
	// */
	// @GetMapping("/findProductDeliveryByModelNo")
	// public CommonResult<ProductDelivery> findProductDeliveryByModelNo(String
	// modelNo) {
	// try {
	// ProductDelivery result =
	// opsProductService.findProductDeliveryByModelNo(modelNo);
	// return Objects.isNull(result) ? CommonResult.failure("无记录") :
	// CommonResult.success(result);
	// } catch (Exception e) {
	// return CommonResult.failure(e.getMessage());
	// }
	// }

	/**
	 * 刷新 productPhysics
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@GetMapping("/productPhysics/refresh")
	public CommonResult refreshProducPhySicstData(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshProducPhySicstData(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 查产品
	 *
	 * @param modelNo
	 * @return testTime 2021/10/19 09:24 C14717
	 */
	@RequestMapping("/product")
	public CommonResult<Product> searchProduct(@RequestParam String modelNo) {

		try {
			Product result = opsProductService.selectProductByModelNo(modelNo);
			return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 刷新productredis
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@RequestMapping("/product/refresh")
	public CommonResult refreshProductData(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshProductData(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 查bom
	 *
	 * @param modelNo
	 * @return testTime 2021/10/19 09:26 C14717
	 */
	@RequestMapping("/bom")
	public CommonResult<List<ProductBom>> searchProductBom(@RequestParam String modelNo) {
		try {
			List<ProductBom> list = opsProductService.selectProductBomByModelNo(modelNo);
			// 调取3s
			/*
			 * if (CollectionUtils.isEmpty(list)) { //今天如果已经调用过3s 就不要调用了 Object
			 * obj =
			 * opsRedisUtils.get("ops:productBom:check3sApi:modelNo:"+modelNo);
			 * if(Objects.nonNull(obj) && (Boolean) obj){ //不掉接口 }else{ // 更新数据
			 * boolean flag = opsProductService.updateBomAndBomDetail(modelNo);
			 * if (flag) { // 返回更新3s数据的新结果 list =
			 * opsProductService.selectProductBomByModelNo(modelNo);
			 * }else{//如果返回false 一天不用查此型号 opsRedisUtils.setKeyAndTimeout(
			 * "ops:productBom:check3sApi:modelNo:"+modelNo,true,1); } } }
			 */
			return CollectionUtils.isEmpty(list) ? CommonResult.success(new ArrayList<ProductBom>())
					: CommonResult.success(list);
		} catch (Exception e) {
			log.error("{}", e);
			return CommonResult.failure(e.getMessage());
		}
	}

	@RequestMapping("/bomPriorityOne")
	public CommonResult<List<Long>> queryProductBomByModelNoAndPriorityLevel(@RequestParam String modelNo) {
		try {
			List<Long> list = opsProductService.queryProductBomByModelNoAndPriorityLevel(modelNo);
			return CollectionUtils.isEmpty(list) ? CommonResult.success(new ArrayList<Long>())
					: CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 刷新productBom redis
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@RequestMapping("/bom/refresh")
	public CommonResult refreshProductBomData(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshProductBomData(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 查bomDetail
	 *
	 */
	@RequestMapping("/bom/detailbymodel")
	public CommonResult<List<Map<String, Object>>> searchProductBomDetailByModel(@RequestParam String modelNo) {
		try {
			List<Map<String, Object>> list = opsProductService.selectProductBomDetailByModelNos(modelNo);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * 查bomDetail
	 *
	 * @param bomId
	 * @return testTime 2021/10/19 09:29 C14717
	 */
	@RequestMapping("/bom/detail")
	public CommonResult<List<ProductBomDetail>> searchProductBomDetail(@RequestParam String bomId) {
		try {
			List<ProductBomDetail> list = opsProductService.selectProductBomDetailByModelNo(bomId);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * 刷新productBom redis
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@RequestMapping("/bom/detail/refresh")
	public CommonResult refreshProductBomDetailData(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshProductBomDetailData(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取多段价格
	 *
	 * @return
	 */
	@RequestMapping("/findProductPriceByModelNo")
	public CommonResult<List<ProductPrice>> findProductPriceByModelNo(@RequestParam String modelNo) {
		try {
			List<ProductPrice> list = opsProductService.findProductPriceByModelNo(modelNo);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 刷新获取多段价格
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@RequestMapping("/findProductPriceByModelNo/refresh")
	public CommonResult refreshFindProductPriceByModelNo(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshFindProductPriceByModelNo(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取错误型号
	 *
	 * @return
	 */
	@RequestMapping("/findProductErrorByModelNo")
	public CommonResult<ProductError> findProductErrorByModelNo(@RequestParam String modelNo) {
		try {
			ProductError result = opsProductService.findProductErrorByModelNo(modelNo);
			return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 刷新错误型号
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@RequestMapping("/findProductErrorByModelNo/refresh")
	public CommonResult refreshFindProductErrorByModelNo(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshFindProductErrorByModelNo(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取收敛品
	 *
	 * @return
	 */
	@RequestMapping("/findProductEosByModelNo")
	public CommonResult<ProductEos> findProductEosByModelNo(@RequestParam String modelNo) {
		try {
			ProductEos result = opsProductService.findProductEosByModelNo(modelNo);
			return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 刷新收敛品
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@RequestMapping("/findProductEosByModelNo/refresh")
	public CommonResult refreshFindProductEosByModelNo(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshFindProductEosByModelNo(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取贩卖限制
	 *
	 * @return
	 */
	@RequestMapping("/findProductRestrictModelNoByModelNo")
	public CommonResult<String> findProductRestrictModelNoByModelNo(@RequestParam String modelNo) {
		try {
			String result = opsProductService.findProductRestrictModelNoByModelNo(modelNo);
			return StringUtils.isBlank(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取贩卖限制，查询客户，以及贩卖限制品白名单信息
	 */
	@RequestMapping("/findRestrictInfoCustomerNoByModelNo")
	public CommonResult<ProductRestrict> findRestrictInfoCustomerNoByModelNo(@RequestParam String modelNo) {
		try {
			ProductRestrict result = opsProductService.findRestrictInfoCustomerNoByModelNo(modelNo);
			return result == null ? CommonResult.failure("无记录") : CommonResult.success(result);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 刷新贩卖限制
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@RequestMapping("/findProductRestrictModelNoByModelNo/refresh")
	public CommonResult refreshFindProductRestrictModelNoByModelNo(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshFindProductRestrictModelNoByModelNo(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取白名单
	 *
	 * @return
	 */
	@RequestMapping("/findProductRestrictCustomerWhiteListByCustomerNo")
	public CommonResult<List<String>> findProductRestrictCustomerWhiteListByCustomerNo(
			@RequestParam String customerNo) {
		try {
			List<String> list = opsProductService.findProductRestrictCustomerWhiteListByCustomerNo(customerNo);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取贩卖限制品报名单信息 bug7782 B91717 获取白名单客户部门等信息
	 *
	 * @return
	 */
	@RequestMapping("/findRestrictCustomerWhiteList")
	public CommonResult<List<Map<String, Object>>> findRestrictCustomerWhiteList(@RequestParam String modelNo) {
		try {
			List<Map<String, Object>> list = opsProductService.findRestrictCustomerWhiteList(modelNo);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 刷新白名单
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@RequestMapping("/findProductRestrictCustomerWhiteListByCustomerNo/refresh")
	public CommonResult refreshFindProductRestrictCustomerWhiteListByCustomerNo(@RequestParam String mi) {
		try {
			List<String> list = opsProductService.refreshFindProductRestrictCustomerWhiteListByCustomerNo(mi);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取白名单
	 *
	 * @return
	 */
	@RequestMapping("/findInventoryByModel")
	public CommonResult<List<Map<String, Object>>> findInventoryByModel(@RequestParam String modelNo) {
		try {
			List<Map<String, Object>> list = opsProductService.findInventoryByModelNo(modelNo);
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取公司名称代码
	 */
	@RequestMapping(value = "/getSuppliercompany")
	@ResponseBody
	public CommonResult findSuppily() {
		List<Map<String, Object>> list = opsProductService.findSuppliercompany();
		CommonResult commonResult = list.size() == 0 ? CommonResult.success("没有记录") : CommonResult.success(list);
		return commonResult;
	}

	// 获取新型号的供应商
	@RequestMapping("/getNewModelSupplier")
	public CommonResult<List<ProductDelivery>> getNewModelSupplier(@RequestParam String modelNo) {
		// try {
		// List<ProductDelivery> list =
		// opsProductService.getNewModelSupplier(modelNo);
		// return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") :
		// CommonResult.success(list);
		// } catch (Exception e) {
		// return CommonResult.failure(e.getMessage());
		// }
		return CommonResult.failure();
	}

	@RequestMapping("/newModelOperate")
	public CommonResult<Integer> newModelOperate() {
		// try {
		// Integer list = opsProductService.queryPriceNewModelOperate();
		// return CommonResult.success(list);
		// } catch (Exception e) {
		// return CommonResult.failure(e.getMessage());
		// }
		return CommonResult.failure();
	}

	@RequestMapping("/updateSupplierBatch")
	public CommonResult<Integer> updateSupplierBatch() {
		// try {
		// Integer list = opsProductService.updateSupplierBatch();
		// return CommonResult.success(list);
		// } catch (Exception e) {
		// return CommonResult.failure(e.getMessage());
		// }
		return CommonResult.failure();
	}

	/**
	 * 获取供应商对应库存
	 */
	@RequestMapping(value = "/findInventorySupplier")
	public CommonResult findInventorySupplier(@RequestParam String modelNo) {
		List<InventorySupplierDto> list = opsProductService.getInventorySupplier(modelNo);
		CommonResult commonResult = list.size() == 0 ? CommonResult.success("没有记录") : CommonResult.success(list);
		return commonResult;
	}
}
