package com.sales.ops.service.inqb;

import com.sales.ops.dto.inqb.OpsInqbInfo;
import com.sales.ops.dto.inqb.OpsInqbUsageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

/**
 * INQB模块公共服务接口
 * 提供INQB模块的查询及更新等相关操作
 */
public interface InqbCommonService {

	/**
	 * 1.获取INQB申请信息明细
	 *	根据提供的INQB申请号，获取INQB申请信息明细
	 */
	ResultVo<OpsInqbInfo> getInqbApplyInfo(String applyNo);


	/**
	 * 2.根据传入信息，更新INQ-B表的使用状态，并写入使用数量。
	 *
	 */
	ResultVo<String> updateInqbUsageInfo(OpsInqbUsageInfo opsInqbUsageInfo);

}
