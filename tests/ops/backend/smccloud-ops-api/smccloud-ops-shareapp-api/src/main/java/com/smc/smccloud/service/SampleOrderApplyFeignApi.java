package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.SmsSendOpsDetailTaskBean;
import com.smc.smccloud.model.sampleorder.*;
import com.smc.smccloud.service.hystrix.SampleOrderApplyFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 *  SampleOrderApplyFeignApi
 */
@FeignClient(name = "shareapp-service",
        contextId = "sampleorder",
        fallbackFactory = SampleOrderApplyFeignApiHystrix.class,
        configuration = AuthFeignAutoConfiguration.class)
public interface SampleOrderApplyFeignApi {


    /**
     *  生成样品数据接口
     *  样品数据主体和样品数据明细 一对多
     * @param sampleOrderDTO
     * @return
     */
    @RequestMapping(value = "/shareapp/sampleorder/addSampleOrder",method = RequestMethod.POST)
    ResultVo<List<SampleOrderReturnDTO>> addSampleOrder(@Valid @RequestBody SampleOrderDTO sampleOrderDTO);


//    /**
//     * 生成样品数据
//     *  注: 以下属性不可为空
//     *  applyNo,customerNo, applyDeptNo, itemNo, modelNo
//     */
//    @RequestMapping(value = "/shareapp/sampleorder/addSampleOrderByList",method = RequestMethod.POST)
//    ResultVo<String> addSampleOrderByList(@RequestBody List<SampleOrderVO> sampleOrderVOList);

    /**
     * 根据物流发货数据 自动生成结算数据
     * @return
     */
    @GetMapping(value = "/shareapp/sampleorder/autoGenerateSampleBalData")
    ResultVo<String> autoGenerateSampleBalData();

    /**
     * 状态为待处理的样品订单自动生成订单
     */
    @GetMapping("/shareapp/sampleorder/autoCreateOrerBySampleOrder")
    ResultVo<String> autoCreateOrerBySampleOrder();

    /**
     * 样品订单 自动结转(待结转->待转出)
     *
     */
    @GetMapping("/shareapp/sampleorder/autoOrderCarryTurn")
    ResultVo<String> autoOrderCarryTurn();

    /**
     * 执行样品待转出 自动写入成本 (正式后 月底或者月初执行一次)
     */
    @GetMapping("/shareapp/sampleorder/autoInsertSales")
    ResultVo<String> autoInsertSales();

    /**
     * 样品待转销售自动开票
     */
    @GetMapping("/shareapp/sampleorder/autoToSalesInvoice")
    ResultVo<String> autoToSalesInvoice();


    @PostMapping("/shareapp/sampleorder/insertSampleBalApply")
    ResultVo<String> insertSampleBalApply(@RequestBody SampleBalApplyVO info);

    @GetMapping("/findHandSampleBalApply")
    ResultVo<String> findHandSampleBalApply(@RequestBody FindHandSampleBalHandVO vo);

    @GetMapping("/checkRcvQty")
    ResultVo<String> checkRcvQty(@RequestBody CheckRcvQtyVO checkRcvQtyVO);

    @PostMapping("/shareapp/sampleorder/insertReturnOrder")
    ResultVo<String> insertReturnOrder(@RequestBody SmsSendOpsDetailTaskBean bean);

    @PostMapping("/shareapp/sampleorder/sureApplySampleBalApi")
    ResultVo<String> sureApplySampleBalApi(@RequestBody List<String> ids);

    @PostMapping("/shareapp/sampleorder/getSampleBalApplyInfo")
    ResultVo<SampleBalApplyVO> getSampleBalApplyInfo(@RequestBody SampleBalApplyVO info);

}
