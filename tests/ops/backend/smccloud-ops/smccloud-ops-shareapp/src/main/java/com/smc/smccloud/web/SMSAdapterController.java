package com.smc.smccloud.web;

//import com.smc.smccloud.annotation.SysLog;
import com.smc.smccloud.log.annotation.SysLog;
import com.smc.smccloud.core.annotation.NoNeedAccessAuthentication;
import com.smc.smccloud.model.adapter.BinDataApply;
import com.smc.smccloud.model.adapter.ChinaRegionWarehouseSupplyApply;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.KettleJobManageService;
import com.smc.smccloud.service.SMSAdapterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-03-09 17:05
 * Description:
 */
@RestController
@RequestMapping(value = "/shareapp/SMSAdapter")
public class SMSAdapterController {

    @Resource
    private SMSAdapterService smsAdapterService;

    @Resource
    private KettleJobManageService kettleJobManageService;

    /**
     * 获取在库补库申请信息
     */
    @RequestMapping(value = "/advanceInfo/findBinApply", method = RequestMethod.GET)
    public ResultVo<BinDataApply> findBinApply(@RequestParam("no") String no) {
        return smsAdapterService.findBinApply(no);
    }

    /**
     * 根据申请号查询分库补库申请信息
     */
    @RequestMapping(value = "/advanceInfo/findChinaRegionWarehouse", method = RequestMethod.GET)
    public ResultVo<ChinaRegionWarehouseSupplyApply> findChinaRegionWarehouse(@RequestParam("no") String no) {
        return smsAdapterService.findChinaRegionWarehouse(no);
    }

    /**
     * mdm通知任务接口
     */
    @PostMapping(value = "/mdm/mdmNoticeWithOpsBaseData")
    public ResultVo<String> mdmNoticeWithOpsBaseData(@RequestBody String jsonStr) {
        return kettleJobManageService.mdmNoticeWithOpsBaseData(jsonStr);
    }

    /**
     * test
     */
    @GetMapping(value = "/mdm/testSaveServiceLog")
    public void testSaveServiceLog(@RequestParam("str") String str) {
        kettleJobManageService.testSaveLog(str);
    }

}
