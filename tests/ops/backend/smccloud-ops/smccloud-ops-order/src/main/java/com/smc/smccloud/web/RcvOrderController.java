package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.util.ReturnXxlJobResult;
import com.sales.ops.feign.XxlJobFeignApi;
import com.smc.smccloud.config.XXLJOBProp;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import com.smc.smccloud.model.OrderSales.OrderSalesRequest;
import com.smc.smccloud.model.order.RcvDetailDO;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.model.order.RcvSaleDTO;
import com.smc.smccloud.model.orderstate.OrderStateRequest;
import com.smc.smccloud.model.receiveorder.*;
import com.smc.smccloud.model.sampleorder.SampleOrderManageQuery;
import com.smc.smccloud.service.OrderSalesService;
import com.smc.smccloud.service.OrderStateService;
import com.smc.smccloud.service.ReceiveOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/order/rcvorder")
public class RcvOrderController {

    @Autowired
    @Lazy
    private ReceiveOrderService receiveOrderService;

    @Resource
    private OrderSalesService orderSalesService;

    @Resource
    private XxlJobFeignApi xxlJobFeignApi;

    @Resource
    private OrderStateService orderStateService;

    @Resource
    private XXLJOBProp xxljobHandleIdProp;


    /**
     * 接单处理定时任务状态 状态；
     */
    @GetMapping("/xxlJobAutoOrderHandlerStatus")
    public ResultVo<String> xxlJobAutoOrderHandler(@RequestParam("parityFlag") String parityFlag) {
        try {
            int count = 0;
            List<Integer> orderhandleid = new ArrayList<>();
            if(parityFlag.equals("odd")){
                orderhandleid.add(xxljobHandleIdProp.getOrderhandleidodd());
                count = 1;
            }else if(parityFlag.equals("even")){
                orderhandleid.add(xxljobHandleIdProp.getOrderhandleideven());
                count = 1;
            }else if(parityFlag.equals("other")){
                orderhandleid.add(xxljobHandleIdProp.getOrderhandleidodd());
                orderhandleid.add(xxljobHandleIdProp.getOrderhandleideven());
                count = 2;
            }
            int countCheck = 0;
            for(Integer id : orderhandleid){
                ReturnXxlJobResult result = xxlJobFeignApi.getStatusById(id);
                if( Objects.isNull(result)){
                    return ResultVo.failure("job状态调用失败");
                }
                if(result.getContent().equals(1)){
                    countCheck++;
                }
            }
            // 等于0 都开
            if(countCheck == 0){
                return ResultVo.success("0");
            }else {
                if(count != countCheck){
                    return ResultVo.success(countCheck+"/"+count);
                }
                return ResultVo.success("1");
            }
        } catch (Exception e) {
            return ResultVo.failure();
        }
    }

    /**
     * 接单处理定时任务状态 开关
     */
    @GetMapping("/xxlJobAutoOrderHandlerSwitch")
    public ResultVo<String> xxlJobAutoOrderHandlerSwitch(@RequestParam("flag") Boolean flag,@RequestParam("parityFlag") String parityFlag) {
        try {
            List<Integer> orderhandleid = new ArrayList<>();
            if(parityFlag.equals("odd")){
                orderhandleid.add(xxljobHandleIdProp.getOrderhandleidodd());
            }else if(parityFlag.equals("even")){
                orderhandleid.add(xxljobHandleIdProp.getOrderhandleideven());
            }else if(parityFlag.equals("other")){
                orderhandleid.add(xxljobHandleIdProp.getOrderhandleidodd());
                orderhandleid.add(xxljobHandleIdProp.getOrderhandleideven());
            }
            if (flag) {
                for(Integer id : orderhandleid){
                    xxlJobFeignApi.startById(id);
                }
            } else {
                for(Integer id : orderhandleid){
                    xxlJobFeignApi.stopById(id);
                }
            }
        } catch (Exception e) {
            return ResultVo.failure();
        }
        return ResultVo.success();
    }

