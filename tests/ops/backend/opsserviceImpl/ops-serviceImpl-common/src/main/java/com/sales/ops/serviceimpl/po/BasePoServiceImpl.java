package com.sales.ops.serviceimpl.po;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sales.ops.db.dao.OpsPurchaseinvoiceMapper;
import com.sales.ops.db.dao.OpsPurchaseorderCancelLogMapper;
import com.sales.ops.db.dao.OpsPurchaseorderMapper;
import com.sales.ops.db.dao.OpsRequestpurchaseCancelLogMapper;
import com.sales.ops.db.dao.OpsRequestpurchaseMapper;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseinvoiceExample;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsPurchaseorderCancelLog;
import com.sales.ops.db.entity.OpsPurchaseorderExample;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsRequestpurchaseCancelLog;
import com.sales.ops.db.entity.OpsRequestpurchaseExample;
import com.sales.ops.db.extdao.PurchaseDeleteDao;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.PurchaseTypeEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.service.po.BasePoService;

/**
 * @author C12961
 * @date 2023/2/16 18:28
 */
@Service
public class BasePoServiceImpl implements BasePoService {

	@Autowired
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

	@Autowired
	private OpsPurchaseorderMapper opsPurchaseorderMapper;

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

	@Autowired
	private PurchaseDeleteDao purchaseDeleteDao;

	@Autowired
	private OpsPurchaseorderCancelLogMapper opsPurchaseorderCancelLogMapper;

	@Autowired
	private OpsRequestpurchaseCancelLogMapper opsRequestpurchaseCancelLogMapper;

	// 判断是否为补库订单
	@Override
	public boolean isSupplyType(String type) {
		if (type == null) {
			return false;
		}
		List<String> filter = Arrays.asList(OrderTypeEnum.BIN, OrderTypeEnum.PRE, OrderTypeEnum.WT);
		return filter.contains(type);
	}

	// 判断是否为补库订单
	@Override
	public boolean isSalesType(String type) {
		if (type == null) {
			return false;
		}
		List<String> filter = Arrays.asList(OrderTypeEnum.KEHU, OrderTypeEnum.YANGPIN, OrderTypeEnum.YIBANMAOYI);
		return filter.contains(type);
	}

	@Override
	public boolean is_CR_DR_Type(String type) {
		if (type == null) {
			return false;
		}
		List<String> filter = Arrays.asList(OrderTypeEnum.CR, OrderTypeEnum.DR);
		return filter.contains(type);
	}

	@Override
	public boolean isSalesPurchaseType(String type) {
		if (type == null) {
			return false;
		}
		String typeCode = PurchaseTypeEnum.SALE.getTypeCode();
		return StringUtils.equals(type, typeCode);
	}

	@Override
	public boolean isSupplyPurchaseType(String type) {
		if (type == null) {
			return false;
		}
		List<String> list = Arrays.asList(PurchaseTypeEnum.BIN.getTypeCode(), PurchaseTypeEnum.PRE.getTypeCode());
		return list.contains(type);
	}

	@Override
	public boolean isDifferentWarehouse(OpsRequestpurchase request) {
		return !StringUtils.equals(request.getRequestwarehouseid(), request.getPurchasewarehouseid());
	}

	@Override
	public OpsPurchaseorder getPurchase(Long id) {
		return opsPurchaseorderMapper.selectByPrimaryKey(id);
	}

	@Override
	public OpsRequestpurchase getRequestPurchase(Long id) {
		return opsRequestpurchaseMapper.selectByPrimaryKey(id);
	}

