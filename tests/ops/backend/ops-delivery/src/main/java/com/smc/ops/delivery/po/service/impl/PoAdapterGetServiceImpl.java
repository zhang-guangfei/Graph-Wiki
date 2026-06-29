package com.smc.ops.delivery.po.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.ops.dto.poDeliver.ChangeFromSupplier;
import com.smc.ops.delivery.po.mapper.PoAdapterDao;
import com.smc.ops.delivery.po.service.PoAdapterGetService;

@Service
public class PoAdapterGetServiceImpl implements PoAdapterGetService {

	@Autowired
	private PoAdapterDao poAdapterDao;

	@Override
	public List<ChangeFromSupplier> getWaitOperateChange(long id, long endid) {
		return poAdapterDao.selectMainLog(id, endid);
	}

}
