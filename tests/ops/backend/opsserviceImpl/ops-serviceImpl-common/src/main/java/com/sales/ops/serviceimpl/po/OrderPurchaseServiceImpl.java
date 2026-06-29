package com.sales.ops.serviceimpl.po;

import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.PoConfigDao;
import com.sales.ops.dto.po.core.PoCalCoreDto;
import com.sales.ops.dto.purchase.PurchaseDayDto;
import com.sales.ops.dto.purchase.PurchaseInvoiceDetailInfo;
import com.sales.ops.dto.purchase.PurchaseOrderDetailInfo;
import com.sales.ops.dto.purchase.PurchaseOrderDetailInfo.StateCode;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.PoReplyDateStrEnum;
import com.sales.ops.enums.PurchaseInvoiceStatusEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.po.OrderPurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class OrderPurchaseServiceImpl implements OrderPurchaseService {

	@Autowired
	private BasePoService basePoService;

	@Autowired
	private OpsRequestpurchaseCancelLogMapper opsRequestpurchaseCancelLogMapper;

	@Autowired
	private OpsPurchaseorderCancelLogMapper opsPurchaseorderCancelLogMapper;

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

	@Autowired
	private ImpInvoiceMasterMapper impInvoiceMasterMapper;

	@Autowired
	private SupplierMapper supplierMapper;

    @Autowired
    private PoConfigDao poConfigDao;
	@Autowired
	private TransferInfoMapper transferInfoMapper;

	@Override
	public PurchaseOrderDetailInfo getPurchaseInfo(String orderNo, Integer itemNo, Integer splitNo) {
		PurchaseOrderDetailInfo result = new PurchaseOrderDetailInfo();

		OpsRequestpurchase request = basePoService.getRequestPurchase(orderNo, itemNo, splitNo);
		if (request == null) {
			// 查询已删除数据
			OpsRequestpurchaseCancelLogExample e = new OpsRequestpurchaseCancelLogExample();
			if (splitNo == null) {
				e.createCriteria().andOrdernoEqualTo(orderNo).andItemnoEqualTo(itemNo).andSplititemnoIsNull();
			} else {
				e.createCriteria().andOrdernoEqualTo(orderNo).andItemnoEqualTo(itemNo).andSplititemnoEqualTo(splitNo);
			}
			e.setOrderByClause("insertTime desc");
			List<OpsRequestpurchaseCancelLog> temp = opsRequestpurchaseCancelLogMapper.selectByExample(e);
			if (CollectionUtils.isEmpty(temp)) {
				// 此请购单号不存在
				result.setStateCode(StateCode.OTHER);
			} else {
				// 已删除
				result.setPoOrderNo(orderNo);
				result.setPoItemNo(itemNo);
				result.setPoSplitItemNo(splitNo);
				result.setPoOrderFNo(splitNo == null ? orderNo + "-" + itemNo.toString()
						: orderNo + "-" + itemNo.toString() + "-" + splitNo.toString());
				result.setSupplierId(temp.get(0).getSupplierid());
				result.setExportDate(temp.get(0).getHopeexportdate());
				result.setTransType(temp.get(0).getTranstype());
				result.setStateCode(StateCode.DELETE);
			}
			return result;
		}

		// 请购拦截
		if (StringUtils.equals(request.getStatecode(), RequestPurchaseStatusEnum.LANJIE)
				|| StringUtils.equals(request.getStatecode(), RequestPurchaseStatusEnum.SHIKOMILANJIE)) {
			result.setSupplierId(request.getSupplierid());
			result.setStateCode(StateCode.INTERCEPT);
			result.setInterceptionReason(request.getInterceptmsg());
			return result;
		}
		if (request.getPoOrderNo() == null) {
			// 此采购单号不存在,请购处理状态
			result.setSupplierId(request.getSupplierid());
			result.setExportDate(request.getHopeexportdate());
			result.setTransType(request.getTranstype());
			result.setStateCode(StateCode.REQUEST);
			return result;
		}
		// bug13662 请购表新增三个采购单号，废除合并表
		OpsPurchaseorder purchase = basePoService.getPurchase(request.getPoOrderNo(), request.getPoItemNo(),
				request.getPoSplitNo());

		// 若采购单不存在
		if (purchase == null) {
			OpsPurchaseorderCancelLogExample e = new OpsPurchaseorderCancelLogExample();
			if (request.getPoSplitNo() == null) {
				e.createCriteria().andOrdernoEqualTo(request.getPoOrderNo()).andItemnoEqualTo(request.getPoItemNo())
						.andSplititemnoIsNull();
			} else {
				e.createCriteria().andOrdernoEqualTo(request.getPoOrderNo()).andItemnoEqualTo(request.getPoItemNo())
						.andSplititemnoEqualTo(request.getPoSplitNo());
			}
			e.setOrderByClause("insertTime desc");
			List<OpsPurchaseorderCancelLog> temp = opsPurchaseorderCancelLogMapper.selectByExample(e);
			if (CollectionUtils.isEmpty(temp)) {
				// 此采购单号不存在,请购处理状态
				result.setSupplierId(request.getSupplierid());
				result.setExportDate(request.getHopeexportdate());
				result.setTransType(request.getTranstype());
				result.setStateCode(StateCode.REQUEST);
			} else {
				// 已删除
				result.setSupplierId(temp.get(0).getSupplierid());
				result.setExportDate(temp.get(0).getHopeexportdate());
				result.setTransType(temp.get(0).getTranstype());
				result.setStateCode(StateCode.DELETE);
			}
			return result;
		}

		result.setPoOrderNo(request.getPoOrderNo());
		result.setPoItemNo(request.getPoItemNo());
		result.setPoSplitItemNo(request.getPoSplitNo());
		result.setPoOrderFNo(
				request.getPoSplitNo() == null ? request.getPoOrderNo() + "-" + request.getPoItemNo().toString()
						: request.getPoOrderNo() + "-" + request.getPoItemNo().toString() + "-"
								+ request.getPoSplitNo().toString());
		result.setSupplierId(purchase.getSupplierid());
		result.setExportDate(purchase.getHopeexportdate());
		result.setTransType(purchase.getTranstype());
		result.setSendDate(purchase.getPurchasedate());
		if (StringUtils.equals(purchase.getStatecode(), PurchaseOrderStatusEnum.DAICHULI)) {
			result.setStateCode(StateCode.REQUEST);
			return result;
		}
		OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
		if (request.getPoSplitNo() == null || request.getPoSplitNo() == 0) {
			opsPurchaseinvoiceExample.createCriteria().andOrdernoEqualTo(request.getPoOrderNo())
					.andItemnoEqualTo(request.getPoItemNo()).andSplititemnoIsNull();
		} else {
			opsPurchaseinvoiceExample.createCriteria().andOrdernoEqualTo(request.getPoOrderNo())
					.andItemnoEqualTo(request.getPoItemNo()).andSplititemnoEqualTo(request.getPoSplitNo());
		}
		List<OpsPurchaseinvoice> l = opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
		if (CollectionUtils.isNotEmpty(l)) {
			OpsPurchaseinvoice invoice = l.get(0);
			result.setStateCode(StateCode.SEND);
			// 已接单
			if (StringUtils.isNotBlank(invoice.getReplyorderno()) || invoice.getReplyorderdate() != null) {
				result.setStateCode(StateCode.RECEIVE);
				// bug14362增加供应商接单号及截单日期的返回
				if (StringUtils.isNotBlank(invoice.getReplyorderno()))
					result.setSupplierOrderNo(invoice.getReplyorderno());
				if (invoice.getReplyorderdate() != null)
					result.setSupplierOrderDate(invoice.getReplyorderdate());
			}

			// 返信
			if (invoice.getReplyexportdate() != null) {
				result.setReplyExportDate(invoice.getReplyexportdate());
				result.setStateCode(StateCode.RECEIVE);
			}

			// bug14645返回实际运输方式
			if (StringUtils.isNotBlank(invoice.getTranstypeFact())) {
				result.setFacTransType(invoice.getTranstypeFact());
			}

			// bug14386原始返信
			if (StringUtils.isNotBlank(invoice.getSrcDeliveryTime())) {
				result.setSrcdeliverytime(invoice.getSrcDeliveryTime());
				for (PoReplyDateStrEnum i : PoReplyDateStrEnum.values()) {
					if (StringUtils.equals(i.getCode(), invoice.getSrcDeliveryTime())) {
						result.setReplyExportDate(null);
						result.setSrcdeliverytimedesc(i.getCodeName());
						break;
					}
				}
				// bug14389 不确定日期的返信解析
				if (StringUtils.isBlank(result.getSrcdeliverytimedesc())
						&& invoice.getSrcDeliveryTime().endsWith("99")) {
					result.setReplyExportDate(null);
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					result.setSrcdeliverytimedesc("最晚" + formatter.format(invoice.getReplyexportdate()));
				}
			} else if (invoice.getReplyexportdate() != null) {
				// bug15104 若为老订单无src字段时，也对返信进行不确定返信解析
				String temp = PoReplyDateStrEnum.getDescByCode(invoice.getReplyexportdate()).getCode();
				if (StringUtils.isNotBlank(temp)) {
					for (PoReplyDateStrEnum i : PoReplyDateStrEnum.values()) {
						if (StringUtils.equals(i.getCode(), temp)) {
							result.setReplyExportDate(null);
							result.setSrcdeliverytimedesc(i.getCodeName());
							break;
						}
					}
					if (StringUtils.isBlank(result.getSrcdeliverytimedesc()) && temp.endsWith("99")) {
						result.setReplyExportDate(null);
						result.setSrcdeliverytimedesc("最晚" + temp.substring(0, temp.length() - 2)
								+ DateUtil.dateToString(invoice.getReplyexportdate()).substring(8, 10));
					}
				}
			}

			// 出库区分
			if (StringUtils.isNotBlank(invoice.getProducefactory())) {
				result.setStockType(invoice.getProducefactory());
				result.setStateCode(StateCode.RECEIVE);
			}
			// 生产HOLON
			if (StringUtils.isNotBlank(invoice.getProduceholon())) {
				result.setHolon(invoice.getProduceholon());
				result.setStateCode(StateCode.PRODUCT);
			}
			// 实际生产日
			if (invoice.getBeginproducedate() != null) {
				result.setFacProdDate(invoice.getBeginproducedate());
				result.setStateCode(StateCode.PRODUCT);
			}
			// 实际离厂日
			// bug14400
			// 如果采购单分纳，且是第二次及以上来货事，不再返回实际离厂日：根据qtyrecive判断，如有值且小于采购数量则不返回实际离厂日
			if (invoice.getFacexpdate() != null
					&& (invoice.getQtyreceive() == null || (invoice.getQtyreceive() >= invoice.getQuantity()))) {
				result.setFacExpDate(invoice.getFacexpdate());
				result.setStateCode(StateCode.TRANSIT);
			}
			// 运输中
			if (StringUtils.isNotBlank(invoice.getInvoiceno())) {
				result.setStateCode(StateCode.TRANSIT);
			}
			// 已完成
			if (StringUtils.equals(PurchaseInvoiceStatusEnum.YIWANCHENG, invoice.getStatecode())) {
				result.setStateCode(StateCode.FINISH);
			}
			return result;
		} else {
			// 没有invoice表数据，没有完全发送成功或者没有删除成功，非正常数据
			result.setStateCode(StateCode.OTHER);
			return result;
		}

	}
// 1签收 2 发货 3.收货
	@Override
	public PurchaseInvoiceDetailInfo getInvoiceInfo(String invoiceNo, Long invoiceId) {
		PurchaseInvoiceDetailInfo info = new PurchaseInvoiceDetailInfo();
		info.setInvoiceId(invoiceId);
		info.setInvoiceNo(invoiceNo);
		ImpInvoiceMasterExample example = new ImpInvoiceMasterExample();
		example.createCriteria().andInvoiceNoEqualTo(invoiceNo).andIdEqualTo(invoiceId);
		List<ImpInvoiceMaster> list = impInvoiceMasterMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			info.setStateCode(PurchaseInvoiceDetailInfo.StateCode.OTHER);
			return info;
		}
		TransferInfoExample example1 = new TransferInfoExample();
		example1.createCriteria().andInvoiceNoEqualTo(invoiceNo).andInvoiceIdEqualTo(invoiceId);
		List<TransferInfo> transferInfos = transferInfoMapper.selectByExample(example1);
		if (CollectionUtils.isNotEmpty(transferInfos)) {
			PurchaseOrderDetailInfo.TransferLogisticsStatus statusEnum = PurchaseOrderDetailInfo.TransferLogisticsStatus.parseByCode(transferInfos.get(0).getLogisticsStatus());
			if (statusEnum != null) {
				info.setTransferLogisticsStatus(statusEnum.getDesc());
			}
		}
		info.setSupplierId(list.get(0).getSupplierCode());
		info.setStateCode(PurchaseInvoiceDetailInfo.StateCode.DELIVERY);
		// 发运日期
		if (list.get(0).getShipDate() != null) {
			info.setShipDate(list.get(0).getShipDate());
		}
		// 预计到货仓
		if (StringUtils.isNotBlank(list.get(0).getArrivedWarehouseCode())) {
			info.setWarehouseCode(list.get(0).getArrivedWarehouseCode());
		}
		// 预计到货期
		if (list.get(0).getPrearriveDate() != null) {
			info.setEsArrivalDate(list.get(0).getPrearriveDate());
		}
		// 到场日期
		if (list.get(0).getReceiveDate() != null) {
			info.setReceiveDate(list.get(0).getReceiveDate());
		}
		// 到港日
		if (list.get(0).getPortArrivedate() != null) {
			info.setFacProtDate(list.get(0).getPortArrivedate());
			info.setStateCode(PurchaseInvoiceDetailInfo.StateCode.CUSTOMS);
		}
		// 报关日期
		if (list.get(0).getCustomsDate() != null) {
			info.setCustomsDate(list.get(0).getCustomsDate());
			info.setStateCode(PurchaseInvoiceDetailInfo.StateCode.CUSTOMS);
		}
		// 收货仓
		if (StringUtils.isNotBlank(list.get(0).getReceiveWarehouseCode())) {
			info.setWarehouseCode(list.get(0).getReceiveWarehouseCode());
			// info.setStateCode(PurchaseInvoiceDetailInfo.StateCode.ARRIVE);
		}
		// 已到货
		if (list.get(0).getConfirmDate() != null || list.get(0).getConfirmDate() != null) {
			info.setStateCode(PurchaseInvoiceDetailInfo.StateCode.ARRIVE);
		}
		return info;
	}

	@Override
	public PurchaseDayDto getPurchaseDay(String orderNo, Integer itemNo, Integer splitNo) {
		PurchaseDayDto result = new PurchaseDayDto();
		OpsRequestpurchase request = basePoService.getRequestPurchase(orderNo, itemNo, splitNo);
		if (request == null) {
			return null;
		} else {
			if (request.getPoOrderNo() == null) {
				if (StringUtils.isBlank(request.getSupplierid())) {
					result.setExistSupplier(false);
					return result;
				}
				Supplier supplier = supplierMapper.selectByPrimaryKey(request.getSupplierid());
				result.setProduceDay(supplier.getStddeliveryday());
				result.setProduceWorkDay(true);
                List<PoCalCoreDto> transDays = poConfigDao.getTransDays(request.getPurchasewarehouseid(), request.getSupplierid(), request.getTranstype());
                if (CollectionUtils.isNotEmpty(transDays)) {
					Integer trans = transDays.stream().min(Comparator.comparingInt(PoCalCoreDto::getTransDay)).get()
							.getTransDay();
					result.setTransDay(trans);
					result.setTransWorkDay(false);
				}

			} else {
				List<OpsPurchaseinvoice> purchase = basePoService.getPurchaseInvoicesByNo(request.getPoOrderNo(),
						request.getPoItemNo(), request.getPoSplitNo());
				if (CollectionUtils.isEmpty(purchase)) {
					return null;
				} else {
					Supplier supplier = supplierMapper.selectByPrimaryKey(purchase.get(0).getSupplierid());
					result.setProduceDay(supplier.getStddeliveryday());
					result.setProduceWorkDay(true);
					//bugid:18508 c14717 20250813
					if(StringUtils.isEmpty(purchase.get(0).getTranstypeFact())
							&& StringUtils.isEmpty(purchase.get(0).getTranstypemod())
							&& StringUtils.isEmpty(purchase.get(0).getTranstype())){
						log.warn("运输方式获取失败");
						return null;
					}
					// bug14437根据实际运输方式获取运输天数
                    List<PoCalCoreDto> transDays = poConfigDao.getTransDays(purchase.get(0).getReceivewarehouseid()
                            ,purchase.get(0).getSupplierid(), StringUtils.isNotBlank(purchase.get(0).getTranstypeFact())
                                    ? purchase.get(0).getTranstypeFact()
                                    : StringUtils.isNotBlank(purchase.get(0).getTranstypemod())
                                    ? purchase.get(0).getTranstypemod() : purchase.get(0).getTranstype());

                    if (CollectionUtils.isNotEmpty(transDays)) {
						Integer trans = transDays.stream().min(Comparator.comparingInt(PoCalCoreDto::getTransDay)).get()
								.getTransDay();
						result.setTransDay(trans);
						result.setTransWorkDay(false);
					}
				}
			}
			return result;
		}

	}

}
