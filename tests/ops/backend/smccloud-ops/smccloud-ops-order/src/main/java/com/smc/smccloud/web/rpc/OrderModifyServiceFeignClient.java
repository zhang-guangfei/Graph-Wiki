package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.OrderDeleteReturn;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.model.ordermodify.ApproveOrderModifyDTO;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.service.OrderModifyService;
import com.smc.smccloud.service.OrderModifyServiceFeignApi;
import com.smc.smccloud.service.SalesNotickTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2022/1/13 16:13
 */
@RestController
@Slf4j
public class OrderModifyServiceFeignClient implements OrderModifyServiceFeignApi {

    @Resource
    private OrderModifyService orderModifyService;

    @Resource
    private SalesNotickTaskService salesNotickTaskService;

//    @Override
//    public ResultVo<List<Long>> applyOrderModify(List<OrderModifyVO> list){
//        return orderModifyService.applyOrderModify(list);
//    }

    @Override
    public ResultVo<List<OrderDeleteReturn>> applyOrderCancel(List<OrderModifyVO> list) {
        return orderModifyService.applyOrderCancel(list);
    }

//    @Override
//    public ResultVo<String> modifyOrderEprice(Date fromTime) {
//        return orderModifyService.modifyOrderEprice(fromTime);
//    }

    @Override
    public ResultVo<String> calcImportLotEPrice() {
        return orderModifyService.calcImportLotEPrice();
    }

    @Override
    public ResultVo<String> upTaskInfoByBatchNo(UpTaskInfoVO param) {
        return salesNotickTaskService.upTaskInfoByBatchNo(param);
    }

    @Override
    public ResultVo<String> autoHandNotCancelData() {
        return salesNotickTaskService.autoHandNotCancelData();
    }

    @Override
    public ResultVo<String> calcExportLotEPrice() {
        return orderModifyService.calcExportLotEPrice();
    }

}
