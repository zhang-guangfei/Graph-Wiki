package com.smc.ops.delivery.po.service;

import java.util.List;

import com.sales.ops.dto.poDeliver.ChangeFromSupplier;

public interface PoAdapterGetService {

	List<ChangeFromSupplier> getWaitOperateChange(long id, long endid);
}