	/**
	 * @description 查询采购单, 输入采购单
	 * @author C12961
	 * @date 2023/3/11 13:32
	 */
	@Override
	public OpsPurchaseorder getPurchase(String poNo, Integer itemNo, Integer splitNo) {
		if (StringUtils.isBlank(poNo)) {
			return null;
		}
		OpsPurchaseorderExample example = new OpsPurchaseorderExample();
		OpsPurchaseorderExample.Criteria criteria = example.createCriteria().andOrdernoEqualTo(poNo)
				.andItemnoEqualTo(itemNo);
		if (splitNo != null && splitNo != 0) {
			criteria.andSplititemnoEqualTo(splitNo);
		} else {
			criteria.andSplititemnoIsNull();
		}
		List<OpsPurchaseorder> opsPurchaseOrderList = opsPurchaseorderMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(opsPurchaseOrderList)) {
			return null;
		}
		return opsPurchaseOrderList.get(0);
	}

	/**
	 * @description 查询请购单，输入请购单号
	 * @author C12961
	 * @date 2023/3/11 13:33
	 */
	@Override
	public OpsRequestpurchase getRequestPurchase(String repoNo, Integer itemNo, Integer splitNo) {
		OpsRequestpurchaseExample ex = new OpsRequestpurchaseExample();
		OpsRequestpurchaseExample.Criteria criteria = ex.createCriteria();
		criteria.andOrdernoEqualTo(repoNo).andItemnoEqualTo(itemNo);
		if (splitNo != null && splitNo != 0) {
			criteria.andSplititemnoEqualTo(splitNo);
		} else {
			criteria.andSplititemnoIsNull();
		}
		List<OpsRequestpurchase> opsRequestPurchaseList = opsRequestpurchaseMapper.selectByExample(ex);
		if (CollectionUtils.isEmpty(opsRequestPurchaseList)) {
			return null;
		}
		return opsRequestPurchaseList.get(0);

	}

	/**
	 * @description 查询请购单，输入采购单号，自动判断是否
	 * @author C12961
	 * @date 2023/3/11 13:33
	 */
	@Override
	public List<OpsRequestpurchase> getRequestPurchaseByPurchase(String poNo, Integer itemNo, Integer splitNo) {
		// bug13662 根据请购表中的采购单号查询请购单
		OpsRequestpurchaseExample ex = new OpsRequestpurchaseExample();
		if (splitNo != null && splitNo != 0) {
			ex.createCriteria().andPoOrderNoEqualTo(poNo).andPoItemNoEqualTo(itemNo).andPoSplitNoEqualTo(splitNo);
		} else {
			ex.createCriteria().andPoOrderNoEqualTo(poNo).andPoItemNoEqualTo(itemNo).andPoSplitNoIsNull();
		}
		List<OpsRequestpurchase> opsRequestPurchaseList = opsRequestpurchaseMapper.selectByExample(ex);
		if (CollectionUtils.isEmpty(opsRequestPurchaseList)) {
			return null;
		}
		return opsRequestPurchaseList;
	}

	/**
	 * @description 查询采购单，输入请购单号
	 * @author C12961
	 * @date 2023/3/21 11:09
	 */
	@Override
	public OpsPurchaseorder getPurchaseByRequestPurchase(String poNo, Integer itemNo, Integer splitNo) {
		OpsRequestpurchase request = getRequestPurchase(poNo, itemNo, splitNo);
		if (StringUtils.isBlank(request.getPoOrderNo())) {
			return null;
		}
		// bug13662 请购新增三个采购单号字段，废除合并表
		OpsPurchaseorder purchase = getPurchase(request.getPoOrderNo(), request.getPoItemNo(), request.getPoSplitNo());
		return purchase;
	}

	@Override
	public List<OpsRequestpurchase> getRequestPurchasesByRcvNo(String orderNo, int itemNo) {
		OpsRequestpurchaseExample ex = new OpsRequestpurchaseExample();
		ex.createCriteria().andOrdernoEqualTo(orderNo).andItemnoEqualTo(itemNo);
		return opsRequestpurchaseMapper.selectByExample(ex);
	}

	// @Override
	// @Transactional
	// public void deleteReqPoMapping(OpsPurchaseorder purchase,
	// OpsRequestpurchase request) {
	// OpsReqPoMappingKey key = new OpsReqPoMappingKey();
	// key.setPurchaseorderno(purchase.getOrderno());
	// key.setRequestpurchaseid(request.getId());
	// opsReqPoMappingMapper.deleteByPrimaryKey(key);
	// }

	@Override
	@Transactional
	public void deletePurchase(OpsPurchaseorder purchase, String operator) {
		if (StringUtils.isNotBlank(operator)) {
			purchase.setOperator(operator);
		}
		deletePurchase(purchase);
	}

	@Override
	@Transactional
	public void deletePurchase(OpsPurchaseorder purchase) {
		purchaseDeleteDao.insertPurchase(purchase.getId(), PurchaseOrderStatusEnum.SHANCHU, purchase.getOperator());
		opsPurchaseorderMapper.deleteByPrimaryKey(purchase.getId());
	}

	@Override
	@Transactional
	public void deletePurchaseByCancelLog(OpsPurchaseorderCancelLog opsPurchaseorderCancelLog) {
		opsPurchaseorderCancelLogMapper.insertSelective(opsPurchaseorderCancelLog);
		opsPurchaseorderMapper.deleteByPrimaryKey(opsPurchaseorderCancelLog.getId());
	}

	@Override
	@Transactional
	public void deleteRequestPurchase(OpsRequestpurchase request, String operator) {
		// bug10483 采购删单增加操作人 B91717
		if (StringUtils.isNotBlank(operator)) {
			request.setOperator(operator);
		}
		deleteRequestPurchase(request);
	}

	@Override
	@Transactional
	public void deleteRequestPurchase(OpsRequestpurchase request) {
		purchaseDeleteDao.insertRequest(request.getId(), RequestPurchaseStatusEnum.QUXIAO, request.getOperator());
		opsRequestpurchaseMapper.deleteByPrimaryKey(request.getId());
	}

	@Override
	@Transactional
	public void deleteRequestPurchaseByCancelLog(OpsRequestpurchaseCancelLog opsRequestpurchaseCancelLog) {
		opsRequestpurchaseCancelLogMapper.insertSelective(opsRequestpurchaseCancelLog);
		opsRequestpurchaseMapper.deleteByPrimaryKey(opsRequestpurchaseCancelLog.getId());
	}

	@Override
	public List<OpsPurchaseinvoice> getPurchaseInvoices(OpsPurchaseorder purchase) {
		OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
		OpsPurchaseinvoiceExample.Criteria criteria = opsPurchaseinvoiceExample.createCriteria();
		criteria.andOrdernoEqualTo(purchase.getOrderno()).andItemnoEqualTo(purchase.getItemno());
		if (purchase.getSplititemno() != null) {
			criteria.andSplititemnoEqualTo(purchase.getSplititemno());
		} else {
			criteria.andSplititemnoIsNull();
		}
		return opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
	}

	@Override
	public List<OpsPurchaseinvoice> getPurchaseInvoicesByNo(String poNo, Integer itemNo, Integer splitNo) {
		OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
		OpsPurchaseinvoiceExample.Criteria criteria = opsPurchaseinvoiceExample.createCriteria();
		criteria.andOrdernoEqualTo(poNo).andItemnoEqualTo(itemNo);
		if (splitNo != null && splitNo != 0) {
			criteria.andSplititemnoEqualTo(splitNo);
		} else {
			criteria.andSplititemnoIsNull();
		}
		return opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
	}

	@Override
	public int updateRequestPurchaseById(OpsRequestpurchase request) {
		return opsRequestpurchaseMapper.updateByPrimaryKeySelective(request);
	}

	@Override
	public int updatePurchaseById(OpsPurchaseorder purchase) {
		return opsPurchaseorderMapper.updateByPrimaryKeySelective(purchase);
	}

	@Override
	public int updatePurchaseInvoiceById(OpsPurchaseinvoice purchase) {
		return opsPurchaseinvoiceMapper.updateByPrimaryKeySelective(purchase);
	}
}
