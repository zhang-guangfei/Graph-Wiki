package com.sales.ops.feign.eta;

import com.sales.ops.dto.eta.ETAPageDto;
import com.sales.ops.dto.eta.FindETADataDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.config.EtaConfig;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/11/16 14:09
 */
@FeignClient(name = "dds-pre-order", contextId = "etaAction",configuration = EtaConfig.class)
public interface EtaFeign {

    //查询参考货期
    @RequestMapping(value = "/findEtaAPI", method = RequestMethod.POST)
    ResultVo<ETAPageDto> findEtaAPI(@RequestBody List<FindETADataDto> dataList);

    //查询参考货期
    @RequestMapping(value = "/findEta", method = RequestMethod.POST)
    CommonResult<ETAPageDto> findEta(@RequestBody List<FindETADataDto> dataList);

    // 定时任务 执行一次
    @RequestMapping(value = "/do", method = RequestMethod.GET)
    String doData(@RequestParam(required = false,name = "limit",defaultValue="1") Long limit,
                                       @RequestParam(required = false,name = "partition",defaultValue="A") String partition);

    // 定时任务 执行完成
    @RequestMapping(value = "/doAllTableData", method = RequestMethod.GET)
    String doAllTableData(@RequestParam(required = false,name = "limit",defaultValue="1") Long limit,
                  @RequestParam(required = false,name = "partition",defaultValue="A") String partition);
    // 定时任务清缓存
    @RequestMapping(value = "/cleanAllCache", method = RequestMethod.GET)
    String cleanAllCache();

    // 定时任务存库存缓存
    @RequestMapping(value = "/saveInvCache", method = RequestMethod.GET)
    String saveInvCache();

    // 定时任务存库存缓存
    @RequestMapping(value = "/cleanEveryDayCache", method = RequestMethod.GET)
    String cleanEveryDayCache();

    // 定时任务存同步通用库存 bugid:18776 c14717 20240826
    @RequestMapping(value = "/api/inventory/jobSynTyInv", method = RequestMethod.GET)
    ResultVo<String> jobSynTyInv();




}
