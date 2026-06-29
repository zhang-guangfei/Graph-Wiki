package com.sales.ops.serviceimpl.purchase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsPoEventLogMapper;
import com.sales.ops.db.dao.OpsPurchaseinvoicetranslogMapper;
import com.sales.ops.db.entity.OpsPoEventLog;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseinvoicetranslog;
import com.sales.ops.db.entity.OpsPurchaseinvoicetranslogExample;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.order.FinishPoDto;
import com.sales.ops.dto.order.FinishPoListForDto;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.enums.PurchaseCancelSourceEnum;
import com.sales.ops.enums.PurchaseInvoiceStatusEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.po.FinishPoService;
import com.sales.ops.service.purchase.PurchaseCancelService;
import com.sales.ops.service.purchase.PurchaseCompleteService;
import com.sales.ops.service.wm.WmFinishInvMoveService;

/**
 * @author B91717 采购完纳
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseCompleteServiceImpl implements PurchaseCompleteService {

	@Autowired
	private BasePoService basePoService;

	@Resource
	private FinishPoService finishPoService;

	@Resource
	private WmFinishInvMoveService wmFinishInvMoveService;

	@Resource
	private OpsPoEventLogMapper opsPoEventLogMapper;

	@Resource
	private OpsPurchaseinvoicetranslogMapper opsPurchaseinvoicetranslogMapper;

	@Autowired
	private PurchaseCancelService purchaseCancelService;

	/**
	 * bug 11836 根据采购单号查询完纳信息
	 *
	 * @param info
	 * @return
	 */
	@Override
	public List<FinishPoListForDto> getPoListPoNo(FinishPoDto info) {
		// 是否加入采购状态的校验？
		List<FinishPoDto> finishPoDtos = new ArrayList<>();
		finishPoDtos.add(info);
		return finishPoService.getPoListByPoNo(finishPoDtos);
	}

	/**
	 * bug 11836 采购单完纳流程 1.前端校验输入完纳数量与已发数量的关系 2.调用订单接口，更改move表的P状态quantity
	 * 3.更新采购相关表的数量和状态（ops_requestpurchase,ops_purchaseOrder,ops_purchaseInvoice）
	 *
	 * @param finishPoListForDto
	 * @throws OpsException
	 */
	@Override
	public String doFinishPo(FinishPoListForDto finishPoListForDto) throws Exception {
		if (finishPoListForDto.getDoStatus().compareTo(1) == 0) {
			// bug12957 增加完纳删除
			RequestCancelDto cdto = new RequestCancelDto();
			cdto.setOrderno(finishPoListForDto.getpOrderNo());
			cdto.setItemno(finishPoListForDto.getpOrderItem());
			cdto.setSplititemno(finishPoListForDto.getpSplitNo());
			cdto.setSourceType(PurchaseCancelSourceEnum.FINISH_PURCHASE.getType());
			cdto.setReason("完纳删除");
			try {
				purchaseCancelService.cancelPurchase(cdto);
			} catch (OpsException e) {
				return e.getMessage();
			}
		} else {
			if (finishPoListForDto.getFinishPoQty() == null) {
				return "采购完纳数量不能为空，请补充后重试！";
			}
			if (finishPoListForDto.getFinishPoQty() == 0) {
				return "采购完纳数量不能零，请补充后重试！";
			}
			// 获取采购实体，校验状态
			OpsPurchaseorder opsPurchaseorder = basePoService.getPurchase(finishPoListForDto.getpOrderNo(),
					finishPoListForDto.getpOrderItem(), finishPoListForDto.getpSplitNo());
			// 首选校验采购单的状态是否可以完纳
			if (opsPurchaseorder.getStatecode().equals(PurchaseOrderStatusEnum.YIJIEDAN)
					|| opsPurchaseorder.getStatecode().equals(PurchaseOrderStatusEnum.YUNSHUZHONG)) {
				// 调用wm接口，更改move表的P状态quantity
				wmFinishInvMoveService.exeFinishInvMoveQty(finishPoListForDto);
				// 更新采购表相关表的状态和数量
				String result = purchaseWn(finishPoListForDto, opsPurchaseorder);
				if (StringUtils.isNotBlank(result)) {
					return result;
				}
				// bug12470 将记录表中参数变更为保存函数传输的参数值
				opsPolog(finishPoListForDto);
			} else {
				return "该采购单状态不能进行完纳，请重试！";
			}
		}
		return "";
	}

	/**
	 * 采购完纳，更新采购相关表信息
	 *
	 * @param finishPoListForDto
	 * @return
	 */
	public String purchaseWn(FinishPoListForDto finishPoListForDto, OpsPurchaseorder opsPurchaseorder) {
		org.joda.time.LocalDateTime today = new org.joda.time.LocalDateTime(new Date());
		if (opsPurchaseorder != null) {
			List<OpsPurchaseinvoice> opsPurchaseinvoices = basePoService.getPurchaseInvoices(opsPurchaseorder);
			Integer transQty = 0;
			Integer qtyOri = opsPurchaseorder.getQuantity();
			if (CollectionUtils.isNotEmpty(opsPurchaseinvoices) && !opsPurchaseinvoices.isEmpty()) {
				// bug12367 供应商已发数量根据此预到货表ops_purchaseInvoiceTransLog查询
				OpsPurchaseinvoicetranslogExample tex = new OpsPurchaseinvoicetranslogExample();
				tex.createCriteria().andPonoEqualTo(opsPurchaseinvoices.get(0).getPono())
						.andLineitemEqualTo(opsPurchaseinvoices.get(0).getLineitem());
				List<OpsPurchaseinvoicetranslog> transList = opsPurchaseinvoicetranslogMapper.selectByExample(tex);
				if (CollectionUtils.isNotEmpty(transList)) {
					transQty = transList.stream().mapToInt(OpsPurchaseinvoicetranslog::getQuantity).sum();
				}
			}
            // 入库货数量
			Integer qtyReceive = 0 ;
            if(opsPurchaseorder.getQtyreceive() != null){
                qtyReceive = opsPurchaseorder.getQtyreceive();
            }
			// bug12957 判断采购数量与供应商已发数量 20260605 C14717
			if (opsPurchaseorder.getQuantity().equals(transQty)) {
				return "采购单状态不允许进行处理，供应商已发数量等于采购数量";
			}
			if (StringUtils.isNotBlank(opsPurchaseorder.getInformation())) {
				opsPurchaseorder.setInformation(opsPurchaseorder.getInformation() + ";" + today
						+ finishPoListForDto.getOperator() + "强制完纳，初始采购数量为：" + opsPurchaseorder.getQuantity() + ";");
			} else {
				opsPurchaseorder.setInformation(
						today + finishPoListForDto.getOperator() + "强制完纳，初始采购数量为：" + opsPurchaseorder.getQuantity());
			}
			if (StringUtils.isNotBlank(finishPoListForDto.getFinishMsg())) {
				opsPurchaseorder.setInformation(
						opsPurchaseorder.getInformation() + ",原因为：" + finishPoListForDto.getFinishMsg() + ";");
			} else {
				opsPurchaseorder.setInformation(opsPurchaseorder.getInformation() + ";");
			}
			opsPurchaseorder.setOperator(finishPoListForDto.getOperator());
			opsPurchaseorder.setUpdatetime(new Date());
			opsPurchaseorder.setQuantity(finishPoListForDto.getFinishPoQty());
			// 对比数量，完纳数量=到货数量，则更新为已完成
			// bug12470 数量对比使用equles函数，当数量大于128时大于小于号在对比时失效
			if (CollectionUtils.isNotEmpty(opsPurchaseinvoices) && !opsPurchaseinvoices.isEmpty()
					&& transQty.equals(finishPoListForDto.getFinishPoQty()) && qtyReceive.equals(transQty)) {
				opsPurchaseorder.setStatecode(PurchaseOrderStatusEnum.YIWANCHENG);
				opsPurchaseorder.setFinishdate(new Date());
			}
			basePoService.updatePurchaseById(opsPurchaseorder);

			// 更新采购发票表
			if (CollectionUtils.isNotEmpty(opsPurchaseinvoices) && !opsPurchaseinvoices.isEmpty()) {
				opsPurchaseinvoices.get(0).setOperatorid(finishPoListForDto.getOperator());
				opsPurchaseinvoices.get(0).setUpdatetime(new Date());
				opsPurchaseinvoices.get(0).setQuantity(finishPoListForDto.getFinishPoQty());
				if (transQty.equals(finishPoListForDto.getFinishPoQty()) && qtyReceive.equals(transQty)) {
					opsPurchaseinvoices.get(0).setStatecode(PurchaseInvoiceStatusEnum.YIWANCHENG);
				}
				basePoService.updatePurchaseInvoiceById(opsPurchaseinvoices.get(0));
			}
			// if (opsPurchaseorder.getMergeflag()) {
			// 合并
			// bug13662 请购表增加采购单号字段，取消合并表
			List<OpsRequestpurchase> rList = basePoService.getRequestPurchaseByPurchase(
					finishPoListForDto.getpOrderNo(), finishPoListForDto.getpOrderItem(),
					finishPoListForDto.getpSplitNo());
			for (OpsRequestpurchase r : rList) {
				if (StringUtils.isNotBlank(r.getInformation())) {
					r.setInformation(r.getInformation() + ";" + today + finishPoListForDto.getOperator() + "强制完纳，初始数量为："
							+ qtyOri + ",原因为：" + finishPoListForDto.getFinishMsg() + ";");
				} else {
					r.setInformation(today + finishPoListForDto.getOperator() + "强制完纳，初始数量为：" + qtyOri + ",原因为："
							+ finishPoListForDto.getFinishMsg() + ";");
				}
				if (CollectionUtils.isNotEmpty(opsPurchaseinvoices) && !opsPurchaseinvoices.isEmpty()
						&& transQty.equals(finishPoListForDto.getFinishPoQty()) && qtyReceive.equals(transQty)) {
					r.setStatecode(RequestPurchaseStatusEnum.YIWANCHENG);
				}
				r.setUpdatetime(new Date());
				basePoService.updateRequestPurchaseById(r);
			}
			// } else {
			// OpsRequestpurchase r =
			// basePoService.getRequestPurchase(finishPoListForDto.getpOrderNo(),
			// finishPoListForDto.getpOrderItem(),
			// finishPoListForDto.getpSplitNo());
			// if (r != null) {
			// if (StringUtils.isNotBlank(r.getInformation())) {
			// r.setInformation(r.getInformation() + ";" + today +
			// finishPoListForDto.getOperator()
			// + "强制完纳，初始数量为：" + qtyOri + ",原因为：" +
			// finishPoListForDto.getFinishMsg() + ";");
			// } else {
			// r.setInformation(today + finishPoListForDto.getOperator() +
			// "强制完纳，初始数量为：" + qtyOri + ",原因为："
			// + finishPoListForDto.getFinishMsg() + ";");
			// }
			// if (CollectionUtils.isNotEmpty(opsPurchaseinvoices) &&
			// opsPurchaseinvoices.size() > 0
			// && transQty.equals(finishPoListForDto.getFinishPoQty())) {
			// r.setStatecode(RequestPurchaseStatusEnum.YIWANCHENG);
			// }
			// r.setUpdatetime(new Date());
			// basePoService.updateRequestPurchaseById(r);
			// }
			// }
		}
		return "";
	}

	// 写入采购事件记录表中
	public void opsPolog(FinishPoListForDto finishPoListForDto) throws OpsException {
		OpsPoEventLog opsPoEventLog = new OpsPoEventLog();
		opsPoEventLog.setPoType("采购完纳");
		opsPoEventLog.setParams(JSON.toJSONString(finishPoListForDto));
		opsPoEventLog.setOrderno(finishPoListForDto.getpOrderNo());
		opsPoEventLog.setItemno(finishPoListForDto.getpOrderItem());
		if (finishPoListForDto.getpSplitNo() != null) {
			opsPoEventLog.setSplititemno(finishPoListForDto.getpSplitNo());
		}
		opsPoEventLog.setCreateTime(new Date());
		opsPoEventLogMapper.insertSelective(opsPoEventLog);
	}

}
