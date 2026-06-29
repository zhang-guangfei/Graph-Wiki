package com.smc.ops.delivery.controller;

import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.service.lowfreq.LowFreqInterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/9 11:52
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/inv/lowFreq")
@Slf4j
public class LowFreqInterController {


    @Resource
    private LowFreqInterService lowFreqInterService;


    /**
     * bugid: 14635 顾客在库低频型号的自动获取及订单 拦截
     * @return
     */
    @RequestMapping("/last8")
    public CommonResult<String> lastEightMonthsLowFreq(){
        try {
            //1. 查询 库存表 条件：1.有效在库大于0 ；2. 非委托在库 3. 'GK-TY','GK-PJ','GK-PPL'
            //2.通过型号和StockCode='ALL' AND AvgQtyOf8 = 0 AND ModelNo ='' model_exp_freq 型号带* 转换为 (.*)
            StringBuffer sucessMsg = new StringBuffer();
            List<String> interceptModelNos = lowFreqInterService.getInterceptModelNos();
            sucessMsg.append("近8月清单size:");
            sucessMsg.append(interceptModelNos.size());
            if(CollectionUtils.isNotEmpty(interceptModelNos)){
                //3.写入低频拦截清单
                Integer integer = lowFreqInterService.insertInterceptData(interceptModelNos);
                sucessMsg.append(";写入低频订单size:");
                sucessMsg.append(integer);
                //4. a.批量更新po拦截表为不可用； b.插入或更新 ops_requestPurchase_intercept_config
                Integer integerPo = lowFreqInterService.updateInterceptDatePo(interceptModelNos);
                sucessMsg.append(";写入或更新采购拦截表size:");
                sucessMsg.append(integerPo);
            }
            return CommonResult.success(sucessMsg.toString());
        } catch (Exception e) {
            log.error("近八月低频型号计算",e);
            return CommonResult.failure();
        }
    }
}
