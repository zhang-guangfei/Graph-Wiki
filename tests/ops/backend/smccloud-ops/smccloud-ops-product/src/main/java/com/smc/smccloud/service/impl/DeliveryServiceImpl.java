package com.smc.smccloud.service.impl;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.sales.ops.dto.eta.FindETADataDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.delivery.CanUseQtyAndIsBinVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.db.entity.OpsPoTranstypeConfig;
import com.sales.ops.db.entity.OpsPoWarehouseDeliveryday;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.smc.smccloud.mapper.BindataMapper;
import com.smc.smccloud.mapper.DeliveryMapper;
import com.smc.smccloud.model.adapter.DeliveryInventory;
import com.smc.smccloud.model.delivery.DeliveryInfo;
import com.smc.smccloud.model.delivery.DeliveryModelInfo;
import com.smc.smccloud.model.delivery.ProductSupplyInfo;
import com.smc.smccloud.service.DeliveryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***
 * 计算参考货期天数
 *
 * @author MT
 *
 */

@Service
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

	@Resource
	private DeliveryMapper deliveryMapper;
	@Resource
	private BindataMapper bindataMapper;

	private static final int CAL_SIZE = 2000;

	@Override
	public DeliveryInfo getDeliveryDay(DeliveryInfo infos) {
		long lstart = System.currentTimeMillis();
		Map<String, String> trans = transType();
		List<OpsPoTranstypeConfig> transConfigList = deliveryMapper.shipModelConfig();
		// 获取该部门对应的收货仓
		OpsWarehouseSalesbranchConfig dday = deliveryMapper.getFirstPurchaseWarehouse(infos.getDeptNo());
		// 获取发货仓优先级
		List<String> priority = deliveryMapper.getPurchaseWarehousePriority(infos.getDeptNo());
		List<String> modelNos = new ArrayList<>(infos.getModelList().size());
		Map<String, ProductSupplyInfo> modelMap = new HashMap<>(infos.getModelList().size());
		// 获取该部门所有仓的运输时间
		List<OpsWarehouseSalesbranchConfig> configList = deliveryMapper
				.getWarehouseSalesbranchConfig(infos.getDeptNo());

		// 获取不重复型号列表
		infos.getModelList().forEach(i -> {
			if (!modelMap.containsKey(i.getModelNo())) {
				modelNos.add(i.getModelNo());
				modelMap.put(i.getModelNo(), null);
			}
		});

		// 获取型号Product信息、可用在库信息
		List<ProductSupplyInfo> productSupplyList = new ArrayList<>(modelNos.size());
		List<DeliveryInventory> canUseList = new ArrayList<>(modelNos.size());
		// SET在增加ADD时会去重，因此使用SET
		Set<String> binModelSet = new HashSet<>(modelNos.size());
		List<String> temp;
		List<ProductSupplyInfo> productSupplyTemp;
		List<DeliveryInventory> canUseTemp;
		List<String> binModelTemp;

		for (int i = 0; i < modelNos.size(); i++) {
			if (i % CAL_SIZE == 0) {
				if (i + CAL_SIZE < modelNos.size()) {
					temp = modelNos.subList(i, i + CAL_SIZE);
				} else {
					temp = modelNos.subList(i, modelNos.size());
				}
				productSupplyTemp = deliveryMapper.selectProductSupplyInfo(temp);
				if (productSupplyTemp != null && productSupplyTemp.size() > 0) {
					productSupplyList.addAll(productSupplyTemp);
				}
				canUseTemp = deliveryMapper.getInventoryCanUseCommonGK(infos.getDeptNo(), infos.getCustomerNo(), temp);
				if (canUseTemp != null && canUseTemp.size() > 0) {
					canUseList.addAll(canUseTemp);
				}
				if (StringUtils.isNotBlank(infos.getGroupCustomer())) {
					canUseTemp = deliveryMapper.getInventoryCanUseCommonGroup(infos.getDeptNo(), temp,
							infos.getGroupCustomer());
					if (canUseTemp != null && canUseTemp.size() > 0) {
						canUseList.addAll(canUseTemp);
					}
				}
				canUseTemp = deliveryMapper.getInventoryCanUseCommonTY(infos.getDeptNo(), infos.getCustomerNo(), temp);
				if (canUseTemp != null && canUseTemp.size() > 0) {
					canUseList.addAll(canUseTemp);
				}
				// bug9804马腾 增加委托在库通用库存查询
				canUseTemp = deliveryMapper.getInventoryWTCanUse(infos.getCustomerNo(), temp);
				if (canUseTemp != null && canUseTemp.size() > 0) {
					canUseList.addAll(canUseTemp);
				}
				binModelTemp = bindataMapper.listBinModelTY(temp, infos.getCustomerNo());
				if (binModelTemp != null && binModelTemp.size() > 0) {
					binModelSet.addAll(binModelTemp);
				}
				binModelTemp = bindataMapper.listBinModelGK(temp, infos.getCustomerNo());
				if (binModelTemp != null && binModelTemp.size() > 0) {
					binModelSet.addAll(binModelTemp);
				}
			}
		}
		long lend = System.currentTimeMillis();
		log.info("执行计算交货期数据库操作完毕,耗时(s): " + (lend - lstart) / 1000);

		// 汇总型号的可用公共库存
		Map<String, List<DeliveryInventory>> modelCanUse = new HashMap<>(canUseList.size());
		if (canUseList.size() > 0) {
			for (DeliveryInventory use : canUseList) {
				if (modelCanUse.containsKey(use.getModelNo())) {
					canUseTemp = modelCanUse.get(use.getModelNo());
					canUseTemp.add(use);
					modelCanUse.put(use.getModelNo(), canUseTemp);
				} else {
					canUseTemp = new ArrayList<>();
					canUseTemp.add(use);
					modelCanUse.put(use.getModelNo(), canUseTemp);
				}
			}
		}
		// 汇总产品信息
		modelMap.clear();
		productSupplyList.forEach(j -> {
			if (!modelMap.containsKey(j.getModelNo()))
				modelMap.put(j.getModelNo(), j);
		});
		// 获取仓间调拨时间
		List<OpsPoWarehouseDeliveryday> warehouseDeliveryDays = deliveryMapper.warehouseTransWarehouse();
		Map<String, List<OpsPoWarehouseDeliveryday>> deliveryMap = new HashMap<>(warehouseDeliveryDays.size());
        // from -> to
		for (OpsPoWarehouseDeliveryday item : warehouseDeliveryDays) {
		    if (deliveryMap.containsKey(item.getFromwarehouse())) {
                List<OpsPoWarehouseDeliveryday> opsPoWarehouseDeliverydays = deliveryMap.get(item.getFromwarehouse());
                opsPoWarehouseDeliverydays.add(item);
                deliveryMap.put(item.getFromwarehouse(),opsPoWarehouseDeliverydays);
            } else {
                List<OpsPoWarehouseDeliveryday> list = new ArrayList<>();
                list.add(item);
                deliveryMap.put(item.getFromwarehouse(),list);
            }
        }
        // to -> from
        for (OpsPoWarehouseDeliveryday item : warehouseDeliveryDays) {
            String from = item.getFromwarehouse();
            String to = item.getTowarehouse();
            item.setFromwarehouse(to);
            item.setTowarehouse(from);
            if (deliveryMap.containsKey(to)) {
                List<OpsPoWarehouseDeliveryday> opsPoWarehouseDeliverydays = deliveryMap.get(to);
                opsPoWarehouseDeliverydays.add(item);
                deliveryMap.put(to,opsPoWarehouseDeliverydays);
            } else {
                List<OpsPoWarehouseDeliveryday> list = new ArrayList<>();
                list.add(item);
                deliveryMap.put(to,list);
            }
        }
//		for (OpsPoWarehouseDeliveryday k : warehouseDeliveryDays) {
//		    // from -> to
//			if (!deliveryMap.containsKey(k.getFromwarehouse())) {
//				deliveryMap.put(k.getFromwarehouse(), warehouseDeliveryDays.stream()
//						.filter(i -> k.getFromwarehouse().equals(i.getFromwarehouse())).collect(Collectors.toList()));
//			}
//            // to -> from
//            if (!deliveryMap.containsKey(k.getTowarehouse())) {
//                deliveryMap.put(k.getTowarehouse(), warehouseDeliveryDays.stream()
//                        .filter(i -> k.getTowarehouse().equals(i.getTowarehouse())).collect(Collectors.toList()));
//            }
//		}

		ProductSupplyInfo pro;
		List<DeliveryInventory> canAll;
		Map<String, DeliveryInventory> canUseStock;
		// List<DeliveryInventory> dlvInventory;

		for (DeliveryModelInfo info : infos.getModelList()) {
			// 判断BIN型号-设置BIN标识
			if (binModelSet.contains(info.getModelNo())) {
				info.setBin(true);
			}
			int flag = 0;
			pro = null;
			canAll = null;

			if (modelMap.containsKey(info.getModelNo())) {
				pro = modelMap.get(info.getModelNo());
			}
			// 判断库存在库
			if (modelCanUse.containsKey(info.getModelNo())) {
				canAll = modelCanUse.get(info.getModelNo());
			}
			if (StringUtils.isNotBlank(info.getPpl()) || StringUtils.isNotBlank(info.getPj())) {
				if (canAll != null && canAll.size() > 0) {
					canAll.addAll(deliveryMapper.getInventoryCanUse(infos.getDeptNo(), info.getModelNo(),
							infos.getCustomerNo(), info.getPpl(), info.getPj()));
				} else {
					canAll = deliveryMapper.getInventoryCanUse(infos.getDeptNo(), info.getModelNo(),
							infos.getCustomerNo(), info.getPpl(), info.getPj());
				}
			}

			if (CollectionUtils.isNotEmpty(canAll)) {
				// 汇总可用库存
				int canUseQuantity = 0;
				canUseStock = new HashMap<>(canAll.size());

				for (DeliveryInventory inventory : canAll) {
					canUseQuantity = canUseQuantity + inventory.getCanuse();
					if (canUseStock.containsKey(inventory.getStockCode())) {
						canUseStock.get(inventory.getStockCode()).setCanuse(
								canUseStock.get(inventory.getStockCode()).getCanuse() + inventory.getCanuse());
					} else {
						canUseStock.put(inventory.getStockCode(), inventory);
					}
				}
				info.setCanuseQuantity(canUseQuantity);

				// priority: 发货仓优先级
				for (String p : priority) {
					if (canUseStock.containsKey(p)) {
						DeliveryInventory inventory = canUseStock.get(p);
						if (inventory.getCanuse() >= info.getQuantity()) {
						    // dday:  该部门对应的收货仓信息
							if (dday != null) {
                                /**
                                 * 11828bug  updateUser lyc
                                 * 现状只修改出在库的计算逻辑
                                 * 1.如果判断出在库，则先判断仓库类型。
                                 * 2. warehouse type是sub的时候，则取分库到营业所配置的时间（ops_warehouse_salesbranch_config）即国内运输时间，不再累加仓间调拨时间。
                                 * 3. warehouse type是mater的时候，且集约仓和发货仓不一致的情况，计算仓间调拨时间(ops_po_warehouse_deliveryday)
                                 * 优化：可以反向计算，无论from，还是to, 再计算集约仓到营业所的时间，结果= 仓间调拨+国内运输时间
                                 */
                                int subTranDay = -1;
                                if (StringUtils.equals(inventory.getWarehouseType(), "SUB")) {
                                    for (OpsWarehouseSalesbranchConfig item : configList) {
                                        if (inventory.getStockCode().equals(item.getWarehouseCode())) {
                                            subTranDay = item.getDeliveryDay();
                                            break;
                                        }
                                    }
                                    // 等于0代表没有查出国内运输时间 默认4天
                                    if (subTranDay == -1) {
                                        subTranDay = 4;
                                    }
                                    info.setDeliveryDay(subTranDay);
                                    info.setDeliveryDayDescription(
                                            inventory.getStockType() + ":" + inventory.getStockCode());
                                } else {
                                    // 仓间调拨
                                    Integer warehouseTrans = 0;
                                    int ok = 0;
                                    if (!StringUtils.equals(inventory.getStockCode(), dday.getWarehouseCode())) {
                                        // deliveryMap: 仓间调拨配置信息
                                        if (deliveryMap.containsKey(inventory.getStockCode())) {
                                            for (OpsPoWarehouseDeliveryday a : deliveryMap.get(inventory.getStockCode())) {
                                                if (StringUtils.equals(a.getTowarehouse(), dday.getWarehouseCode())) {
                                                    warehouseTrans = a.getDeliveryday();
                                                    ok = 1;
                                                }
                                            }
                                        } else {
                                            warehouseTrans = 3;
                                            ok = 1;
                                        }
                                        if (ok == 0) {
                                            warehouseTrans = 3;
                                        }
                                    }
                                    info.setDeliveryDay(dday.getDeliveryDay() + warehouseTrans);
                                    info.setDeliveryDayDescription(
                                            inventory.getStockType() + ":" + inventory.getStockCode());
                                }
								flag = 1;
								break;
							}
						}
					}
				}
			}
			if (flag == 0) {
				// 判断主供应商
				if (pro != null) {
					int day = 1;
					// 判断供应商在库
					if (pro.getQuantity() != null && pro.getQuantity() > info.getQuantity()) {
						day++;
						info.setDeliveryDayDescription(pro.getSupplyId() + "采购,出供应商库存");
					} else {
						info.setDeliveryDayDescription(pro.getSupplyId() + "采购");
                        if (StringUtils.isNotBlank(pro.getDesignTypeId())) {
                            // 标准品
                            if ("1".equals(pro.getDesignTypeId())) {
                                day += Optional.ofNullable(pro.getStdProductManuDay()).orElse(0);
                            } else {
                                day += Optional.ofNullable(pro.getNstdProductManuDay()).orElse(0);
                            }
                        } else {
                            if (Objects.nonNull(pro.getNstdProductManuDay())) {
                                day += pro.getNstdProductManuDay();
                            } else {
                                day += Optional.ofNullable(pro.getStdProductManuDay()).orElse(0);
                            }
                        }
					}
					boolean ship;
					if (pro.getNonstandard_sized_product() == null) {
						ship = judgeShip(info.getModelNo(), transConfigList, "");
					} else {
						ship = judgeShip(info.getModelNo(), transConfigList, pro.getNonstandard_sized_product());
					}

					if (ship && pro.getShipDeliveryDay() != null) {
						info.setDeliveryDayDescription(info.getDeliveryDayDescription() + ",海运");
						day += pro.getShipDeliveryDay();
					} else {
						info.setDeliveryDayDescription(info.getDeliveryDayDescription()
								+ (pro.getFstTransTypeId() != null ? "," + trans.get(pro.getFstTransTypeId()) : ""));
						day += pro.getFstDeliveryDay();
					}

					if (dday != null) {
						day += dday.getDeliveryDay();
						// 仓间调拨
						if (!StringUtils.equals(dday.getWarehouseCode(), pro.getWarehouseCode())) {
							if (deliveryMap.containsKey(pro.getWarehouseCode())) {
								for (OpsPoWarehouseDeliveryday w : deliveryMap.get(pro.getWarehouseCode())) {
									if (StringUtils.equals(w.getTowarehouse(), dday.getWarehouseCode())) {
										day += w.getDeliveryday();
										break;
									}
								}
							}
						}
					}
					info.setDeliveryDay(day);
					flag = 1;
				}
			}
			if (flag == 0) {
				info.setDeliveryDay(26);
				info.setDeliveryDayDescription("默认天数");
			}
			info.setSuitableDay(info.getDeliveryDay()+2);
		}
		lend = System.currentTimeMillis();
		log.info("执行计算交货期完毕,耗时(s): " + (lend - lstart) / 1000);
		return infos;
	}

    @Override
    public  ResultVo<List<CanUseQtyAndIsBinVO>> getDeliveryIsBInAndCanUseQty(List<FindETADataDto> dataList) {
        if(CollectionUtils.isEmpty(dataList)) {
            return ResultVo.failure("参数不可为空.");
        }

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        List<CanUseQtyAndIsBinVO> list = new ArrayList<>();

        log.info("参考货期getDeliveryIsBInAndCanUseQty => 开始  型号: {}", dataList.size());
        long lstart = System.currentTimeMillis();
        try {
            for(FindETADataDto item: dataList) {

                CanUseQtyAndIsBinVO canUseQtyAndIsBinVO = new CanUseQtyAndIsBinVO();
                canUseQtyAndIsBinVO.setModelNo(item.getModelNo());
                canUseQtyAndIsBinVO.setQty(item.getQty());

                fixedThreadPool.execute(() -> {

                    // 通用库存
                    List<DeliveryInventory> canUseInventoryList = deliveryMapper.getInventoryCanUseCommonTY(item.getDeptNo(), "", Collections.singletonList(item.getModelNo()));

                    // 顾客通用库存
                    if(StringUtils.isNotBlank(item.getEndUser())) {
                        List<DeliveryInventory> inventoryCanUseCommonGK = deliveryMapper.getInventoryCanUseCommonGK(item.getDeptNo(), item.getEndUser(), Collections.singletonList(item.getModelNo()));
                        if (CollectionUtils.isNotEmpty(inventoryCanUseCommonGK)) {
                            canUseInventoryList.addAll(inventoryCanUseCommonGK);
                        }

                        // 客户可用委托在库库存
                        List<DeliveryInventory> inventoryWTCanUse = deliveryMapper.getInventoryWTCanUse(item.getEndUser(), Collections.singletonList(item.getModelNo()));
                        if (CollectionUtils.isNotEmpty(inventoryWTCanUse)) {
                            canUseInventoryList.addAll(inventoryWTCanUse);
                        }

                    }

                    // 查询战略在库
                    if (StringUtils.isNotBlank(item.getGroupCustomerNo())) {
                        String[] split = item.getGroupCustomerNo().split(",");
                        List<DeliveryInventory> inventoryCanUseCommonGroupByDelivery = deliveryMapper.getInventoryCanUseCommonGroupByDelivery(item.getDeptNo(), Collections.singletonList(item.getModelNo()), Arrays.asList(split));
                        if (CollectionUtils.isNotEmpty(inventoryCanUseCommonGroupByDelivery)) {
                            canUseInventoryList.addAll(inventoryCanUseCommonGroupByDelivery);
                        }
                    }

                    if (StringUtils.isNotBlank(item.getPplNo()) || StringUtils.isNotBlank(item.getProjectNo())) {
                        // 获取ppl及pj可用库存
                        List<DeliveryInventory> pplCanUseInvList = deliveryMapper.getInventoryCanUse(item.getDeptNo(), item.getModelNo(), item.getEndUser(), item.getPplNo(), item.getProjectNo());
                        if (CollectionUtils.isNotEmpty(pplCanUseInvList)) {
                            canUseInventoryList.addAll(pplCanUseInvList);
                        }
                    }
                    // 汇总库存
                    canUseQtyAndIsBinVO.setCanuseQuantity(canUseInventoryList.stream().mapToInt(DeliveryInventory::getCanuse).sum());

                    // 判断bin
                    if (StringUtils.isNotBlank(item.getEndUser())) {
                        List<String> listBinModel = bindataMapper.listBinModel(Collections.singletonList(item.getModelNo()), item.getEndUser());
                        canUseQtyAndIsBinVO.setBin(!CollectionUtils.isEmpty(listBinModel));
                    }
                    list.add(canUseQtyAndIsBinVO);

                });
            }

            log.info("参考货期getDeliveryIsBInAndCanUseQty => 查询可用库存/bin 结束 耗时: {} s ",(System.currentTimeMillis() - lstart) / 1000);

            return ResultVo.success(list);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            fixedThreadPool.shutdown();
            while (true) {
                if (fixedThreadPool.isTerminated()) {
                    break;
                }
            }
        }
    }

    // 判断是否海运优先型号
	private boolean judgeShip(String modelNo, List<OpsPoTranstypeConfig> transConfigList, String bigModel) {
		// 超长超宽
		if (StringUtils.equals("1", bigModel) || StringUtils.equals("2", bigModel)) {
			return true;
		}
		// 海运优先清单
		for (OpsPoTranstypeConfig item : transConfigList) {
			if (StringUtils.equals("0", item.getType())) {
				if (item.getMatchtype()) {
					// 正则匹配
					if (modelNo.matches(item.getModelno())) {
						return true;
					}
				} else {
					// 完全匹配
					if (StringUtils.equals(modelNo, item.getModelno())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private Map<String, String> transType() {
		List<OpsPoTranstype> list = deliveryMapper.transtype();
		Map<String, String> trans = new HashMap<>(list.size());
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(i -> trans.put(i.getId(), i.getName()));
		}
		return trans;
	}

}
