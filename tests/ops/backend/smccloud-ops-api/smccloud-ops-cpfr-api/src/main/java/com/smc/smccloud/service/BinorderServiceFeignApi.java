package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.hystrix.BinOrderServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/10/25 10:55
 */
@FeignClient(name = "cpfr-service",
        contextId = "cpfr-binorder",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = BinOrderServiceFeignHystrix.class)

public interface BinorderServiceFeignApi {

    /**
     * 查询BIN销售
     * @param dto
     * @return
     */
    @RequestMapping(value = "/cpfr/binorder/listModelExpFreq", method = RequestMethod.POST)
    ResultVo<PageInfo<ModelExpFreqVO>> listModelExpFreq(@RequestBody ModelExpFreqRequest dto);

    /**
     * 查询BIN销售明细
     * @param dto
     * @return
     */
    @RequestMapping(value = "/cpfr/binorder/listModeldetail", method = RequestMethod.POST)
    ResultVo<List<ModelExpDetailVO>> listModeldetail(@RequestBody ModelExpFreqRequest dto);

    /**
     * 查询BIN销售明细
     * @param dto
     * @return
     */
    @RequestMapping(value = "/cpfr/binorder/listModeldetailByJob", method = RequestMethod.POST)
    ResultVo<List<ModelExpDetailVO>> listModeldetailByJob(@RequestBody ModelExpFreqRequest dto);

    /**
     * 查询型号的每个库房的在库
     * @param modelNo
     * @return
     */
    @RequestMapping(value = "/cpfr/binorder/listBinWarehouseStock", method = RequestMethod.POST)
    ResultVo<List<BinOrderInventoryInfoVO>> listBinWarehouseStock(@RequestParam("modelNo") String modelNo);

    /**
     * 定时计算型号订货频率
     */
    @RequestMapping(value = "/cpfr/binorder/calcmodelExpFreq", method = RequestMethod.POST)
    ResultVo<String> calcmodelExpFreq(@RequestParam("type") int type);

    /**
     * 获取上次采购交货期
     */
    @RequestMapping(value = "/cpfr/binorder/getLastPurchaseDlvDate", method = RequestMethod.GET)
    Date getLastPurchaseDlvDate(@RequestParam("modelNo") String modelNo,@RequestParam("warehouseCode") String warehouseCode);

    @RequestMapping(value = "/cpfr/binorder/runBinTrialJob", method = RequestMethod.POST)
    ResultVo<String> runBinTrialJob();

    @RequestMapping(value = "/cpfr/common/getmodelexpfreqforavgqty", method = RequestMethod.POST)
    ResultVo<List<ModelExpFreqVO>> getModelExpFreqForAvgQty(@RequestParam("modelNoS") List<String> modelNoS,
                                                            @RequestParam("warehouseCode") String warehouseCode,
                                                            @RequestParam("month") Integer month);

    @RequestMapping(value = "/cpfr/binorder/newBinOrderCalcId/client", method = RequestMethod.POST)
    ResultVo<BinOrderCalcVO> newBinOrderCalcId(@RequestBody BinOrderCalcRequestVO dto);

    @RequestMapping(value = "/cpfr/binorder/calcBinOrder/client", method = RequestMethod.POST)
    ResultVo<BinOrderCalcVO> calcBinOrder(@RequestBody BinOrderCalcRequestVO dto);


    @RequestMapping(value = "/cpfr/binorder/calcBinOrder/finish", method = RequestMethod.POST)
    ResultVo<String> finishbinordercalc(@RequestParam("id") Long id);

    @RequestMapping(value = "/cpfr/binorder/listByWarehouseCode", method = RequestMethod.POST)
    ResultVo<List<BinOrderCalcVO>> findBinOrderCalcByWarehouseCode(@RequestParam("warehouseCode") String warehouseCode);
}
