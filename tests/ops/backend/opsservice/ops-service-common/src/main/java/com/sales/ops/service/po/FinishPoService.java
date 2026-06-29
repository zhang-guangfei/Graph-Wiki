package com.sales.ops.service.po;

import java.util.List;

import com.sales.ops.dto.order.FinishPoDto;
import com.sales.ops.dto.order.FinishPoListForDto;

public interface FinishPoService {

	List<FinishPoListForDto> getPoListByPoNo(List<FinishPoDto> info);
}
