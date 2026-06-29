package com.sales.ops.service.deliver;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.poDeliver.PoDeliverSelectDto;
import com.sales.ops.dto.poDeliver.SelectFilter;
import com.sales.ops.dto.util.PageModel;

public interface PoDeliverSelectService {

	PageInfo<PoDeliverSelectDto> selectDeliverInfo(PageModel<SelectFilter> info) throws OpsException;

	// 获取当前页详细数据
	List<PoDeliverSelectDto> selectDetailInfo(List<PoDeliverSelectDto> list);
}
