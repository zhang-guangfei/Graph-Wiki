package com.sales.ops.serviceimpl.purchase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.batchdao.AddBatchRequestPurchaseDao;
import com.sales.ops.db.dao.OpsRequestpurchaseMapper;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsRequestpurchaseExample;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.service.purchase.PurchaseCreateService;

@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseCreateServiceImpl implements PurchaseCreateService {

	@Autowired
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;
	@Resource
	private AddBatchRequestPurchaseDao addBatchRequestPurchaseDao;

	/**
	 * @description 创建请购单，批量写入 暂时没有调用
	 */
	@Override
	public void addRequestPurchaseBatch(List<RequestPurchaseInfoDto> list) throws OpsException {
		List<OpsRequestpurchase> insert = new ArrayList<OpsRequestpurchase>();
		list.forEach(i -> {
			OpsRequestpurchase item = i.convertToEntity();
			// 判断是否存在
			OpsRequestpurchaseExample ex = new OpsRequestpurchaseExample();
			if (item.getSplititemno() == null)
				ex.createCriteria().andOrdernoEqualTo(item.getOrderno()).andItemnoEqualTo(item.getItemno())
						.andSplititemnoIsNull();
			else
				ex.createCriteria().andOrdernoEqualTo(item.getOrderno()).andItemnoEqualTo(item.getItemno())
						.andSplititemnoEqualTo(item.getSplititemno());
			List<OpsRequestpurchase> temp = opsRequestpurchaseMapper.selectByExample(ex);
			// 写入表
			if (temp == null || temp.size() == 0) {
				if (OrderTypeEnum.DR.equals(item.getOrdtype()) || OrderTypeEnum.CR.equals(item.getOrdtype())) {
					item.setSupplierid("JP");
				}
				item.setModelno(item.getModelno().trim());
				item.setStatecode(RequestPurchaseStatusEnum.DAICHULI);
				item.setUpdatetime(new Date());
				insert.add(item);
			}
		});

		// 批量写入
		for (int i = 0; i < insert.size(); i++) {
			if (i % 50 == 0) {
				List<OpsRequestpurchase> temp = new ArrayList<OpsRequestpurchase>();
				if (i + 50 < insert.size()) {
					temp = insert.subList(i, i + 50);
				} else {
					temp = insert.subList(i, insert.size());
				}
				addBatchRequestPurchaseDao.insertBatch(temp);
			}
		}
	}

	/**
	 * @description 创建请购单 WM模块调用
	 */
	@Override
	public void addRequestPurchase(List<RequestPurchaseInfoDto> list) throws OpsException {

		list.forEach(i -> {
			OpsRequestpurchase item = i.convertToEntity();
			// 判断是否存在
			OpsRequestpurchaseExample ex = new OpsRequestpurchaseExample();
			if (item.getSplititemno() == null)
				ex.createCriteria().andOrdernoEqualTo(item.getOrderno()).andItemnoEqualTo(item.getItemno())
						.andSplititemnoIsNull();
			else
				ex.createCriteria().andOrdernoEqualTo(item.getOrderno()).andItemnoEqualTo(item.getItemno())
						.andSplititemnoEqualTo(item.getSplititemno());
			List<OpsRequestpurchase> temp = opsRequestpurchaseMapper.selectByExample(ex);
			// 写入表
			if (temp == null || temp.size() == 0) {
				if (OrderTypeEnum.DR.equals(item.getOrdtype()) || OrderTypeEnum.CR.equals(item.getOrdtype())) {
					item.setSupplierid("JP");
				}
				item.setModelno(item.getModelno().trim());
				item.setStatecode(RequestPurchaseStatusEnum.DAICHULI);
				item.setUpdatetime(new Date());
				opsRequestpurchaseMapper.insertSelective(item);
			}
		});
	}

}
