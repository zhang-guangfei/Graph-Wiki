package com.smc.smccloud.service;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adjust.StockAdjustApplyDTO;
import com.smc.smccloud.model.adjust.StockAdjustDTO;
import com.smc.smccloud.model.adjust.StockAdjustRequest;
import com.smc.smccloud.model.adjust.StockAdjustVO;
import com.smc.smccloud.service.hystrix.StockAdjustServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "order-service",
        contextId = "stockadjust",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = StockAdjustServiceFeignHystrix.class)
public interface StockAdjustServiceFeignApi {

    /**
     * 新增入库调整数据
     * @param dto
     * @return
     */
    @RequestMapping(value = "/order/adjust/addAdjustData", method = RequestMethod.POST)
    ResultVo<String> addAdjustData(@RequestBody StockAdjustApplyDTO dto);

    /**
     * 查询入库调整数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/adjust/listAdjustData", method = RequestMethod.POST)
    ResultVo<PageInfo<StockAdjustVO>> listAdjustData(@RequestBody StockAdjustRequest request);

    /**
     * 导出入库调整数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/adjust/exportStockAdjustData", method = RequestMethod.POST)
    void exportStockAdjustData(@RequestBody StockAdjustRequest request);

    /**
     * 删除入库调整数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/order/adjust/delAdjustData", method = RequestMethod.GET)
    ResultVo<String> delAdjustData(@RequestParam(value = "id") Integer id);

    /**
     * 创建发票号
     * @return
     */
    @RequestMapping(value = "/order/adjust/createInvoiceNo", method = RequestMethod.GET)
    ResultVo<String> createInvoiceNo();

    /**
     * 根据订单获取采购信息
     * @param fullOrderNo
     * @return
     */
    @RequestMapping(value = "/order/adjust/getOrderInfoForImpAdjuest", method = RequestMethod.GET)
    ResultVo<StockAdjustDTO> getOrderInfoForImpAdjuest(@RequestParam("fullOrderNo") String fullOrderNo);

    @RequestMapping(value = "/order/adjust/importStockAdjustData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    ResultVo<String> importStockAdjustData(@RequestPart("file") MultipartFile file);

    /**
     * 进行入库调整 更改库存
     * @param invoiceNo
     * @return
     */
    @RequestMapping(value = "/order/adjust/determineStockAdjust", method = RequestMethod.GET)
    ResultVo<String> determineStockAdjust(@RequestParam("invoiceNo") String invoiceNo);

    /**
     * 检查3C产品并进行财务库存调整
     * @return
     */
    @RequestMapping(value = "/order/adjust/threeCProductAdjust", method = RequestMethod.GET)
    ResultVo<String> threeCProductAdjust();

    /**
     * 发票型号与入库型号不一致，生成组单数据
     * @return
     */
    @RequestMapping(value = "/order/adjust/createStockAssembleFromInvoiceError", method = RequestMethod.GET)
    ResultVo<String> createStockAssembleFromInvoiceError();
}