    /**
     * 自动接入订单
     */
    @GetMapping("/autoRcvOrder")
    public ResultVo<String> autoRcvOrder(@RequestParam("flag") Boolean flag) {
        if(!flag)
        {
            return ResultVo.failure("未开启自动接单");
        }
        return receiveOrderService.receiveOrderALLFromOrderSales();
    }

    /**
     * 手动接入订单
     */
    @PostMapping("/rcvOrderByOrderSales")
    public ResultVo<String> rcvOrderByOrderSales(@Valid @RequestBody RcvOrderByNos rcvOrderByNos) {
        return receiveOrderService.receiveOrderByOrderNos(rcvOrderByNos.getRorderNos());
    }

    /**
     * 接单查询 (处理中订单)
     *
     * @param request
     * @param page
     * @return
     */
    @PostMapping("/listRcvOrder")
    public ResultVo<PageInfo<ReceiveOrderVO>> listRcvOrder(@RequestBody ReceiveOrderRequest request, Page page) {
        return receiveOrderService.listReceiveOrder(request, page);
    }

    /***
     * 接单处理查询（订单查询 未接入）
     * @param orderSalesRequest
     * @param page
     * */
    @PostMapping("/listOrderSales")
    public ResultVo<PageInfo<OrderSalesDO>> listOrderSales(@RequestBody OrderSalesRequest orderSalesRequest, Page page) {
        PageInfo<OrderSalesDO> orderSalesList = orderSalesService.findAll(orderSalesRequest, page);
        if (orderSalesList.getTotal() >= 0) {
            return ResultVo.success(orderSalesList);
        } else {
            return ResultVo.failure("获取数据失败");
        }
    }

    /**
     * 根据部门统计记录数量
     */
    @PostMapping("/getOrderCountByDeptNo")
    public ResultVo<OrderCount> getOrderCountByDeptNo(@RequestBody OrderSalesRequest request) {
        return orderSalesService.getOrderCount(request);
    }

    /**
     * 订单转为处理
     */
    @PostMapping("/convertToProcessing")
    public ResultVo<String> convertToProcessing(@RequestBody List<ReceiveOrderVO> receiveOrderVOList) {
        return receiveOrderService.convertToProcessing(receiveOrderVOList);
    }


    @GetMapping("/calcOrderPriceSMCGroupCustomer")
    public ResultVo<String> calcOrderPriceSMCGroupCustomer() {
        return receiveOrderService.calcOrderPriceSMCGroupCustomer();
    }

    @RequestMapping(value = "/getSalesDateByModelNo", method = RequestMethod.GET)
    public ResultVo<RcvSaleDTO> getSalesDateByModelNo(@RequestParam("modelNo") String modelNo,@RequestParam("applyDate") String applyDate) {
        return receiveOrderService.getSalesDateByModelNo(modelNo, applyDate);
    }

    // 查询订单主表信息
    @PostMapping("/list")
    public ResultVo<PageInfo<RcvMasterDO>> listRcvMaster(@RequestBody RcvMasterRequest request, Page page) {
        return receiveOrderService.listRcvMaster(request,page);
    }

    // 查询订单子表信息
    @GetMapping("/rcvOrderDetail")
    public ResultVo<List<RcvDetailDO>> listRcvDetail(@RequestParam("rorderNo") String rorderNo) {
        return receiveOrderService.listRcvDetail(rorderNo);
    }

    /**
     * 货期状态导出
     */
    @PostMapping("/exportOrderState")
    public void exportOrderState(@RequestBody OrderStateRequest request) {
        orderStateService.exportOrderState(request);
    }

    @PostMapping("/receiveIPSOrder")
    public ResultVo<String> receiveIPSOrder(@RequestBody List<String> id) {
        return orderSalesService.receiveIPSOrder(id);
    }

}
