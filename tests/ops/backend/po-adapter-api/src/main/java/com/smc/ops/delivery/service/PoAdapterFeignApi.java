package com.smc.ops.delivery.service;


import com.smc.ops.delivery.service.hystrix.POAdapterFeignApiHystrix;
import com.smc.ops.delivery.util.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author lyc
 * @Date 2024/4/17 8:26
 * @Descripton TODO
 */
@FeignClient(name = "po-adapter" ,contextId = "poAdapterFeign", fallbackFactory = POAdapterFeignApiHystrix.class)
public interface PoAdapterFeignApi {

    /**
     * 取出中国制造接单及返信信息
     * @return
     */
    @RequestMapping(value = "/poAdapter/cm/getReceivingOrdersAndReturnLetter", method = RequestMethod.GET)
    ResultVo<String> CMGetReceivingOrdersAndReturnLetter();


    /**
     * 取出中国制造实物发票信息、箱码信息、自报货物发票价格数据及价格明细数据
     * @return
     */
    @RequestMapping(value = "/poAdapter/cm/getInvoiceBarcodePrice", method = RequestMethod.GET)
    ResultVo<String> CMGetInvoiceBarcodePrice();

    /**
     * 接收 广州制造接单及返信信息
     * @param jsonParam
     * @return
     */
    @RequestMapping(value = "/poAdapter/gz/getReceivingOrdersAndReturnLetter", method = RequestMethod.POST)
    ResultVo<String> GZGetReceivingOrdersAndReturnLetter(@RequestBody String jsonParam);


    /**
     * 取出广州制造实物发票信息、箱码信息、自报货物发票价格数据及价格明细数据
     * @return
     */
    @RequestMapping(value = "/poAdapter/gz/getInvoiceBarcodePrice", method = RequestMethod.GET)
    ResultVo<String> GZGetInvoiceBarcodePrice();

    /**
     * 邮件解析
     * @return
     */
    @GetMapping("/poAdapter/jp/emailParse")
    ResultVo<String> emailParse();

    /**
     * ftp解析 解析delivery文件
     * @return
     */
    @GetMapping("/poAdapter/jp/ftpDeliveryParse")
    ResultVo<String> ftpDeliveryParse();

    /**
     * 获取日本异常信息-> ManuJPShippedInfo
     * @return
     */
    @GetMapping("/poAdapter/jp/getManuJPShippedInfoToUnusualData")
    ResultVo<String> getManuJPShippedInfoToUnusualData();
    /**
     * 获取日本异常信息-> InqueryDataSending
     */
    @GetMapping("/poAdapter/jp/getInqueryDataSendingToUnusualData")
    ResultVo<String> getInqueryDataSendingToUnusualData();

    /**
     * 获取中国制造三方及日本的发票信息数据
     */
    @GetMapping("/poAdapter/gw/getGwInvoiceInfoWithAutoJob")
    ResultVo<String> getGwInvoiceInfoWithAutoJob();
}
