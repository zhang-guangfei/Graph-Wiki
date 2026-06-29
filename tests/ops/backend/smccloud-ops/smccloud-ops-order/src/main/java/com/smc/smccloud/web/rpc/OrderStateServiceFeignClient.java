package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OrderState;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.XLSExcelUtil;
import com.smc.smccloud.model.orderstate.*;
import com.smc.smccloud.service.BuService;
import com.smc.smccloud.service.OrderStateDetailService;
import com.smc.smccloud.service.OrderStateService;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
public class OrderStateServiceFeignClient implements OrderStateServiceFeignApi {

    @Resource
    private OrderStateService orderStateService;

    @Resource
    private OrderStateDetailService orderStateDetailService;
    @Resource
    private BuService buService;

    @Override
    public ResultVo<String> addOrderState(OrderStateVO orderStateVO) {
        return orderStateService.addOrderState(orderStateVO);
    }

//    @Override
//    public ResultVo<PageInfo<OrderStateVO>> listOrderState(OrderStateRequest request) {
//        return orderStateService.listOrderState(request, request.getPage());
//    }

    @Override
    public ResultVo<PageInfo<OrderStateDTO>> listOrderState(OrderStateRequest request) {
        return orderStateService.listOrderState(request, request.getPage());
    }

    @Override
    public ResultVo<List<OrderStateDetailVO>> getStateDetail(String orderNo) {
        List<OrderStateDetailDO> orderStateDetailDOList = orderStateDetailService.getOrderStateDetail(orderNo);
        List<OrderStateDetailVO> orderStateDetailVOList = BeanCopyUtil.copyList(orderStateDetailDOList, OrderStateDetailVO.class);
        /*if (PublicUtil.isEmpty(orderStateDetailVOList)) {
            return ResultVo.failure("获取数据失败");
        }*/
        return ResultVo.success(orderStateDetailVOList);
    }

    @Override
    public ResultVo<String> importJPDeliveryData(List<JPDeliveryDataVO> jpDeliveryDataVOList) {
        return orderStateService.importJPDeliveryData(jpDeliveryDataVOList);
    }

    @Override
    public ResultVo<String> importJPReceiveOrderFile(MultipartFile file) {
        return orderStateService.importJPReceiveOrderFile(file);
    }

    @Override
    public Date calcEsArrivalDate(String provider, String transType, Date dlvDate) {
        return orderStateService.calcEsArrivalDate(provider, transType, dlvDate);
    }

    @Override
    public ResultVo<String> importCNMOrderReply() {
        return orderStateService.importCNMOrderReply();
    }

    @Override
    public ResultVo<String> importOverseaOrderReply(List<OrderStateVO> list) {
        return orderStateService.importOverseaOrderReply(list);
    }

    @Override
    public ResultVo<String> importCNFactoryNotSendOrderState() {
        return buService.importCNFactoryNotSendOrderState();
    }

    @Override
    public ResultVo<String> importCNMOPSVRequisitionStatusToSales() {


        ResultVo<String> resultVo=orderStateService.importCNMNotFinishOrderState();

        return resultVo;
    }

    @Override
    public ResultVo<String> supplierReplyOrderState(OrderSupplierReplyState replyState) {
        return orderStateService.supplierReplyOrderState(replyState);
    }

    @Override
    public ResultVo<String> handleDeleleOrder(HandleDeleteOrderDTO dto) {
        return orderStateService.handleDeleleOrder(dto);
    }

    @Override
    public ResultVo<String> checkOrderState() {
        return orderStateService.checkOrderState();
    }

    @Override
    public void syncOrderStateInfo() {
        orderStateService.syncOrderStateInfo();
    }

    @Override
    public ResultVo<String> importSupplierReplyDate(MultipartFile file) {
       return orderStateService.importSupplierReplyDate(file);
    }

    @Override
    public ResultVo<String> downloadJPDeliveryFile() {
        return orderStateService.downloadJPDeliveryFile();
    }

    @Override
    public ResultVo<List<String>> canDelOrderStatus() {
        return orderStateService.canDelOrderStatus();
    }


}