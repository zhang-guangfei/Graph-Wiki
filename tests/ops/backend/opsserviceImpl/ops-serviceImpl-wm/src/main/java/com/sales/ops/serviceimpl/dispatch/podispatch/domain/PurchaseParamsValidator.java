package com.sales.ops.serviceimpl.dispatch.podispatch.domain;

import com.alibaba.fastjson.JSON;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.PoToWmDto;
import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.utils.PoNoUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author C12961
 * @date 2023/3/2 14:40
 */
public class PurchaseParamsValidator {

	public static void reset(OpsPurchaseorder purchase, List<OpsInventoryMove> moveList) throws OpsException {
		// 查询是否有非生产中库存
		boolean hasNotProduct = moveList.stream().anyMatch(
				move -> !StringUtils.equals(move.getInventoryStatus(), InventoryStatusEnum.PRODUCE.getCode()));
		if (hasNotProduct) {
			// 不应该有非生产中库存，如果有则报错
			throw Exceptions.OpsException("库存中有非生产中库存");
		}
	}

	public static void moveForProduct(PurchaseAcceptContextItem item, List<OpsInventoryMove> moves)
			throws OpsException {
		if (moves.size() > 1) {
			throw Exceptions.OpsException("一条请购单查询出多条move库存");
		}
		OpsInventoryMove move = moves.get(0);
		// 如果有非生产中库存，则报采购转订接单异常：有非生产中库存
		boolean isProduct = StringUtils.equals(move.getInventoryStatus(), InventoryStatusEnum.PRODUCE.getCode());
		if (!isProduct) {
			throw Exceptions.OpsException("采购转订接单失败：存在非P生产中库存数据，采购转订接单号：" + PoNoUtil.getFullPoNo(item.getPurchase()));
		}
		item.setOldProduct(move);
	}

	public static void accept(PoToWmDto dto) throws OpsException {
		// 1.非空判断
		if (Objects.isNull(dto)) {
			throw Exceptions.OpsException("无接单数据");
		}
		OpsPurchaseorder purchase = dto.getPurchase();
		List<OpsRequestpurchase> requests = dto.getRequests();
		if (Objects.isNull(purchase)) {
			throw Exceptions.OpsException("无接单数据");
		}
		if (CollectionUtils.isEmpty(requests)) {
			throw Exceptions.OpsException("无接单数据");
		}
		// 2.判断请购数量和采购数量
		// 采购数量不能小于等于0
		if (purchase.getQuantity() <= 0) {
			throw Exceptions.OpsException("导入订单采购数量需大于0。" + purchase.getOrderno());
		}
		// 计算请购总数
		Integer sum_qty = requests.stream().map(OpsRequestpurchase::getQuantity).reduce(0, Integer::sum);
		// 采购数量不能小于请购数量
		if (purchase.getQuantity() < sum_qty) {
			throw Exceptions.OpsException("导入请购总数不可大于采购数量。" + purchase.getOrderno());
		}

		// 3.判断仓库和库存是否有效
		// 采购仓不能为空
		if (StringUtils.isEmpty(purchase.getReceivewarehouseid())) {
			throw Exceptions.OpsException("导入数据.库房不可空。" + purchase.getOrderno());
		}
		// 请购仓和库存类别的判断
		for (OpsRequestpurchase request : requests) {
			// 请购库房不能空
			if (StringUtils.isEmpty(request.getRequestwarehouseid())) {
				throw Exceptions.OpsException("导入请购库房有误。" + purchase.getOrderno());
			}
			// 请购库存类别不能为空
			if (StringUtils.isEmpty(request.getInventorytypecode())) {
				throw Exceptions.OpsException("导入库存类别有误，不可为空。" + purchase.getOrderno());
			}
		}
	}





}
