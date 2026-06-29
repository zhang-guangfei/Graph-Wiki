package com.sales.ops.serviceimpl.po;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.ops.db.batchdao.ProductInspectionsGroupDao;
import com.sales.ops.dto.product.ProductRecordRefModelToSales;
import com.sales.ops.service.po.PoModelService;

@Service
public class PoModelServiceImpl implements PoModelService {

	@Autowired
	private ProductInspectionsGroupDao productInspectionsGroupDao;

	@Override
	public boolean judge3CModel(String modelNo) {
		ProductRecordRefModelToSales temp = productInspectionsGroupDao.selectByModelNo(modelNo);
		if (temp != null && StringUtils.equals("3C认证品", temp.getCategory())) {
			return true;
		}
		return false;
	}

}
