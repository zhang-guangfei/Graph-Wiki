package com.sales.ops.feign;

import com.sales.ops.db.entity.OpsPoUnusualOrderLog;
import com.sales.ops.dto.ips.IpsSendDeliveryInfoToOpsDto;
import com.sales.ops.dto.poDeliver.ChangeFromSupplier;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@FeignClient(name = "delivery-server", contextId = "DeliveryPo")
public interface DeliveryPoFeignApi {

	@RequestMapping("/do/po/poadapter/getWaitOperateChange")
	public CommonResult<List<ChangeFromSupplier>> getWaitOperateChange(@RequestParam("id") long id,
			@RequestParam("endid") long endid);


	@RequestMapping("/do/po/unusual/getLogs")
	CommonResult<List<OpsPoUnusualOrderLog>> getUnusualOrderLogs(@RequestParam Integer num, @RequestParam Long maxId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate);

	@RequestMapping("/do/po/unusual/getLog/{id}")
	CommonResult<OpsPoUnusualOrderLog> getUnusualLogsById(@PathVariable Long id);

	@RequestMapping("/do/read/psi/queryIpsSendDeliveryInfoToOpsList")
	ResultVo<List<IpsSendDeliveryInfoToOpsDto>> queryIpsSendDeliveryInfoToOpsList();
}
