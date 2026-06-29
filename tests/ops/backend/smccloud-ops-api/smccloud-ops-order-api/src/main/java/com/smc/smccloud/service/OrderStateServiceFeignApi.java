package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausOtherToSalesVO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.*;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.model.receiveorder.RcvOrderInfoVO;
import com.smc.smccloud.service.hystrix.OrderStateServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 发布时请去掉
 * url= "http://10.116.194.236:8100"
 */
@FeignClient(name = "order-service",
        contextId = "orderstate",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = OrderStateServiceFeignHystrix.class)
public interface OrderStateServiceFeignApi {

    /**
     * 增加订单状态货期变更
     *
     * @param orderStateVO
     * @return
     */
    @RequestMapping(value = "/order/state/add", method = RequestMethod.POST)
    ResultVo<String> addOrderState(@Valid @RequestBody OrderStateVO orderStateVO);

    /**
     * 货期状态查询
     */
    @RequestMapping(value = "/order/state/list", method = RequestMethod.POST)
    ResultVo<PageInfo<OrderStateDTO>> listOrderState(@RequestBody OrderStateRequest request);

//    @RequestMapping(value = "/order/state/list", method = RequestMethod.POST)
//    ResultVo<PageInfo<OrderStateVO>> listOrderState(@RequestBody OrderStateRequest request);

    /**
     * 根据orderNo查 order_statedetail
     */
    @RequestMapping(value = "/order/state/getStateDetail", method = RequestMethod.GET)
    ResultVo<List<OrderStateDetailVO>> getStateDetail(@RequestParam("orderNo") String orderNo);

    /**
     * 导入日本delivery.data
     * @param jpDeliveryDataVOList
     * @return
     */
    @RequestMapping(value = "/order/state/importJPDeliveryData", method = RequestMethod.POST)
    ResultVo<String> importJPDeliveryData(@Valid @RequestBody List<JPDeliveryDataVO> jpDeliveryDataVOList);

    /**
     * 解析导入日本ORDER-95012.TXT文件
     */
    @PostMapping(value = "/order/state/importJPReceiveOrderFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVo<String> importJPReceiveOrderFile(@RequestPart("file") MultipartFile file);

    /**
     * 计算预计到达日期
     * @param provider  供应商
     * @param transType 运输方式
     * @param dlvDate 出厂纳期
     * @return
     */
    @RequestMapping(value = "/order/rcvorder/getEsArrivalDate", method = RequestMethod.GET)
    Date calcEsArrivalDate(@RequestParam(value = "provider") String provider, @RequestParam("transType") String transType,
                          @RequestParam("dlvDate") Date dlvDate);


    /**
     * 导入中国制造订单返信
     * @return
     */
    @RequestMapping(value = "/order/state/importCNMOrderReply", method = RequestMethod.POST)
    ResultVo<String> importCNMOrderReply();

    /**
     * 导入海外订单返信
     * @return
     */
    @RequestMapping(value = "/order/state/importOverseaOrderReply", method = RequestMethod.POST)
    ResultVo<String> importOverseaOrderReply(@RequestBody List<OrderStateVO> list);

    /**
     * 营业订单状态接口（已接单)
     * 从中国工厂导入所有未发货订单状态
     * @return
     */
    @RequestMapping(value = "/order/state/importCNFactoryNotSendOrderState", method = RequestMethod.GET)
    ResultVo<String> importCNFactoryNotSendOrderState();

    @RequestMapping(value = "/order/state/importCNMOPSVRequisitionStatusToSales", method = RequestMethod.GET)
    ResultVo<String> importCNMOPSVRequisitionStatusToSales();

    /**
     * 供应商回复订单状态
     * @return
     */
    @RequestMapping(value = "/order/state/supplierReplyOrderState", method = RequestMethod.POST)
    ResultVo<String> supplierReplyOrderState(@RequestBody OrderSupplierReplyState replyState);


    /**
     * 收到ops删单并且处理了删单，则将order_state状态从90改成91
     * @param dto
     * @return
     */
    @RequestMapping(value = "/order/state/handleDeleleOrder", method = RequestMethod.POST)
    ResultVo<String> handleDeleleOrder(@RequestBody HandleDeleteOrderDTO dto);


    /**
     * 根据订单状态校对货期状态 (废弃)
     */
    @RequestMapping(value = "/order/state/checkOrderState", method = RequestMethod.GET)
    ResultVo<String> checkOrderState();

    /**
     * 同步订单状态-货期状态
     */
    @RequestMapping(value = "/order/state/syncOrderStateInfo", method = RequestMethod.GET)
    void syncOrderStateInfo();


    /**
     * 导入供应商返信
     */
    @PostMapping(value = "/order/state/importSupplierReplyDate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVo<String> importSupplierReplyDate(@RequestPart("file") MultipartFile file);

    /**
     * 日本订单返信
     * http://192.168.168.4:9999/JP-CN/  下载 BACKODR.ZIP 代替 delivery.dat 文件
     */
    @PostMapping(value = "/order/state/downloadJPDeliveryFile")
    ResultVo<String> downloadJPDeliveryFile();


    /**
     * 获取可删单状态列表
     */
    @GetMapping("/canDelOrderStatus")
    ResultVo<List<String>> canDelOrderStatus();

}
