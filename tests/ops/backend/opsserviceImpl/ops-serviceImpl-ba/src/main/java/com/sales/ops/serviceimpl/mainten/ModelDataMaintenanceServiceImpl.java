package com.sales.ops.serviceimpl.mainten;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsBsQuerypriceNewmodel;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductBomDetail;
import com.sales.ops.db.entity.ProductDelivery;
import com.sales.ops.db.extdao.BasicsMaintenanceDao;
import com.sales.ops.dto.mainten.ModelData;
import com.sales.ops.service.mainten.BasicsMaintenanceService;

@Service
public class ModelDataMaintenanceServiceImpl implements BasicsMaintenanceService {

	private static final String split_symbol = "!";

	private static final int ZERO = 0;
	private static final int ONE = 1;

	private static final int YES_MAIN_EXIST = 20001;
	private static final int NOT_DATA_INFO = 30001;
	private static final int NOT_SAVE_DATA = 40001;
	private static final int YES_SAVE_DATA = 20000;

	@Autowired
	private BasicsMaintenanceDao maintenanceDao;

	@Override
	public PageInfo<ModelData> findProductModelByNo(String modelNo, Boolean fuzzy, Integer pageNumber, Integer pageSize,
			String startTime, String endTime) {

		return PageHelper.startPage(pageNumber, pageSize)
				.doSelectPageInfo(() -> maintenanceDao.findProductModelByNo(modelNo, fuzzy, startTime, endTime));
	}

	@Override
	public List<ModelData> findProductModelListByNo(String modelNo) {
		return maintenanceDao.findProductModelNoByDim(modelNo);
	}

	@Override
	public List<ModelData> findSplitInfoByWholeNo(String modelNo) {

		return maintenanceDao.findSplitInfoByWholeNo(modelNo);
	}

	@Override
	public List<ProductBom> findWholeModelInfoByModelNo(String modelNo) {
		return maintenanceDao.findWholeModelInfoByModelNo(modelNo);
	}

	@Override
	@Transactional
	public Integer updateWholeModelInfoByModelNo(List<ProductBom> bom) {
		for (ProductBom list : bom) {
			if (list.getPriorityLevel() == null || list.getPriorityComplete() == null) {
				return NOT_DATA_INFO;
			}
			// bug8640 B91717 页面编辑更新时，只更新变更那条的 变更人以及变更时间
			// 根据bomid查询整单信息，然后对比新旧数据,三个数值 只要有一个变更就更新
			ProductBom bomOld = maintenanceDao.findWholeModelInfoByBomId(list.getBomid());
			if (!bomOld.getIsvalid().equals(list.getIsvalid())
					|| !bomOld.getPriorityComplete().equals(list.getPriorityComplete())
					|| !bomOld.getPriorityLevel().equals(list.getPriorityLevel())) {
				list.setUpdatetime(new Date());
				// list.setUpdateuser(dto.getUserName());
				maintenanceDao.updateWholeModelInfoByModelNo(list);
			}
		}
		return YES_SAVE_DATA;
	}

	@Override
	@Transactional
	public Integer insertProductModel(List<ModelData> modelData) {
		if (modelData.isEmpty() || modelData.size() == 0) {
			return NOT_DATA_INFO;
		}
		// project: 根据整型号查询数据库中已经存在的数据；
		String modelNo = modelData.get(0).getWholeModelNo();
		List<ModelData> project = maintenanceDao.findSplitInfoByWholeNo(modelNo);
		if (project.isEmpty() || project.size() == 0) {
			this.insertBom(modelData);
			return YES_SAVE_DATA;
		}

		// num: 初始变量，判断入参是否需要存入数据库；
		// stream() 先分组、再取差集、最后赋值判断是否需要存入数据库；
		final AtomicInteger num = new AtomicInteger(ZERO);
		Map<Integer, List<ModelData>> map = project.stream().collect(Collectors.groupingBy(ModelData::getBomId));
		try {
			map.forEach((key, value) -> {
				long count = value.stream().filter(i -> !modelData.stream().map(ModelData::getSplitModelNo)
						.collect(Collectors.toList()).contains(i.getSplitModelNo())).count();

				// 若count == ZERO，表示取不出子型号的差集.
				// 则需要判断型号的数量，数量相同不允许存入数据库，反之允许存储！
				if (count == ZERO) {
					Map<String, Integer> quantityMap = modelData.stream()
							.collect(Collectors.toMap(ModelData::getSplitModelNo, ModelData::getQuantity));
					for (Map.Entry<String, Integer> entry : quantityMap.entrySet()) {
						long quantity = value.stream().filter(i -> entry.getKey().equals(i.getSplitModelNo())
								&& entry.getValue().equals(i.getQuantity())).count();
						num.set(quantity == ONE ? ONE : ZERO);
						if (num.intValue() == ZERO)
							break;
					}
				}
				if (num.intValue() == ONE)
					throw new RuntimeException("终止forEach循环！！！");
			});
		} catch (RuntimeException e) {
			// 暂不做任何异常处理！！！
		}
		// 大于零，则不需要存入数据库；
		if (num.intValue() > ZERO) {
			return NOT_SAVE_DATA;
		}
		this.insertBom(modelData);
		return YES_SAVE_DATA;
	}

