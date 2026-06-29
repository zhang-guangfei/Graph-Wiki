package com.sales.ops.serviceimpl.po;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.excel.util.StringUtils;
import com.sales.ops.db.dao.OpsPurchaseinvoicetranslogMapper;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseinvoicetranslog;
import com.sales.ops.db.entity.OpsPurchaseinvoicetranslogExample;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.order.FinishPoDto;
import com.sales.ops.dto.order.FinishPoListForDto;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.po.FinishPoService;

@Service
public class FinishPoServiceImpl implements FinishPoService {

	@Autowired
	private BasePoService basePoService;

	@Autowired
	private OpsPurchaseinvoicetranslogMapper opsPurchaseinvoicetranslogMapper;

	@Override
	public List<FinishPoListForDto> getPoListByPoNo(List<FinishPoDto> info) {
		// TODO 根据订单号查询还是根据拆分号查询？数量返回请购数量还是采购数量？
		List<FinishPoListForDto> list = new ArrayList<FinishPoListForDto>();
		info.forEach(i -> {
			FinishPoListForDto dto = new FinishPoListForDto();
			dto.setpOrderNo(i.getpOrderNo());
			dto.setpOrderItem(i.getpOrderItem());
			if (i.getpSplitNo() == null) {
				dto.setpFullNo(i.getpOrderNo() + "-" + i.getpOrderItem().toString());
			} else {
				dto.setpFullNo(i.getpOrderNo() + "-" + i.getpOrderItem().toString() + "-" + i.getpSplitNo().toString());
				dto.setpSplitNo(i.getpSplitNo());
			}
			OpsPurchaseorder order = basePoService.getPurchase(i.getpOrderNo(), i.getpOrderItem(), i.getpSplitNo());
			if (order == null) {
				// TODO 没有采购的时候到底返不返回请购数据
				OpsRequestpurchase request = basePoService.getRequestPurchase(i.getpOrderNo(), i.getpOrderItem(),
						i.getpSplitNo());
				if (request == null) {
					dto.setPoStatus("无请购采购数据");
					dto.setCanDelete(false);
				} else {
					dto.setSupplierId(request.getSupplierid());
					dto.setModelNo(request.getModelno());
					dto.setpQty(request.getQuantity());
					dto.setArrQty(0);
					dto.setPoStatus("请购中");
					if (StringUtils.equals(RequestPurchaseStatusEnum.DAICHULI, request.getStatecode())
							|| StringUtils.equals(RequestPurchaseStatusEnum.CHULIZHONG, request.getStatecode())) {
						dto.setCanDelete(true);
					}
					if (StringUtils.equals(RequestPurchaseStatusEnum.LANJIE, request.getStatecode())
							|| StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, request.getStatecode())) {
						dto.setCanDelete(true);
						dto.setPoStatus("请购拦截");
					}
				}
				dto.setCanFinish(false);
				dto.setMsg("未生成采购单");
			} else {
				List<OpsPurchaseinvoice> invoice = basePoService.getPurchaseInvoices(order);
				dto.setSupplierId(order.getSupplierid());
				dto.setModelNo(order.getModelno());
				dto.setpQty(order.getQuantity());
				if (CollectionUtils.isEmpty(invoice)) {
					dto.setArrQty(0);
				} else {
					// bug12957 供应商已发数量根据此预到货表ops_purchaseInvoiceTransLog查询
					OpsPurchaseinvoicetranslogExample tex = new OpsPurchaseinvoicetranslogExample();
					tex.createCriteria().andPonoEqualTo(invoice.get(0).getPono())
							.andLineitemEqualTo(invoice.get(0).getLineitem());
					List<OpsPurchaseinvoicetranslog> transList = opsPurchaseinvoicetranslogMapper.selectByExample(tex);
					if (!CollectionUtils.isEmpty(transList)) {
						dto.setArrQty(transList.stream().mapToInt(OpsPurchaseinvoicetranslog::getQuantity).sum());
					} else {
						dto.setArrQty(0);
					}
				}

				if (StringUtils.equals(order.getStatecode(), PurchaseOrderStatusEnum.YIFASONG)) {
					dto.setPoStatus("已发送");
					dto.setMsg("采购单状态不允许进行处理");
				} else if (StringUtils.equals(order.getStatecode(), PurchaseOrderStatusEnum.YIJIEDAN)) {
					dto.setPoStatus("已接单");
					dto.setCanDelete(true);
					dto.setCanFinish(true);
				} else if (StringUtils.equals(order.getStatecode(), PurchaseOrderStatusEnum.YUNSHUZHONG)) {
					dto.setPoStatus("运输中");
					if (order.getQuantity().compareTo(dto.getArrQty()) == 0) {
						dto.setMsg("采购单状态不允许进行处理");
					} else {
						dto.setCanFinish(true);
					}
				} else if (StringUtils.equals(order.getStatecode(), PurchaseOrderStatusEnum.DAICHULI)) {
					dto.setPoStatus("未发送");
				} else if (StringUtils.equals(order.getStatecode(), PurchaseOrderStatusEnum.YIWANCHENG)) {
					dto.setPoStatus("已完成");
					dto.setMsg("采购单状态不允许进行处理");
				} else {
					dto.setPoStatus("其他");
				}
			}
			list.add(dto);
		});
		return list;
	}

}
