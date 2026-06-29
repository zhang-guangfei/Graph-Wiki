package com.sales.ops.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sales.ops.db.entity.Product;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductBomDetail;
import com.sales.ops.db.entity.ProductEos;
import com.sales.ops.db.entity.ProductError;
import com.sales.ops.db.entity.ProductPhysics;
import com.sales.ops.db.entity.ProductPrice;
import com.sales.ops.dto.util.CommonResult;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：产品基本数据缓存接口信息
 * @date ：Created in 2021/10/8 14:18
 */
@FeignClient(name = "ba-service", contextId = "product")
public interface OPSProductFeignApi {

	/**
	 * wms 获取上传bom 查询接口
	 */
	@RequestMapping(value = "/wms/product/getBomByLimit", method = RequestMethod.GET)
	CommonResult<List<ProductBom>> getBomByLimitApi(@RequestParam("limit") String limit);

	/**
	 * 根据modelno 获取产品Physics（数据库或缓存）
	 */
	@RequestMapping(value = "/ops/product/findProductPhysicsByModelNo", method = RequestMethod.GET)
	CommonResult<ProductPhysics> findProductPhysicsByModelNo(@RequestParam("modelNo") String modelNo);

	/**
	 * job任务 定时刷新缓存 删除redis mi 分钟的数据
	 * 
	 * @param mi
	 *            最近分钟
	 */
	@RequestMapping(value = "/ops/product/productPhysics/refresh", method = RequestMethod.GET)
	CommonResult refreshProducPhySicstData(@RequestParam("mi") String mi);

	/**
	 * 根据modelno 获取产品（数据库或缓存）
	 */
	@RequestMapping(value = "/ops/product/product", method = RequestMethod.POST)
	CommonResult<Product> searchProduct(@RequestParam("modelNo") String modelNo);

	/**
	 * job任务 定时刷新缓存 删除redis mi 分钟的数据
	 * 
	 * @param mi
	 *            最近分钟
	 */
	@RequestMapping(value = "/ops/product/product/refresh", method = RequestMethod.GET)
	CommonResult refreshProductData(@RequestParam("mi") String mi);

	/**
	 * 根据modelno 获取bom（数据库或缓存）
	 */
	@RequestMapping(value = "/ops/product/bom", method = RequestMethod.POST)
	CommonResult<List<ProductBom>> searchProductBom(@RequestParam("modelNo") String modelNo);

	/**
	 * 根据modelno 获取bom（数据库或缓存）
	 */
	@RequestMapping(value = "/ops/product/bomPriorityOne", method = RequestMethod.POST)
	CommonResult<List<Long>> queryProductBomByModelNoAndPriorityLevel(@RequestParam("modelNo") String modelNo);

	/**
	 * job任务 定时刷新缓存 删除redis mi分钟的数据
	 * 
	 * @param mi
	 *            最近分钟
	 */
	@RequestMapping(value = "/ops/product/bom/refresh", method = RequestMethod.GET)
	CommonResult refreshProductBomData(@RequestParam("mi") String mi);

	/**
	 * 根据bomId 获取bomDetail（数据库或缓存）
	 */
	@RequestMapping(value = "/ops/product/bom/detail", method = RequestMethod.POST)
	CommonResult<List<ProductBomDetail>> searchProductBomDetail(@RequestParam("bomId") String bomId);

	/**
	 * job任务 定时刷新缓存 删除redis mi分钟的数据
	 * 
	 * @param mi
	 *            最近小时
	 */
	@RequestMapping(value = "/ops/product/bom/detail/refresh", method = RequestMethod.GET)
	CommonResult refreshProductBomDetailData(@RequestParam("mi") String mi);

	/**
	 * 获取多段价格
	 */
	@RequestMapping(value = "/ops/product/findProductPriceByModelNo", method = RequestMethod.POST)
	CommonResult<List<ProductPrice>> findProductPriceByModelNo(@RequestParam("modelNo") String modelNo);

	/**
	 * job任务 定时刷新缓存 删除redis mi分钟的数据 多段价格
	 * 
	 * @param mi
	 *            最近分钟
	 */
	@RequestMapping(value = "/ops/product/findProductPriceByModelNo/refresh", method = RequestMethod.GET)
	CommonResult refreshFindProductPriceByModelNo(@RequestParam("mi") String mi);

	/**
	 * 获取错误型号
	 */
	@RequestMapping(value = "/ops/product/findProductErrorByModelNo", method = RequestMethod.POST)
	CommonResult<ProductError> findProductErrorByModelNo(@RequestParam("modelNo") String modelNo);

	/**
	 * job任务 定时刷新缓存 删除redis mi分钟的数据 错误型号
	 * 
	 * @param mi
	 *            最近分钟
	 */
	@RequestMapping(value = "/ops/product/findProductErrorByModelNo/refresh", method = RequestMethod.GET)
	CommonResult refreshFindProductErrorByModelNo(@RequestParam("mi") String mi);

	/**
	 * 获取收敛品
	 */
	@RequestMapping(value = "/ops/product/findProductEosByModelNo", method = RequestMethod.POST)
	CommonResult<ProductEos> findProductEosByModelNo(@RequestParam("modelNo") String modelNo);

	/**
	 * job任务 定时刷新缓存 删除redis mi分钟的数据 收敛品
	 * 
	 * @param mi
	 *            最近分钟
	 */
	@RequestMapping(value = "/ops/product/findProductEosByModelNo/refresh", method = RequestMethod.GET)
	CommonResult refreshFindProductEosByModelNo(@RequestParam("mi") String mi);

	/**
	 * 获取贩卖限制
	 */
	@RequestMapping(value = "/ops/product/findProductRestrictModelNoByModelNo", method = RequestMethod.POST)
	CommonResult<String> findProductRestrictModelNoByModelNo(@RequestParam("modelNo") String modelNo);

	/**
	 * job任务 定时刷新缓存 删除redis mi分钟的数据 贩卖限制
	 * 
	 * @param mi
	 *            最近分钟
	 */
	@RequestMapping(value = "/ops/product/findProductRestrictModelNoByModelNo/refresh", method = RequestMethod.GET)
	CommonResult refreshFindProductRestrictModelNoByModelNo(@RequestParam("mi") String mi);

	/**
	 * 获取白名单
	 */
	@RequestMapping(value = "/ops/product/findProductRestrictCustomerWhiteListByCustomerNo", method = RequestMethod.POST)
	CommonResult<List<String>> findProductRestrictCustomerWhiteListByCustomerNo(
			@RequestParam("customerNo") String customerNo);

	/**
	 * job任务 定时刷新缓存 删除redis mi分钟的数据 白名单
	 * 
	 * @param mi
	 *            最近分钟
	 */
	@RequestMapping(value = "/ops/product/findProductRestrictCustomerWhiteListByCustomerNo/refresh", method = RequestMethod.GET)
	CommonResult refreshFindProductRestrictCustomerWhiteListByCustomerNo(@RequestParam("mi") String mi);

}