	/**
	 * 存入拆分型号数据
	 *
	 * @param modelData
	 *            人工增加的数据
	 * @param dto
	 *            获取当前登录人
	 */
	private void insertBom(List<ModelData> modelData) {
		// 存入主表数据
		ProductBom bom = new ProductBom();
		ModelData data = modelData.get(0);
		bom.setModelno(data.getWholeModelNo());
		bom.setPriorityComplete(data.isPriorityComplete());
		int maxNum = maintenanceDao.selectMaxNumByModelNo(data.getWholeModelNo());
		bom.setPriorityLevel(maxNum);
		bom.setIsvalid(data.isValid());
		bom.setUpdateuser(data.getUpdateUser());
		bom.setCreateuser(data.getUpdateUser());
		// bom.setCreateuser(dto.getUserName());
		// bom.setUpdateuser(dto.getUserName());
		bom.setCreatetime(new Date());
		bom.setUpdatetime(new Date());
		maintenanceDao.insertProductBom(bom);
		// 存入副表数据
		for (ModelData model : modelData) {
			ProductBomDetail bomDetail = new ProductBomDetail();
			bomDetail.setBomid(bom.getBomid());
			bomDetail.setModelno(model.getSplitModelNo());
			bomDetail.setQuantity(model.getQuantity());
			bomDetail.setClassify(model.isClassify());
			bomDetail.setCreatetime(new Date());
			bomDetail.setUpdatetime(new Date());
			bomDetail.setCreateuser(model.getUpdateUser());
			bomDetail.setUpdateuser(model.getUpdateUser());
			maintenanceDao.insertProductBomDetails(bomDetail);
		}
	}

	@Override
	public PageInfo<ProductDelivery> findProductDeliveryByNo(String modelNo, Integer pageNumber, Integer pageSize,
			String startTime, String endTime) {
		return PageHelper.startPage(pageNumber, pageSize)
				.doSelectPageInfo(() -> maintenanceDao.findProductDeliveryByNo(modelNo, startTime, endTime));
	}

	@Override
	public List<Map<String, String>> findAllSupplier() {
		return maintenanceDao.findAllSupplier();
	}

	@Override
	public Map<String, String> findCountryBySupplierId(String supplyId) {
		return maintenanceDao.findCountryBySupplierId(supplyId);
	}

	@Override
	public List<Map<String, String>> findAllCountry() {
		return maintenanceDao.findAllCountry();
	}

	@Override
	@Transactional
	public int insertProductDelivery(List<ProductDelivery> lists) {
		for (ProductDelivery list : lists) {
			list.setCreatetime(new Date());
			list.setUpdatetime(new Date());
			// bug8640 修改更新人直接从前端获取，B91717
			// list.setCreateuser(dto.getUserName());
			// list.setUpdateuser(dto.getUserName());
			String source = list.getResultsource();
			// 判断数据源为3S新轮到的数据或担当新添加的数据则直接存入到数据库
			if (split_symbol.equals(source.substring(0, 1))) {
				list.setResultsource(source.substring(source.substring(0, source.indexOf(split_symbol)).length() + 1));
			} else if (maintenanceDao.selectDeliveryByModelNo(list.getModelno(), list.getSupplyid()) != null) {
				continue;
			}
			maintenanceDao.insertProductDelivery(list);
		}
		// 根据型号查询供应商数据，DB数据 > 1时则触发变更供应商代码
		String modelNo = lists.get(0).getModelno();
		List<ProductDelivery> deliveries_db = maintenanceDao.selectDeliveryByNo(modelNo);
		if (!deliveries_db.isEmpty() && deliveries_db.size() > 1) {
			// 判断主供应商的数量是否 > 1, 大于则将非最大日期的数据全部更改为false
			if (deliveries_db.stream().filter(i -> i.getIsprimary()).count() > 1) {
				List<ProductDelivery> deliveries = lists.stream().filter(i -> i.getIsprimary())
						.collect(Collectors.toList());
				// stream() 获取最大的日期的记录，再根据型号、供应商去修改数据
				String supplyId = deliveries.stream().max(Comparator.comparing(ProductDelivery::getUpdatetime)).get()
						.getSupplyid();
				maintenanceDao.updateMainDeliveryBySupplyId(modelNo, supplyId);
			}
		}
		return YES_MAIN_EXIST;
	}

