package com.smc.smccloud.web;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.OrderIntercepterInfoVO;
import com.smc.smccloud.model.order.OrderModifyOrderChangeVO;
import com.smc.smccloud.model.order.QueryOrderIntercepterVO;
import com.smc.smccloud.model.order.ZdWithOrderModifyVO;
import com.smc.smccloud.model.order.orderEdit.UpApproveReplayVO;
import com.smc.smccloud.model.order.orderEdit.UpPurOrderSupplierVO;
import com.smc.smccloud.model.order.orderdel.SecondProcessWithUiVO;
import com.smc.smccloud.model.ordermodify.*;
import com.smc.smccloud.model.receiveorder.ReceiveOrderRequest;
import com.smc.smccloud.model.receiveorder.ReceiveOrderVO;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.service.OrderModifyService;
import com.smc.smccloud.service.SalesNotickTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@RestController
@RequestMapping("/order/modifyorder")
@CrossOrigin
public class OrderModifyController {

    @Resource
    private OrderModifyService orderModifyService;
    @Resource
    private SalesNotickTaskService salesNotickTaskService;
    /**
     * 订单直接修改
     * @param info
     * @return
     */
    @PostMapping("/modifyOrders")
    public ResultVo<String> modifyOrders(@RequestBody ModifyRcvOrderDTO info){
        return orderModifyService.modifyOrders(info);
    }

    /**
     * 订单修改审批查询导出
     */
    @PostMapping("/exportOrderModifyData")
    public void exportOrderModifyData(@RequestBody OrderModifyRequest request) {
        orderModifyService.exportOrderModifyData(request);
    }


    /**
     * 订单修改审批查询
     * @param request
     * @return
     */
    @PostMapping("/listOrderModify")
    public ResultVo<PageInfo<OrderModifyVO>> listOrderModifyByPage(@RequestBody OrderModifyRequest request){
        return orderModifyService.listOrderModifyByPage(request);
    }

    /**
     * 订单数据查询
     * @param request
     * @return
     */
    @PostMapping("/listRcvModifyOrders")
    public ResultVo<List<ReceiveOrderVO>> listRcvModifyOrders(@RequestBody ReceiveOrderRequest request){
        return orderModifyService.listRcvModifyOrders(request);
    }

    /**
     * 审批订单修改
     * @param dto
     * @return
     */
    @PostMapping("/approveOrderModify")
    public ResultVo<String> approveOrderModify(@RequestBody ApproveOrderModifyDTO dto){
        return orderModifyService.approveOrderModify(dto);
    }

    /**
     * 订单驳回
     * @param dto
     * @return
     */
    @PostMapping("/returnOrderModify")
    public ResultVo<String> returnOrderModify(@RequestBody ApproveOrderModifyDTO dto){
        return orderModifyService.returnOrderModify(dto);
    }

    // 订单还原
    @PostMapping("/orderChangeToInitStatus")
    public ResultVo<OpsSalesCommonParamVO> orderChangeToInitStatus(@RequestBody OrderModifyOrderChangeVO info) {
        return salesNotickTaskService.orderChangeToInitStatus(info);
    }

    // 订单转订-> 订单转订&&变更后内容为“转订出库存”&&“转订出在途”
    @PostMapping("/zdOrderModifyHand")
    public ResultVo<OpsSalesCommonParamVO> zdOrderModifyHand(@RequestBody ZdWithOrderModifyVO info) {
       return salesNotickTaskService.zdOrderModifyHand(info);
    }

    // 订单转订 业务审批回复
    @PostMapping("/upApproveReplay")
    public ResultVo<String> upApproveReplay(@RequestBody UpApproveReplayVO info) {
        return orderModifyService.upApproveReplay(info);
    }

    // 变更采购 业务审批回复
    @PostMapping("/upPoApproveReplay")
    public ResultVo<String> upPoApproveReplay(@RequestBody UpApproveReplayVO info) {
        return orderModifyService.upPoApproveReplay(info);
    }

    // 信用拦截信息查询
    @PostMapping("/queryOrderIntercepterInfo")
    public ResultVo<List<QueryOrderIntercepterVO>> queryOrderIntercepterInfo(@RequestBody OrderIntercepterInfoVO infoVO) {
        return salesNotickTaskService.queryOrderIntercepterInfo(infoVO);
    }

    // 订单转订->> 变更供应商
    @PostMapping("/upPurOrderSupplier")
    public ResultVo<OpsSalesCommonParamVO> upPurOrderSupplier(@RequestBody UpPurOrderSupplierVO upPurOrderSupplierVO) {
        return orderModifyService.upPurOrderSupplier(upPurOrderSupplierVO);
    }

    // 二次审批
    @PostMapping("/secondProcessWithUi")
    public ResultVo<String> secondProcessWithUi(@RequestBody SecondProcessWithUiVO info){
        return salesNotickTaskService.secondProcessWithUi(info);
    }

    // 订单修改页面处理按钮 根据id改状态
    @PostMapping("/updateOrderModifyStatusById")
    public ResultVo<String> updateOrderModifyStatusById(@RequestBody UpdateOrderModifyParam info) {
        return orderModifyService.updateOrderModifyStatusById(info);
    }

}
