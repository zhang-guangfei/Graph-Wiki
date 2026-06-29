package com.sales.ops.service.product;

import java.util.List;
import java.util.Map;

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
import com.sales.ops.dto.inventory.InventorySupplierDto;
import com.sales.ops.dto.product.ProductBeanDTO;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：产品相关缓存业务
 * @date ：Created in 2021/10/7 11:04
 */
public interface OPSProductService {

	List<Product> findByModelNoLike(String modelNo);

	/**
	 * findProductDetailByModelNo:(通过型号集合查询产品详细列表).
	 * 
	 * @author C02038
	 * @param modelNoList
	 * @return
	 */
	public List<ProductBeanDTO> findProductDetailByModelNo(List<String> modelNoList);

	// 通过型号查询产品来源
	ProductSourceType findSourceByModelNo(String modelNo);

	Product selectProductByModelNo(String modelNo);

	/**
	 * 根据表时间更新刷新缓存
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshProductData(String mi);

	List<ProductBom> selectProductBomByModelNo(String modelNo);

	List<Long> queryProductBomByModelNoAndPriorityLevel(String modelNo);

	// 通过型号查询交付信息
	List<ProductDelivery> findProductDeliveryByModelNo(String modelNo);

	// 通过CompetitivenessID查询竞争系数
	String findProductCompetitivenessType(String id);

	// 通过型号查询产品物理信息
	ProductPhysics findProductPhysicsByModelNo(String modelNo);

	/**
	 * 根据表时间更新刷新缓存
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshProducPhySicstData(String mi);

	/**
	 * 根据表时间更新刷新缓存
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshProductBomData(String mi);

	List<Map<String, Object>> selectProductBomDetailByModelNos(String modelno);

	List<ProductBomDetail> selectProductBomDetailByModelNo(String bomId);

	/**
	 * 根据表时间更新刷新缓存
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshProductBomDetailData(String mi);

	// 获取多段价格
	List<ProductPrice> findProductPriceByModelNo(String modelNo);

	/**
	 * 根据表时间更新刷新缓存 获取多段价格
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshFindProductPriceByModelNo(String mi);

	// 获取错误型号
	ProductError findProductErrorByModelNo(String modelNo);

	/**
	 * 根据表时间更新刷新缓存 获取错误型号
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshFindProductErrorByModelNo(String mi);

	// 获取收敛品
	ProductEos findProductEosByModelNo(String modelNo);

	/**
	 * 根据表时间更新刷新缓存 获取收敛品
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshFindProductEosByModelNo(String mi);

	// 获取贩卖限制
	String findProductRestrictModelNoByModelNo(String modelNo);

	ProductRestrict findRestrictInfoCustomerNoByModelNo(String modelNo);

	/**
	 * 根据表时间更新刷新缓存 获取贩卖限制
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshFindProductRestrictModelNoByModelNo(String mi);

	// 获取白名单
	List<String> findProductRestrictCustomerWhiteListByCustomerNo(String customerNo);

	// 获取白名单，部门等信息
	// bug7782 B91717 获取白名单客户部门等信息
	List<Map<String, Object>> findRestrictCustomerWhiteList(String modelNo);

	/**
	 * 根据表时间更新刷新缓存 白名单
	 * 
	 * @param mi
	 *            分钟
	 * @return
	 */
	List<String> refreshFindProductRestrictCustomerWhiteListByCustomerNo(String mi);

	List<Map<String, Object>> findInventoryByModelNo(String modelNo);

	List<Map<String, Object>> findSuppliercompany();


	// 获取供应商对应库存
	List<InventorySupplierDto> getInventorySupplier(String modelNo);

}