	@Override
	@Transactional
	public void updateProductDelivery(ProductDelivery delivery) {
		if (delivery != null && StringUtils.isNotBlank(delivery.getModelno())) {
			if (delivery.getIsprimary()) {
				ProductDelivery productDelivery = maintenanceDao
						.selectProductDeliveryByModelNoAndIsPrimary(delivery.getModelno(), delivery.getIsprimary());
				productDelivery.setIsprimary(false);
				// bug8640 修改更新人直接从前端获取，B91717
				productDelivery.setUpdateuser(delivery.getUpdateuser());
				// productDelivery.setUpdateuser(dto.getUserName());
				productDelivery.setUpdatetime(new Date());
				maintenanceDao.updateProductDeliveryByIsPrimary(productDelivery);
			}
			// bug8640 修改更新人直接从前端获取，B91717
			// delivery.setUpdateuser(dto.getUserName());
			delivery.setUpdatetime(new Date());
			maintenanceDao.updateProductDelivery(delivery);
		}
	}

	@Override
	@Transactional
	public void deleteProductDelivery(String modelNo, String supplyId) {
		// if (StringUtils.isNotBlank(modelNo) &&
		// StringUtils.isNotBlank(supplyId)) {
		// ProductDelivery delivery =
		// maintenanceDao.selectProductDeliveryByModelNoAndSupplyId(modelNo,
		// supplyId.substring(0, 2));
		// if (delivery != null) {
		// productUpdateDao.insertDeliveryDelete(modelNo,
		// delivery.getSupplyid());
		// maintenanceDao.deleteProductDelivery(modelNo,
		// delivery.getSupplyid());
		// }
		//
		// }
	}

	@Override
	public ProductDelivery selectDeliveryByModelNo(String modelNo, String supplierId) {
		if (StringUtils.isNotBlank(supplierId) && supplierId.length() > 2) {
			return maintenanceDao.selectDeliveryByModelNo(modelNo, supplierId.substring(0, 2));
		}
		return null;
	}

	@Override
	public List<ProductDelivery> selectDeliveryListByNo(String modelNo) {
		return maintenanceDao.selectDeliveryListByNo(modelNo);
	}

	@Override
	public List<ProductDelivery> findBeingSuppliersByModelNo(String modelNo) {
		// 根据型号查询供应商的数据(数据库、3S规则接口)
		List<ProductDelivery> suppliers_db = maintenanceDao.findBeingSuppliersByModelNo(modelNo);
		// List<ProductDelivery> supplier_inter =
		// productService.getNewModelSupplier(modelNo);
		// // 取俩个的差集，若不为空则addAll()到DB结果集中
		// if (!suppliers_db.isEmpty() && !supplier_inter.isEmpty()) {
		// List<ProductDelivery> difference = supplier_inter.stream().filter(i
		// -> !suppliers_db.stream()
		// .map(ProductDelivery::getSupplyid).collect(Collectors.toList()).contains(i.getSupplyid()))
		// .collect(Collectors.toList());
		// if (!difference.isEmpty())
		// suppliers_db.addAll(difference);
		// }
		// if (suppliers_db.isEmpty() && !supplier_inter.isEmpty()) {
		// suppliers_db.addAll(supplier_inter);
		// }
		return suppliers_db;
	}

	@Override
	public List<OpsBsQuerypriceNewmodel> findQueryPriceNewModelByModelNo(String modelNo) {
		return maintenanceDao.findQueryPriceNewModelByModelNo(modelNo);
	}

	@Override
	@Transactional
	public int insertQueryPrice(List<ProductDelivery> lists) {
		int success = this.insertProductDelivery(lists);
		if (success == YES_MAIN_EXIST) {
			maintenanceDao.updateQueryPrice(lists.get(0).getModelno());
			return YES_MAIN_EXIST;
		}
		return NOT_SAVE_DATA;
	}
}
