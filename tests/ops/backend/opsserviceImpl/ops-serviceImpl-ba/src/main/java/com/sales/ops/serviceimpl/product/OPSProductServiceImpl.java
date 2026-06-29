package com.sales.ops.serviceimpl.product;

import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.InventoryRiskDao;
import com.sales.ops.db.extdao.OPSProductDao;
import com.sales.ops.dto.inventory.InventorySupplierDto;
import com.sales.ops.dto.product.ProductBeanDTO;
import com.sales.ops.dto.product.ProductMdmDTO;
import com.sales.ops.enums.ProductValidateEnum;
import com.sales.ops.service.product.OPSProductService;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：产品相关缓存业务
 * @date ：Created in 2021/10/7 11:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OPSProductServiceImpl implements OPSProductService {

	// private static Logger log =
	// LoggerFactory.getLogger(OPSProductServiceImpl.class);

	@Autowired
	private OPSProductDao productMapper;

    @Autowired
    private InventoryRiskDao inventoryRiskDao;

	@Autowired
	private ProductPriceMapper autoProductMapper;

	@Autowired
	private OPSRedisUtils opsRedisUtils;

	@Autowired
	private ProductDeliveryMapper productDeliveryMapper;

	@Autowired
	private ProductCompetitivenessTypeMapper productCompetitivenessTypeMapper;

	@Autowired
	private ProductPhysicsMapper productPhysicsMapper;

	@Autowired
	private InventorySupplierMapper inventorySupplierMapper;

	/**
	 * 通过型号模糊查询产品信息列表前十条
	 *
	 * @param modelNo
	 * @return
	 */
	@Override
	public List<Product> findByModelNoLike(String modelNo) {
		return productMapper.queryProductByModelNoTop10(modelNo);// todo 加缓存查询
	}

	/**
	 * 通过型号集合查询产品详细信息（包含价格、验证状态）
	 *
	 * @param modelNoList
	 * @return
	 */
	@Override
	public List<ProductBeanDTO> findProductDetailByModelNo(List<String> modelNoList) {

		if (CollectionUtils.isEmpty(modelNoList)) {
			return null;
		}

		/**
		 * 结果集
		 */
		List<ProductBeanDTO> results = new ArrayList<ProductBeanDTO>();

		/**
		 * 产品信息
		 */
		ProductMdmDTO product = productMapper.queryProductMdmDTOByModelNo(modelNoList.get(0));

		/**
		 * 产品多段价格
		 */
		List<ProductPrice> productPrices = this.findProductPriceByModelNo(modelNoList.get(0));

		/**
		 * 错误型号
		 */
		ProductError productError = this.findProductErrorByModelNo(modelNoList.get(0));

		/**
		 * 收敛品
		 */
		ProductEos productEos = this.findProductEosByModelNo(modelNoList.get(0));

		String restrict = this.findProductRestrictModelNoByModelNo(modelNoList.get(0));

		for (String modelNo : modelNoList) {
			ProductBeanDTO productBean = new ProductBeanDTO();
			productBean.setModelNo(modelNo);

			/**
			 * 判断是否是错误型号
			 */
			if (productError != null) {
				productBean.setProductValidateResultList(ProductValidateEnum.ERROR);
				results.add(productBean);
				continue;
			}

			if (product != null) {
				productBean.setProduct(product);
				productBean.setProductPriceList(productPrices);
				// 是否收敛品
				if (productEos != null) {
					productBean.addProductValidateResult(ProductValidateEnum.EOS);
				}

				// 是否贩卖限制品
				if (StringUtils.isNotBlank(restrict)) {
					productBean.addProductValidateResult(ProductValidateEnum.RESTRICT);
				}

				if (CollectionUtils.isEmpty(productBean.getProductValidateResultList())) {
					productBean.setProductValidateResultList(ProductValidateEnum.NORMAL);
				}
				results.add(productBean);
				continue;
			}

			/**
			 * 上线后去掉
			 */
			productBean.setProductValidateResultList(ProductValidateEnum.UNKNOWN);
			/**
			 * 3S系统中由此型号
			 */
			results.add(productBean);

		}

		return results;
	}

	/**
	 * findByModelNo:(通过型号查询交付信息). <br/>
	 *
	 * @param modelNo
	 * @return
	 */
	@Override
	public List<ProductDelivery> findProductDeliveryByModelNo(String modelNo) {
		ProductDeliveryExample productDeliveryExample = new ProductDeliveryExample();
		ProductDeliveryExample.Criteria criteria = productDeliveryExample.createCriteria();
		// criteria.andIsprimaryEqualTo(true);//主供应商；
		criteria.andModelnoEqualTo(modelNo).andIsDeletedEqualTo(false);
		List<ProductDelivery> list = productDeliveryMapper.selectByExample(productDeliveryExample);
		if (CollectionUtils.isNotEmpty(list)) {
			return list;
		}
		return null;
	}

	// 通过CompetitivenessID查询竞争系数
	@Override
	public String findProductCompetitivenessType(String id) {
		return productCompetitivenessTypeMapper.selectByPrimaryKey(id).getName();
	}

	/**
	 * 通过型号查询产品来源
	 *
	 * @param modelNo
	 * @return
	 */
	@Override
	public ProductSourceType findSourceByModelNo(String modelNo) {
		return productMapper.findSourceByModelNo(modelNo);
	}

	// 通过型号查询产品物理信息
	@Override
	public ProductPhysics findProductPhysicsByModelNo(String modelNo) {
		ProductPhysics productPhysics = productPhysicsMapper.selectByPrimaryKey(modelNo);
		/*
		 * //引入redis //根据modelNo到redis内获取 if(StringUtils.isBlank(modelNo)){
		 * return null; } Object obj =
		 * opsRedisUtils.hGet("ops:productPhysics:modelNo",modelNo); if(obj
		 * !=null){ productPhysics =
		 * JSON.parseObject(obj.toString(),ProductPhysics.class); }
		 * //productError,则访问下游service if(productPhysics == null){
		 * productPhysics = productPhysicsMapper.selectByPrimaryKey(modelNo);
		 * if(productPhysics != null){ //productError
		 * opsRedisUtils.hPut("ops:productPhysics:modelNo",modelNo,JSON.
		 * toJSONString( productPhysics)); } }
		 */
		return productPhysics;
	}

	@Override
	public List<String> refreshProducPhySicstData(String mi) {
		Long time = Long.parseLong(mi);
		List<String> modelNos = productMapper.refreshProductPhysicsData(time);
		for (String mdoelNo : modelNos) {
			opsRedisUtils.hDelete("ops:productPhysics:modelNo", mdoelNo);
		}
		if (CollectionUtils.isEmpty(modelNos)) {// 没有更新内容
			return null;
		}
		return modelNos;
	}

	/**
	 * 查产品
	 *
	 * @param modelNo
	 * @return
	 */
	@Override
	public Product selectProductByModelNo(String modelNo) {
		Product product = productMapper.queryProductByModelNo(modelNo);
		/*
		 * //引入redis //根据modelNo到redis内获取 if(StringUtils.isBlank(modelNo)){
		 * return null; } Object obj =
		 * opsRedisUtils.hGet("ops:product:modelNo",modelNo); if(obj !=null){
		 * product = JSON.parseObject(obj.toString(),Product.class); }
		 * //若redis内不存在对应的product,则访问下游service if(product == null){ product =
		 * productMapper.queryProductByModelNo(modelNo); if(product != null){
		 * //设置product到redis内
		 * opsRedisUtils.hPut("ops:product:modelNo",modelNo,JSON.toJSONString(
		 * product)); } }
		 */
		return product;
	}

	/**
	 * 根据表时间更新刷新缓存
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@Override
	public List<String> refreshProductData(String mi) {
		Long time = Long.parseLong(mi);
		// product
		List<String> productModelNos = productMapper.queryProductModelNoByCrreateDateUnionUpdateDate(time);
		for (String modelNo : productModelNos) {
			opsRedisUtils.hDelete("ops:product:modelNo", modelNo);
		}
		if (CollectionUtils.isEmpty(productModelNos)) {// 没有更新内容
			return null;
		}

		return productModelNos;
	}

	/**
	 * 查bom
	 *
	 * @param modelNo
	 * @return
	 */
	@Override
	public List<ProductBom> selectProductBomByModelNo(String modelNo) { // 组合方式
		List<ProductBom> list = new ArrayList<>();
		list = productMapper.queryProductBomByModelNo(modelNo);
		/*
		 * //引入redis //根据modelNo到redis内获取 if(StringUtils.isBlank(modelNo)){
		 * return null; } Object obj =
		 * opsRedisUtils.hGet("ops:productBom:modelNo",modelNo); if(obj !=null){
		 * JSONArray productBomJsonArray = JSONArray.parseArray(obj.toString());
		 * int productBomJsonArraySize = productBomJsonArray.size(); for(int
		 * i=0;i<productBomJsonArraySize;i++){
		 * list.add(JSON.parseObject(productBomJsonArray.getString(i),ProductBom
		 * .class)) ; } } //若redis内不存在对应的product,则访问下游service
		 * if(list.isEmpty()){ list =
		 * productMapper.queryProductBomByModelNo(modelNo);
		 * if(CollectionUtils.isNotEmpty(list)){ JSONArray productBoms=
		 * JSONArray.parseArray(JSON.toJSONString(list)); //设置product到redis内
		 * opsRedisUtils.hPut("ops:productBom:modelNo",modelNo,productBoms.
		 * toJSONString( )); } }
		 */
		return list;
	}

	@Override
	public List<Long> queryProductBomByModelNoAndPriorityLevel(String modelNo) {
		return productMapper.queryProductBomByModelNoAndPriorityLevel(modelNo);
	}

	/**
	 * 根据表时间更新刷新缓存
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@Override
	public List<String> refreshProductBomData(String mi) {
		Long time = Long.parseLong(mi);
		// productBom
		List<String> productBomModelNos = productMapper.queryProductBomModelNoByCrreateDateUnionUpdateDate(time);
		for (String modelNo : productBomModelNos) {
			opsRedisUtils.hDelete("ops:productBom:modelNo", modelNo);
		}
		if (CollectionUtils.isEmpty(productBomModelNos)) {// 没有更新内容
			return null;
		}
		return productBomModelNos;
	}

	@Override
	public List<Map<String, Object>> selectProductBomDetailByModelNos(String modelno) {
		List<Map<String, Object>> list = new ArrayList<>();
		list = productMapper.queryProductBomDetailsByModelNo(modelno);
		return list;
	}

	/**
	 * 查bom detail
	 *
	 * @param bomId
	 * @return
	 */
	@Override
	public List<ProductBomDetail> selectProductBomDetailByModelNo(String bomId) {// 组装方式清单
		List<ProductBomDetail> list = new ArrayList<>();
		list = productMapper.queryProductBomDetailByModelNo(Long.parseLong(bomId));
		/*
		 * //引入redis //根据modelNo到redis内获取 if(StringUtils.isBlank(bomId)){ return
		 * null; } Object obj =
		 * opsRedisUtils.hGet("ops:productBomDetail:bomId",bomId); if(obj
		 * !=null){ JSONArray productBomDetailJsonArray =
		 * JSONArray.parseArray(obj.toString()); int
		 * productBomDetailJsonArraySize = productBomDetailJsonArray.size();
		 * for(int i=0;i<productBomDetailJsonArraySize;i++){
		 * list.add(JSON.parseObject(productBomDetailJsonArray.getString(i),
		 * ProductBomDetail.class)); } } //若redis内不存在对应的product,则访问下游service
		 * if(list.isEmpty()){ list =
		 * productMapper.queryProductBomDetailByModelNo(Long.parseLong(bomId));
		 * if(CollectionUtils.isNotEmpty(list)){ //设置product到redis内 JSONArray
		 * productBomDetails= JSONArray.parseArray(JSON.toJSONString(list));
		 * opsRedisUtils.hPut("ops:productBomDetail:bomId",bomId,
		 * productBomDetails. toJSONString()); } }
		 */
		return list;
	}

	/**
	 * 根据表时间更新刷新缓存
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@Override
	public List<String> refreshProductBomDetailData(String mi) {
		Long time = Long.parseLong(mi);
		// productBomDetail
		List<String> productBomDetailBomIds = productMapper
				.queryProductBomDetailBomIdByCrreateDateUnionUpdateDate(time);
		for (String bomId : productBomDetailBomIds) {
			opsRedisUtils.hDelete("ops:productBomDetail:bomId", bomId);
		}
		if (CollectionUtils.isEmpty(productBomDetailBomIds)) {// 没有更新内容
			return null;
		}
		return productBomDetailBomIds;
	}

	/**
	 * 获取多段价格
	 *
	 * @param modelNo
	 * @return
	 */
	@Override
	public List<ProductPrice> findProductPriceByModelNo(String modelNo) {
		List<ProductPrice> list = new ArrayList<>();
		ProductPriceExample productPriceExample = new ProductPriceExample();
		ProductPriceExample.Criteria criteria = productPriceExample.createCriteria();
		// criteria.andIsprimaryEqualTo(true);//主供应商；
		// bug 11975 产品检索处产品价格查询把已删除的数据也显示了出来
		criteria.andModelnoEqualTo(modelNo).andIsDeletedEqualTo(false);
		list = autoProductMapper.selectByExample(productPriceExample);

        if(CollectionUtils.isNotEmpty(list)){
            // 查询通用库存
            Integer invQty = inventoryRiskDao.sumTYInv(modelNo);
            if(Objects.nonNull(invQty)){
                for(ProductPrice price : list){
                    price.setInvQty(invQty);
                }
            }
            //bugid:20641 C14717 20260422 查询风险在库
            Integer rInvQty = inventoryRiskDao.sumRiskInv(modelNo);
            if(Objects.nonNull(rInvQty)){
                for(ProductPrice price : list){
                    price.setrInvQty(rInvQty);
                }
            }
        }
		return list;
	}

	/**
	 * 根据表时间更新刷新缓存 多段价格
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@Override
	public List<String> refreshFindProductPriceByModelNo(String mi) {
		Long time = Long.parseLong(mi);
		List<String> modelNos = productMapper.refreshProductPriceData(time);
		for (String mdoelNo : modelNos) {
			opsRedisUtils.hDelete("ops:productPrice:modelNo", mdoelNo);
		}
		if (CollectionUtils.isEmpty(modelNos)) {// 没有更新内容
			return null;
		}
		return modelNos;
	}

	// 获取错误型号
	@Override
	public ProductError findProductErrorByModelNo(String modelNo) {
		ProductError productError = productMapper.findProductErrorByModelNo(modelNo);
		/*
		 * //引入redis //根据modelNo到redis内获取 if(StringUtils.isBlank(modelNo)){
		 * return null; } Object obj =
		 * opsRedisUtils.hGet("ops:productError:modelNo",modelNo); if(obj
		 * !=null){ productError =
		 * JSON.parseObject(obj.toString(),ProductError.class); }
		 * //productError,则访问下游service if(productError == null){ productError =
		 * productMapper.findProductErrorByModelNo(modelNo); if(productError !=
		 * null){ //productError
		 * opsRedisUtils.hPut("ops:productError:modelNo",modelNo,JSON.
		 * toJSONString( productError)); } }
		 */
		return productError;
	}

	/**
	 * 根据表时间更新刷新缓存 错误型号
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@Override
	public List<String> refreshFindProductErrorByModelNo(String mi) {
		Long time = Long.parseLong(mi);
		List<String> modelNos = productMapper.refreshProductErrorData(time);
		for (String mdoelNo : modelNos) {
			opsRedisUtils.hDelete("ops:productError:modelNo", mdoelNo);
		}
		if (CollectionUtils.isEmpty(modelNos)) {// 没有更新内容
			return null;
		}
		return modelNos;
	}

	// 获取收敛品
	@Override
	public ProductEos findProductEosByModelNo(String modelNo) {
		ProductEos productEos = productMapper.findProductEosByModelNo(modelNo);
		/*
		 * //引入redis //根据modelNo到redis内获取 if(StringUtils.isBlank(modelNo)){
		 * return null; } Object obj =
		 * opsRedisUtils.hGet("ops:productEos:modelNo",modelNo); if(obj !=null){
		 * productEos = JSON.parseObject(obj.toString(),ProductEos.class); }
		 * //productError,则访问下游service if(productEos == null){ productEos =
		 * productMapper.findProductEosByModelNo(modelNo); if(productEos !=
		 * null){ //productError
		 * opsRedisUtils.hPut("ops:productEos:modelNo",modelNo,JSON.
		 * toJSONString( productEos)); } }
		 */
		return productEos;
	}

	/**
	 * 根据表时间更新刷新缓存 收敛品
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@Override
	public List<String> refreshFindProductEosByModelNo(String mi) {
		Long time = Long.parseLong(mi);
		List<String> modelNos = productMapper.refreshProductEosData(time);
		for (String mdoelNo : modelNos) {
			opsRedisUtils.hDelete("ops:productEos:modelNo", mdoelNo);
		}
		if (CollectionUtils.isEmpty(modelNos)) {// 没有更新内容
			return null;
		}
		return modelNos;
	}

	// 获取贩卖限制
	@Override
	public String findProductRestrictModelNoByModelNo(String modelNo) {
		String productRestrict = productMapper.findProductRestrictModelNoByModelNo(modelNo);
		/*
		 * //引入redis //根据modelNo到redis内获取 if(StringUtils.isBlank(modelNo)){
		 * return null; } Object obj =
		 * opsRedisUtils.hGet("ops:productRestrict:modelNo",modelNo); if(obj
		 * !=null){ productRestrict = obj.toString(); }
		 * //productError,则访问下游service if(StringUtils.isBlank(productRestrict)
		 * ){ productRestrict =
		 * productMapper.findProductRestrictModelNoByModelNo(modelNo);
		 * if(productRestrict != null){ //productError
		 * opsRedisUtils.hPut("ops:productRestrict:modelNo",modelNo,
		 * productRestrict); } }
		 */
		return productRestrict;
	}

	@Override
	public ProductRestrict findRestrictInfoCustomerNoByModelNo(String modelNo) {
		ProductRestrict productRestrict = productMapper.findProductRestrictDetailByModelNo(modelNo);
		return productRestrict;
	}

	/**
	 * 根据表时间更新刷新缓存 获取贩卖限制
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@Override
	public List<String> refreshFindProductRestrictModelNoByModelNo(String mi) {
		Long time = Long.parseLong(mi);
		List<String> modelNos = productMapper.refreshProductRestrictData(time);
		for (String mdoelNo : modelNos) {
			opsRedisUtils.hDelete("ops:productRestrict:modelNo", mdoelNo);
		}
		if (CollectionUtils.isEmpty(modelNos)) {// 没有更新内容
			return null;
		}
		return modelNos;
	}

	/**
	 * 获取白名单
	 *
	 * @param customerNo
	 * @return
	 */
	@Override
	public List<String> findProductRestrictCustomerWhiteListByCustomerNo(String customerNo) {
		List<String> list = new ArrayList<>();
		list = productMapper.findProductRestrictCustomerWhiteListByCustomerNo(customerNo);
		/*
		 * if(StringUtils.isBlank(customerNo)){ return null; }
		 * //根据modelNo到redis内获取 Object obj =
		 * opsRedisUtils.hGet("ops:productRestrictCustomerWhiteList:customerNo",
		 * customerNo); if(null != obj){ JSONArray arr =
		 * JSONArray.parseArray(obj.toString()); int arrSize = arr.size();
		 * for(int i=0;i<arrSize;i++){ list.add(arr.getString(i)); } }
		 * //若redis内不存,则访问下游service if(list.isEmpty()){ list =
		 * productMapper.findProductRestrictCustomerWhiteListByCustomerNo(
		 * customerNo); if(CollectionUtils.isNotEmpty(list)){ JSONArray
		 * resultArr = JSONArray.parseArray(JSON.toJSONString(list));
		 * opsRedisUtils.hPut("ops:productRestrictCustomerWhiteList:customerNo",
		 * customerNo,resultArr.toString()); } }
		 */
		return list;
	}

	// bug7782 B91717 获取白名单客户部门等信息
	@Override
	public List<Map<String, Object>> findRestrictCustomerWhiteList(String modelNo) {
		List<Map<String, Object>> result = new ArrayList<>();
		result = productMapper.getRestrictCustomerWhiteList(modelNo);
		return result;
	}

	/**
	 * 根据表时间更新刷新缓存 白名单
	 *
	 * @param mi
	 *            分钟
	 * @return
	 */
	@Override
	public List<String> refreshFindProductRestrictCustomerWhiteListByCustomerNo(String mi) {
		Long time = Long.parseLong(mi);
		List<String> customerNos = productMapper.refreshProductRestrictCustomerWhiteListData(time);
		for (String customerNo : customerNos) {
			opsRedisUtils.hDelete("ops:productRestrictCustomerWhiteList:customerNo", customerNo);
		}
		if (CollectionUtils.isEmpty(customerNos)) {// 没有更新内容
			return null;
		}
		return customerNos;
	}

	@Override
	public List<Map<String, Object>> findInventoryByModelNo(String modelNo) {
		List<Map<String, Object>> result = new ArrayList<>();
		result = productMapper.getInventoryCount(modelNo);
		return result;
	}

	@Override
	public List<Map<String, Object>> findSuppliercompany() {
		List<Map<String, Object>> result = new ArrayList<>();
		result = productMapper.getSuppliercompany();
		return result;
	}

	/**
	 * 获取供应商库存
	 * 
	 * @param modelNo
	 * @return
	 */
	@Override
	public List<InventorySupplierDto> getInventorySupplier(String modelNo) {
		InventorySupplierExample inventorySupplierExample = new InventorySupplierExample();
		InventorySupplierExample.Criteria criteria = inventorySupplierExample.createCriteria();
		criteria.andModelnoEqualTo(modelNo);
		List<InventorySupplier> inventorySuppliers = inventorySupplierMapper.selectByExample(inventorySupplierExample);
        List<InventorySupplierDto> inventorySupplierDtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(inventorySuppliers)) {
            inventorySupplierDtos = BeanCopyUtil.copyList(inventorySuppliers, InventorySupplierDto.class);
            for (InventorySupplierDto item : inventorySupplierDtos) {
                if (item.getQuantity() == null) {
                    item.setQuantity(0);
                }
                if (item.getQuantityprepare() == null) {
                    item.setQuantityprepare(0);
                }
                item.setQuantity(item.getQuantity() - item.getQuantityprepare());
                if(item.getBinflag() != null) {
                    item.setBinflagStr(item.getBinflag() == 1 ? "是" : "否");
                }
            }
        }
        return inventorySupplierDtos;

	}

}
